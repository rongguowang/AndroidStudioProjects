package edu.cs.datamagic.graphics;

import java.util.Random;

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

    public static point getRandom(int value) {
        Random r = new Random();
        int x = r.nextInt(1000 - value) + value;
        int y = r.nextInt(1000 - value) + value;
        System.gc();
        return new point(x, y);
    }
}
