package railwayhelp.tw.com.entity;

/**
 * Created by GoWang on 2016/12/29.
 */

public class RoadInfo {
    private int mRoadLength = 0;
    private TownInfo mStartTown = null;
    private TownInfo mEndTown = null;

    public RoadInfo(int length) {
        if (length >= 0)
            mRoadLength = length;
    }

    public RoadInfo(TownInfo start, TownInfo end, int length) {
        if (start != null && end != null && length != 0) {
            mStartTown = start;
            mEndTown = end;
            mRoadLength = length;
        }
    }

    public TownInfo getStartTown() {
        return mStartTown;
    }

    public TownInfo getEndTown() {
        return mEndTown;
    }

    public int getRoadLength() {
        return mRoadLength;
    }


    public boolean compare(RoadInfo info) {
        boolean isSame = false;
        if (info == null)
            isSame = false;
        if ((mStartTown.equals(info.getStartTown())) && mEndTown.equals(info.getEndTown()) && (mRoadLength == info.getRoadLength())) {
            isSame = true;
        }

        return isSame;
    }

    public String toString() {
        return "\n\tstartTown:" + mStartTown.getName() + " endTown: " + mEndTown.getName() + " length: " + mRoadLength;
    }
}
