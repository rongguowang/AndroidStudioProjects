package edu.cs.datamagic.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

/**
 * Created by GoWang on 2016/12/29.
 */

public class TownInfo {
    private String mTownName = null;
    private ArrayList<RoadInfo> mRoadList = null;
    private int mDestinationCount = Integer.MAX_VALUE;

    public TownInfo(char name) {
        String tmp = name + "";
        //if (tmp != null) {
        mTownName = tmp;
        //}
    }

    public TownInfo(String name) {
        if (name != null)
            mTownName = name;
    }

    public String getName() {
        return mTownName;
    }

    public void addRoadInfo(RoadInfo info) {
        if (mRoadList == null)
            mRoadList = new ArrayList<RoadInfo>();

        if (mRoadList.size() != 0) {
            int i = 0;
            for (i = 0; i < mRoadList.size(); i++) {
                RoadInfo road = mRoadList.get(i);
                if (road.compare(info)) {
                    break;
                }
            }

            if (i == mRoadList.size()) {
                mRoadList.add(info);
            }
        } else {
            mRoadList.add(info);
        }
    }

    public RoadInfo findRoad(TownInfo endTown) {
        if (endTown == null)
            return null;

        if (mRoadList != null && (mRoadList.size() != 0)) {
            for (int i = 0; i < mRoadList.size(); i++) {
                RoadInfo tmpRoad = mRoadList.get(i);
                if (tmpRoad.getEndTown().compare(endTown)) {
                    return tmpRoad;
                }
            }
        }
        return null;
    }

    public ArrayList<WayInfo> findRoad(RoadGraph graph, ArrayList<WayInfo> waylist, int stops) {
        ArrayList<WayInfo> ways = new ArrayList<WayInfo>();
        if (stops == 0) {
            if (waylist != null && waylist.size() > 0) {
                for (int i = 0; i < waylist.size(); i++) {
                    boolean isCurrentTown = false;
                    WayInfo way = waylist.get(i);
                    if (way.getValidWay()) {
                        ways.add(way);
                        continue;
                    }
                    //System.out.println(" way: " + way.toString());
                    Queue<TownInfo> townlist = way.getStopList();
                    if (townlist != null && townlist.size() > 0) {
                        Iterator<TownInfo> iter = townlist.iterator();
                        while (iter.hasNext()) {
                            if ((this.compare(iter.next()) && (!iter.hasNext())) && ((way.getStop() - 1) == 0)) {
                                way.setValidWay(false);
                                isCurrentTown = true;
                            }
                        }
                    }
                    if (isCurrentTown && mRoadList != null && (mRoadList.size() > 0)) {
                        for (int j = 0; j < mRoadList.size(); j++)
                        {
                            RoadInfo road = mRoadList.get(j);
                            WayInfo tmp = new WayInfo(way);
                            if (road.getEndTown().compare(tmp.getEndTown())) {
                                tmp.addStop(road.getEndTown(), true);
                                tmp.addRoad(road, true);
                                tmp.setValidWay(true);
                                tmp.setStop(way.getStop() - 1);
                                ways.add(tmp);
                            }
                        }
                    } else {
                        ways.add(way);
                    }
                }
            }
        } else if (stops > 0){
            if (waylist != null && waylist.size() > 0) {
                for (int i = 0; i < waylist.size(); i++) {
                    boolean isCurrentTown = false;
                    WayInfo way = waylist.get(i);
                    if (way.getValidWay()) {
                        ways.add(way);
                        continue;
                    }
                    //System.out.println(" way: " + way.toString());
                    Queue<TownInfo> townlist = way.getStopList();
                    if (townlist != null && townlist.size() > 0) {
                        Iterator<TownInfo> iter = townlist.iterator();
                        while (iter.hasNext()) {
                            if (this.compare(iter.next()) && (!iter.hasNext())) {
                                way.setValidWay(false);
                                isCurrentTown = true;
                            }
                        }
                    }
                    if (isCurrentTown && mRoadList != null && (mRoadList.size() > 0)) {
                        for (int j = 0; j < mRoadList.size(); j++)
                        {
                            RoadInfo road = mRoadList.get(j);
                            WayInfo tmp = new WayInfo(way);
                            tmp.addStop(road.getEndTown(), true);
                            tmp.addRoad(road, true);
                            tmp.setStop(way.getStop() - 1);
                            ways.add(tmp);
                        }
                    } else {
                        ways.add(way);
                    }
                }

                for (int j = 0; j < mRoadList.size(); j++) {
                    RoadInfo road = mRoadList.get(j);
                    TownInfo town = graph.getTown(road.getEndTown());
                    ways = town.findRoad(graph, ways, (stops - 1));
                }
            }
        }
        return ways;
    }

    public ArrayList<RoadInfo> getRoadList() {
        return mRoadList;
    }

    public void addRoadInfo(TownInfo start, TownInfo end, int length) {
        RoadInfo info = new RoadInfo(start, end, length);

        if (info != null) {
            addRoadInfo(info);
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        String tmp = "TownName: " + getName();
        buffer.append(tmp);
        if (mRoadList != null && mRoadList.size() != 0) {
            buffer.append(" \n\tRoadInfo: ");
            for (int i = 0; i < mRoadList.size(); i++) {
                buffer.append(mRoadList.get(i).toString());
            }
        }
        return buffer.toString();
    }

    public boolean compare(TownInfo info) {
        boolean isSame = false;
        if (mTownName.equals(info.getName())) {
            isSame = true;
        }
        return isSame;
    }
}
