package railwayhelp.tw.com.entity;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by GoWang on 2016/12/29.
 */

public class RoadGraph {
    private ArrayList<TownInfo> mTownList = null;
    private int mTownCount = 0;
    private int mRoadCount = 0;
    private String mGraphDescription = null;
    private String TAG = "RoadGraph: ";

    public RoadGraph(String tmp) {
        if (tmp != null) {
            mGraphDescription = tmp;
        }
        buildGraph();
    }

    private void buildGraph() {
        if (mGraphDescription != null) {
            String[] mList = mGraphDescription.split(",");

            for (int i = 0; i < mList.length; i++) {
                String tmp = mList[i];
                tmp = tmp.trim();
                tmp = tmp.toUpperCase();
                System.out.println(TAG + tmp+"\n\n\n");

                char[] cList = tmp.toCharArray();
                buildTownList(cList);
            }
        }
    }

    private void buildTownList(char[] cList) {

        if (validRoad(cList)) {
            TownInfo startTown = new TownInfo(cList[0]);
            System.out.println(TAG + "strtTown: " + startTown.toString());
            TownInfo endTown = new TownInfo(cList[1]);
            System.out.println(TAG + "endTown: " + endTown.toString());
            RoadInfo road = new RoadInfo(startTown, endTown, (cList[2] - 48));
            startTown.addRoadInfo(road);
            addTown(startTown);
            addTown(endTown);
            System.out.println("*********************************************");
            for (int i = 0 ; i < mTownList.size(); i++) {
                System.out.println(TAG + "--" + mTownList.get(i).toString());
            }
            System.out.println("---------------------------------------------");
        }
    }

    private boolean validRoad(char[] clist) {
        boolean isValid = false;
        if ((clist[0] >= 'A' && clist[0] < 'Z') && (clist[1] >= 'A' && clist[1] <= 'Z') && (clist[2] > '0' && clist[2]< '9'))
        {
            isValid = true;
        }

        return isValid;
    }

    private void addTown(TownInfo town) {
        if (town == null)
            return ;
        if (mTownList == null ) {
            mTownList = new ArrayList<TownInfo>();
        }

        boolean added = false;
        for (int i = 0; i < mTownList.size(); i++) {
            TownInfo mTown = mTownList.get(i);
            System.out.println(TAG + " addTown " + mTown.getName());
            if (mTown.compare(town)) {
                ArrayList<RoadInfo> roadList = town.getRoadList();
                if (roadList != null && roadList.size() > 0) {
                    for (int j = 0; j < roadList.size();j++) {
                        mTown.addRoadInfo(roadList.get(j));
                    }
                }
                mTownList.remove(i);
                mTownList.add(mTown);
                added = true;
            }
        }
        if (!added)
            mTownList.add(town);

    }

    public ArrayList<WayInfo> findWay(ArrayList<TownInfo> townlist, int stops) {
        if (townlist.size() < 2 || stops < 0)
            return null;
        Iterator<TownInfo> iter = townlist.iterator();
        TownInfo startTown = iter.next();
        TownInfo endTown = townlist.get(townlist.size() - 1);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();

        WayInfo way = new WayInfo(townlist, stops);

        if (townlist.size() == 2) {
            if (stops == 1) {
                TownInfo tmpStart = getTown(startTown);
                TownInfo tmpEnd = iter.next();
                RoadInfo mInfo = null;
                mInfo = tmpStart.findRoad(tmpEnd);
                if (mInfo != null) {
                    way.addRoad(mInfo, false);
                    way.addStop(tmpEnd,false);
                }
                waylist.add(way);
            } else if (stops != 1) {
            }
        } else if (townlist.size() > 2) {
            TownInfo tmpStart = getTown(startTown);
            TownInfo tmpEnd = iter.next();
            RoadInfo mInfo = null;
            for (int i = 0; i < stops; i++) {
                mInfo = tmpStart.findRoad(tmpEnd);
                if (mInfo != null) {
                    way.addRoad(mInfo, false);
                    tmpStart = getTown(tmpEnd);
                    if (iter.hasNext()) {
                        tmpEnd = iter.next();
                    }
                    if (i == (stops - 1)) {
                        way.setValidWay(true);
                        way.addStop(tmpEnd,false);
                    }
                    continue;
                } else {
                    if (i < stops) {
                        way.setValidWay(false);
                    }
                    break;
                }
            }
            waylist.add(way);
        }
        return waylist;
    }

    public ArrayList<WayInfo> findWay2(ArrayList<TownInfo> townlist, int stops) {
        if (townlist.size() < 2 || stops < 0)
            return null;
        Iterator<TownInfo> iter = townlist.iterator();
        TownInfo startTown = iter.next();
        TownInfo endTown = townlist.get(townlist.size() - 1);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();

        WayInfo way = new WayInfo(townlist, stops);

        if (townlist.size() == 2) {
            if (stops == 1) {
                TownInfo tmpStart = getTown(startTown);
                TownInfo tmpEnd = iter.next();
                RoadInfo mInfo = null;
                mInfo = tmpStart.findRoad(tmpEnd);
                if (mInfo != null) {
                    way.addRoad(mInfo, false);
                    way.addStop(tmpEnd,false);
                    way.setValidWay(true);
                }
                waylist.add(way);
            } else if (stops > 1) {
                TownInfo tmpStart = getTown(startTown);
                waylist.add(way);
                waylist = tmpStart.findRoad(this, waylist, (stops - 1));

            }
        } else if (townlist.size() > 2) {
            TownInfo tmpStart = getTown(startTown);
            TownInfo tmpEnd = iter.next();
            RoadInfo mInfo = null;
            for (int i = 0; i < stops; i++) {
                mInfo = tmpStart.findRoad(tmpEnd);
                if (mInfo != null) {
                    way.addRoad(mInfo, false);
                    tmpStart = getTown(tmpEnd);
                    if (iter.hasNext()) {
                        tmpEnd = iter.next();
                    }
                    if (i == (stops - 1)) {
                        way.setValidWay(true);
                        way.addStop(tmpEnd,false);
                    }
                    continue;
                } else {
                    if (i < stops) {
                        way.setValidWay(false);
                    }
                    break;
                }
            }
            waylist.add(way);
        }

        return waylist;
    }

    public ArrayList<WayInfo> findAllWays(ArrayList<TownInfo> townlist, int stops) {
        if (townlist.size() < 2 || stops < 0)
            return null;

        ArrayList<WayInfo> waylist = null;
        ArrayList<WayInfo> allways = new ArrayList<WayInfo>();

        for (int i = 0; i <= stops; i++) {
            waylist = findWay2(townlist, i);
            if (waylist != null && waylist.size() > 0) {
                for (int j = 0; j < waylist.size(); j++) {
                    allways.add(waylist.get(j));
                }
            }
        }
        return allways;
    }

    public WayInfo findShortestWay(ArrayList<TownInfo> townlist) {
        if (townlist.size() < 2)
            return null;
        ArrayList<WayInfo> waylist = null;
        waylist = findAllWays(townlist, mTownList.size());
        this.printWayList(waylist);
        WayInfo shortest = null;
        if (waylist != null && waylist.size() > 0) {
            shortest = waylist.get(waylist.size() - 1);
            for (int i = 0; i < waylist.size(); i++) {
                WayInfo tmp = waylist.get(i);
                if ((tmp.getWayLength() > 0 )&& (tmp.getWayLength() < shortest.getWayLength())) {
                    shortest = tmp;
                }
            }
        }

        return shortest;
    }

    public ArrayList<WayInfo> findAllWaysUnderLength(ArrayList<TownInfo> townlist, int length) {
        if (townlist.size() < 2 || length < 0)
            return null;

        ArrayList<WayInfo> waylist = null;
        ArrayList<WayInfo> allways = new ArrayList<WayInfo>();
        waylist = findAllWays(townlist, 9);
        if (waylist != null && waylist.size() > 0) {
            for (int i = 0; i < waylist.size(); i++) {
                WayInfo tmp = waylist.get(i);
                if (tmp.getWayLength() <= length) {
                    allways.add(tmp);
                }
            }
        }


        return allways;
    }

    public TownInfo getTown(TownInfo town) {
        if (town == null)
            return null;
        if (mTownList != null && mTownList.size() > 0) {
            for (int i = 0; i < mTownList.size(); i++) {
                TownInfo tmpTown = mTownList.get(i);
                if (tmpTown.compare(town))
                    return tmpTown;
            }
        }
        return null;
    }

    public void testAD(RoadGraph graph) {
        TownInfo town1 = new TownInfo("A");
        TownInfo town2 = new TownInfo("D");
        ArrayList<TownInfo> townlist = new ArrayList<TownInfo>();
        townlist.add(town1);
        townlist.add(town2);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();
        waylist = graph.findWay(townlist, 1);
        for (int i = 0; i < waylist.size(); i++) {
            System.out.println("\n" + waylist.get(i).toString());
        }
    }

    public void testABC(RoadGraph graph) {
        TownInfo town1 = new TownInfo("A");
        TownInfo town2 = new TownInfo("B");
        TownInfo town3 = new TownInfo("C");
        ArrayList<TownInfo> townlist = new ArrayList<TownInfo>();
        townlist.add(town1);
        townlist.add(town2);
        townlist.add(town3);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();
        waylist = graph.findWay(townlist, 2);
        for (int i = 0; i < waylist.size(); i++) {
            System.out.println("\n" + waylist.get(i).toString());
        }
    }

    public void testADC(RoadGraph graph) {
        TownInfo town1 = new TownInfo("A");
        TownInfo town2 = new TownInfo("D");
        TownInfo town3 = new TownInfo("C");
        ArrayList<TownInfo> townlist = new ArrayList<TownInfo>();
        townlist.add(town1);
        townlist.add(town2);
        townlist.add(town3);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();
        waylist = graph.findWay(townlist, 2);
        for (int i = 0; i < waylist.size(); i++) {
            System.out.println("\n" + waylist.get(i).toString());
        }
    }

    public void testAEBCD(RoadGraph graph) {
        TownInfo town1 = new TownInfo("A");
        TownInfo town2 = new TownInfo("E");
        TownInfo town3 = new TownInfo("B");
        TownInfo town4 = new TownInfo("C");
        TownInfo town5 = new TownInfo("D");
        ArrayList<TownInfo> townlist = new ArrayList<TownInfo>();
        townlist.add(town1);
        townlist.add(town2);
        townlist.add(town3);
        townlist.add(town4);
        townlist.add(town5);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();
        waylist = graph.findWay(townlist, 4);
        for (int i = 0; i < waylist.size(); i++) {
            System.out.println("\n" + waylist.get(i).toString());
        }
    }

    public void testAED(RoadGraph graph) {
        TownInfo town1 = new TownInfo("A");
        TownInfo town2 = new TownInfo("E");
        TownInfo town3 = new TownInfo("D");
        ArrayList<TownInfo> townlist = new ArrayList<TownInfo>();
        townlist.add(town1);
        townlist.add(town2);
        townlist.add(town3);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();
        waylist = graph.findWay(townlist, 2);
        for (int i = 0; i < waylist.size(); i++) {
            System.out.println("\n" + waylist.get(i).toString());
        }
    }

    public void testCE(RoadGraph graph) {
        TownInfo town1 = new TownInfo("C");
        TownInfo town2 = new TownInfo("E");
        ArrayList<TownInfo> townlist = new ArrayList<TownInfo>();
        townlist.add(town1);
        townlist.add(town2);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();
        waylist = graph.findWay2(townlist, 2);
        printWayList(waylist);
    }

    public void testCC(RoadGraph graph) {
        TownInfo town1 = new TownInfo("C");
        TownInfo town2 = new TownInfo("C");
        ArrayList<TownInfo> townlist = new ArrayList<TownInfo>();
        townlist.add(town1);
        townlist.add(town2);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();
        waylist = graph.findWay2(townlist, 5);
        printWayList(waylist);
    }

    public void testCCAll(RoadGraph graph) {
        TownInfo town1 = new TownInfo("C");
        TownInfo town2 = new TownInfo("C");
        ArrayList<TownInfo> townlist = new ArrayList<TownInfo>();
        townlist.add(town1);
        townlist.add(town2);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();
        waylist = graph.findAllWays(townlist, 3);
        printWayList(waylist);
    }

    public void testAC(RoadGraph graph) {
        TownInfo town1 = new TownInfo("A");
        TownInfo town2 = new TownInfo("C");
        ArrayList<TownInfo> townlist = new ArrayList<TownInfo>();
        townlist.add(town1);
        townlist.add(town2);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();
        waylist = graph.findWay2(townlist, 4);
        printWayList(waylist);
    }

    public void testACShortest(RoadGraph graph) {
        TownInfo town1 = new TownInfo("A");
        TownInfo town2 = new TownInfo("C");
        ArrayList<TownInfo> townlist = new ArrayList<TownInfo>();
        townlist.add(town1);
        townlist.add(town2);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();
        WayInfo way = null;
        way = graph.findShortestWay(townlist);
        waylist.add(way);
        printWayList(waylist);
    }

    public void testBBShortest(RoadGraph graph) {
        TownInfo town1 = new TownInfo("B");
        TownInfo town2 = new TownInfo("B");
        ArrayList<TownInfo> townlist = new ArrayList<TownInfo>();
        townlist.add(town1);
        townlist.add(town2);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();
        WayInfo way = null;
        way = graph.findShortestWay(townlist);
        waylist.add(way);
        printWayList(waylist);
    }

    public void testCCLen(RoadGraph graph) {
        TownInfo town1 = new TownInfo("C");
        TownInfo town2 = new TownInfo("C");
        ArrayList<TownInfo> townlist = new ArrayList<TownInfo>();
        townlist.add(town1);
        townlist.add(town2);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();
        waylist = graph.findAllWaysUnderLength(townlist, 30);
        printWayList(waylist);
    }
    public void printWayList(ArrayList<WayInfo> list) {
        if (list != null && list.size() > 0) {
            System.out.println("========================================================================");
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getValidWay())
                    System.out.println("\nWayInfo: " + i + " " + list.get(i).toString());
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }
}

