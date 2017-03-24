package edu.cs.datamagic.graphics;

/**
 * Created by GoWang on 2017/3/24.
 */

public class BallXYHolder {
    private ShapeHolder mBall;

    public BallXYHolder(ShapeHolder ball) {
        mBall = ball;
    }

    public void setXY(point p) {
        mBall.setX(p.getX());
        mBall.setY(p.getY());
    }

    public point getXY() {
        return new point(mBall.getX(), mBall.getY());
    }
}
