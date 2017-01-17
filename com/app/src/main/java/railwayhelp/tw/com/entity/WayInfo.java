package railwayhelp.tw.com.entity;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class WayInfo {
    private TownInfo mStartTown = null;
    private TownInfo mEndTown = null;
    private Queue<TownInfo> mStopList = new LinkedList<TownInfo>();
    private Queue<RoadInfo> mRoadList = new LinkedList<RoadInfo>();
    private int mWayLength = 0;
    private int mStops = 0;
    private boolean isWayInfoValid = false;
    private String TAG = "WayInfo: ";
    
    public WayInfo(TownInfo start, TownInfo end) {
        if ((start == null) && (end == null))
            return ;
        mStartTown = start;
        mEndTown = end;
        mStopList.add(mStartTown);
    }
    
    public WayInfo(String[] townlist) {
        System.out.println(TAG + "townlist.length = " + townlist.length);
        if (townlist.length < 2)
            return ;
        mStartTown = new TownInfo(townlist[0]);
        mEndTown = new TownInfo(townlist[townlist.length - 1]);
        mStopList.add(mStartTown);
        if (townlist.length > 2) {
            for (int i = 1; i < townlist.length - 1; i++) {
                TownInfo tmptown = new TownInfo(townlist[i]);
                mStopList.add(tmptown);
            }
            mStops = mStopList.size();
        }
    }
    
    public WayInfo(ArrayList<TownInfo> townlist, int stops) {
        if (townlist.size() < 2)
            return ;
        mStartTown = townlist.get(0);
        mEndTown = townlist.get(townlist.size() - 1);
        mStopList.add(mStartTown);
        if (townlist.size() > 2) {
            for (int i = 1; i < townlist.size() - 1; i++) {
                TownInfo tmptown = townlist.get(i);
                mStopList.add(tmptown);
            }
            mStops = mStopList.size();
        }
        mStops = stops;
    }
    
    public WayInfo(WayInfo way) {
        if (way != null) {
            mStartTown = way.getStartTown();
            mEndTown = way.getEndTown();
            mWayLength = way.getWayLength();
            isWayInfoValid = way.getValidWay();
            
            Queue<TownInfo> townlist = way.getStopList();
            Iterator<TownInfo> iter1 = townlist.iterator();
            while(iter1.hasNext()) {
                mStopList.add(iter1.next());
            }
            
            Queue<RoadInfo> roadlist = way.getRoadList();
            Iterator<RoadInfo> iter2 = roadlist.iterator();
            while(iter2.hasNext()) {
                mRoadList.add(iter2.next());
            }
        }
    }
    public void setValidWay(boolean valid) {
        isWayInfoValid = valid;
    }
    
    public boolean getValidWay() {
        return isWayInfoValid;
    }

    public void addStop(TownInfo town, boolean cycle){
        if (town == null)
            return ;
        if (!cycle) {
            if (mStopList.size() > 0) {
                Iterator<TownInfo> iter = mStopList.iterator();
                while (iter.hasNext()) {
                    if(iter.next().compare(town)) {
                        break;
                    }
                }
                if (!iter.hasNext()) {
                    mStopList.add(town);
                }
            }
        } else {
            mStopList.add(town);
        }
    }

    public void setStop(int stop) {
        if (stop > 0){
            mStops = stop;
        }
    }
    
    public int getStop() {
        return mStops;
    }

    public void addRoad(RoadInfo road, boolean cycle) {
        if (road == null)
            return ;
        if (!cycle) {
            if (mRoadList.size() > 0) {
                Iterator<RoadInfo> iter = mRoadList.iterator();
                while (iter.hasNext()) {
                    if(iter.next().compare(road)) {
                        break;
                    }
                }
                if (!iter.hasNext()) {
                    mRoadList.add(road);
                    mWayLength += road.getRoadLength();
                }
            } else {
                mRoadList.add(road);
                mWayLength += road.getRoadLength();
            }
        } else {
            mRoadList.add(road);
            mWayLength += road.getRoadLength();
        }
    }

    public void addWayLength(int len) {
        if (len >= 0) {
            mWayLength += len;
        }
    }
    
    public int getWayLength() {
        return mWayLength;
    }
    
    public TownInfo getStartTown() {
        return mStartTown;
    }
    
    public TownInfo getEndTown() {
        return mEndTown;
    }
    
    public Queue<TownInfo> getStopList() {
        return mStopList;
    }
    
    public Queue<RoadInfo> getRoadList() {
        return mRoadList;
    }
    
    public boolean compare(WayInfo way) {
        boolean isSame = false;
        if (way == null)
            isSame = false;
        if (mStartTown.compare(way.getStartTown()) && mEndTown.compare(way.getEndTown())) {
            Queue<TownInfo> wayStops = way.getStopList();
            Iterator<TownInfo> iter1 = mStopList.iterator();
            Iterator<TownInfo> iter2 = wayStops.iterator();
            while (iter1.hasNext() && iter2.hasNext()) {
                if (!iter1.next().compare(iter2.next())) {
                    break;
                }
            }
            if (!iter1.hasNext() && !iter2.hasNext()) {
                isSame = true;
            } 
        }
        
        return isSame;
    }
    
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nStartTown: " + mStartTown);
        builder.append(" EndTown: " + mEndTown);
        builder.append(" length: " + mWayLength);
        builder.append(" isValid: " + isWayInfoValid);
        builder.append(" stops: " + mStops);
        
        Iterator<TownInfo> iter = mStopList.iterator();
        if (iter.hasNext()) {
            builder.append(" \nstopList:");
            int i = 0;
            while (iter.hasNext()) {
                builder.append("\n\t " + i + ": " + iter.next().toString());
                i++;
            }
        }
        
        Iterator<RoadInfo> iter1 = mRoadList.iterator();
        if (iter1.hasNext()) {
            builder.append(" \nRoadList:");
            int i = 0;
            while (iter1.hasNext()) {
                builder.append("\n\t " + i + ": " + iter1.next().toString());
                i++;
            }
        }
        
        return builder.toString();
    }

    public String toString2() {
        StringBuilder builder = new StringBuilder();

        Iterator<TownInfo> iter = mStopList.iterator();

        if (iter.hasNext()) {
            while (iter.hasNext()) {
                builder.append(iter.next().getName());
                if (iter.hasNext()) {
                    builder.append("->");
                }
            }
            builder.append(" Length: " + mWayLength + "\n");
        }
        return builder.toString();
    }
    
    public static void main(String[] args) {
        //String[] list = {"A", "B", "C"};
        ArrayList<TownInfo> list = new ArrayList<TownInfo>();
        TownInfo t3 = new TownInfo("A");
        TownInfo t4 = new TownInfo("B");
        TownInfo t5 = new TownInfo("C");
        
        list.add(t3);
        list.add(t4);
        list.add(t5);
        
        WayInfo way1 = new WayInfo(list,2);
        WayInfo way2 = new WayInfo(list,2);
        System.out.println(way1.toString());
        System.out.println(way2.toString());
        
        TownInfo t1 = new TownInfo('D');
        TownInfo t2 = new TownInfo('D');
        
        way1.addStop(new TownInfo("D"), false);
        way2.addStop(new TownInfo("D"), false);
        //way2.addStop(new TownInfo("E"), false);
        
        System.out.println(way1.toString());
        System.out.println(way2.toString());
        if (way1.compare(way2)) {
            System.out.println("way1 = way2");
        } else {
            System.out.println("way1 != way2");
        }

    }

}
