package railwayhelp.tw.com;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import railwayhelp.tw.com.entity.RoadGraph;
import railwayhelp.tw.com.entity.TownInfo;
import railwayhelp.tw.com.entity.WayInfo;

/**
 * Created by GoWang on 2016/12/31.
 */

public class RoadManager {
    private RoadGraph mGraph = null;
    private Context mContext = null;
    private static RoadManager INSTANCE = null;
    
    private RoadManager(Context context, String graphDesc) {
        mContext = context;
        mGraph = new RoadGraph(graphDesc);
    }

    public static RoadManager getRoadManager(Context context, String graphDesc) {
        if (INSTANCE == null) {
            INSTANCE = new RoadManager(context, graphDesc);
        }
        return INSTANCE;
    }

    public static RoadManager getRoadManager() {
        return INSTANCE;
    }

    public String findWay(String way) {
        ArrayList townlist = getTownListByString(way);
        ArrayList<WayInfo> waylist = null;
        int townNO = townlist.size();
        if (townNO < 2)
            return null;

        waylist = mGraph.findWay2(townlist, (townNO - 1));

        return waylist2String(waylist);
    }

    public String findAllWaysByStop(String way, int stops) {
        ArrayList townlist = getTownListByString(way);
        ArrayList<WayInfo> waylist = null;
        int townNO = townlist.size();

        if (townNO < 2)
            return null;

        waylist = mGraph.findAllWays(townlist, stops);
        return waylist2String(waylist);
    }

    public String findShortestWay(String way) {
        ArrayList townlist = getTownListByString(way);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();
        int townNO = townlist.size();

        if (townNO < 2)
            return null;
        WayInfo info = mGraph.findShortestWay(townlist);
        waylist.add(info);

        return waylist2String(waylist);
    }

    public String findAllWaysByLength(String way, int length) {
        ArrayList townlist = getTownListByString(way);
        ArrayList<WayInfo> waylist = new ArrayList<WayInfo>();
        int townNO = townlist.size();

        if (townNO < 2)
            return null;
        waylist = mGraph.findAllWaysUnderLength(townlist, length);

        return waylist2String(waylist);
    }

    private ArrayList<TownInfo> getTownListByString(String way) {
        if (way != null ) {
            String[] waylist = way.split(",");

            ArrayList<TownInfo> townlist = new ArrayList<TownInfo>();
            if (waylist != null && waylist.length > 0) {
                for (int i = 0; i < waylist.length; i++) {
                    String tmp = waylist[i];
                    tmp = tmp.trim();
                    tmp = tmp.toUpperCase();
                    TownInfo town = new TownInfo(tmp);
                    townlist.add(town);
                }
            }
            return townlist;
        }
        return null;
    }

    private String waylist2String(ArrayList<WayInfo> waylist) {
        if (waylist != null || waylist.size() > 0) {
            StringBuilder builder = new StringBuilder();
            int j = 0;
            for (int i = 0; i < waylist.size(); i++) {
                WayInfo way = waylist.get(i);
                if (way.getValidWay()) {
                    j++;
                    builder.append("Road " + j + ": " + way.toString2());

                }
            }
            builder.append("Total " + j + " Roads Founded\n");
            return builder.toString();
        }
        return null;
    }
}
