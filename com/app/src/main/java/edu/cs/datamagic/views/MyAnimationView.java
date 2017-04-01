package edu.cs.datamagic.views;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

import java.util.ArrayList;

import edu.cs.datamagic.graphics.BallXYHolder;
import edu.cs.datamagic.graphics.ShapeHolder;
import edu.cs.datamagic.graphics.point;

/**
 * Created by GoWang on 2017/3/24.
 */

public class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener {

    public final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();
    ValueAnimator bounceAnim = null;
    ShapeHolder ball = null;
    BallXYHolder ballHolder = null;

    public MyAnimationView(Context context) {
        super(context);
        ball = createBall(25, 25);
        ballHolder = new BallXYHolder(ball);
    }

    private void createAnimation() {
        if (bounceAnim == null) {
            point startXY = new point(0f, 0f);
            point endXY = new point(300f, 500f);
            bounceAnim = ObjectAnimator.ofObject(ballHolder, "xY",
                    new XYEvaluator(), endXY);
            bounceAnim.setDuration(1500);
            bounceAnim.addUpdateListener(this);
        }
    }

    private void createAnimation(point start, point end) {
        if (bounceAnim == null) {
//            point startXY = new point(0f, 0f);
//            point endXY = new point(300f, 500f);
            bounceAnim = ObjectAnimator.ofObject(ballHolder, "xY",
                    new XYEvaluator(), start, end);
            bounceAnim.setDuration(1500);
            bounceAnim.addUpdateListener(this);
        }
    }

    public void startAnimation() {
        createAnimation();
        bounceAnim.start();
    }

    public void startAnimation(point start, point end) {
        createAnimation(start, end);
        bounceAnim.start();
    }

    private ShapeHolder createBall(float x, float y) {
        OvalShape circle = new OvalShape();
        circle.resize(50f, 50f);
        ShapeDrawable drawable = new ShapeDrawable(circle);
        ShapeHolder shapeHolder = new ShapeHolder(drawable);
        shapeHolder.setX(x - 25f);
        shapeHolder.setY(y - 25f);
        int red = (int)(Math.random() * 255);
        int green = (int)(Math.random() * 255);
        int blue = (int)(Math.random() * 255);
        int color = 0xff000000 | red << 16 | green << 8 | blue;
        Paint paint = drawable.getPaint(); //new Paint(Paint.ANTI_ALIAS_FLAG);
        int darkColor = 0xff000000 | red/4 << 16 | green/4 << 8 | blue/4;
        RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                50f, color, darkColor, Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        paint.setStrokeWidth(6.0f);
        paint.setStyle(Paint.Style.STROKE);
        shapeHolder.setPaint(paint);
        return shapeHolder;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(ball.getX(), ball.getY());
        ball.getShape().draw(canvas);
        canvas.restore();
    }

    public void onAnimationUpdate(ValueAnimator animation) {
        invalidate();
    }

    public class XYEvaluator implements TypeEvaluator {
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            point startXY = (point) startValue;
            point endXY = (point) endValue;
            return new point(startXY.getX() + fraction * (endXY.getX() - startXY.getX()),
                    startXY.getY() + fraction * (endXY.getY() - startXY.getY()));
        }
    }
}
