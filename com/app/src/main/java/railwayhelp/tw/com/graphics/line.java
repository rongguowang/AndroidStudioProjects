package railwayhelp.tw.com.graphics;


/**
 * Created by GoWang on 2017/2/27.
 */

public class line {
    private point startPoint = null;
    private point stopPoint = null;

    public line(point x, point y) {
        startPoint = x;
        stopPoint = y;
    }

    public point getStartPoint() {
        return startPoint;
    }

    public point getStopPoint() {
        return stopPoint;
    }
}
