package edu.cs.datamagic.graphics;

/**
 * Created by GoWang on 2017/2/27.
 */

public class point {
    private float x = 0;
    private float y = 0;

    public point(float sx, float sy) {
        x = sx;
        y = sy;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" point: x = " + x + " y = " + y);
        return sb.toString();
    }
}
