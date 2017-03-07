package edu.cs.datamagic.graphics;


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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("line: " + startPoint.toString() + stopPoint.toString());
        return sb.toString();
    }
}
