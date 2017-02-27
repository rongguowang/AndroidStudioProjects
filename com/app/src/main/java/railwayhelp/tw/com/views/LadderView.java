package railwayhelp.tw.com.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;

import railwayhelp.tw.com.graphics.line;
import railwayhelp.tw.com.graphics.point;

/**
 * Created by GoWang on 2017/2/27.
 */

public class LadderView extends View {
    public enum orientation {
        vertical,
        horizontal
    };
    Paint paint;
    point startPoint;
    point stopPoint;
    int steps;
    float mLadderLength;
    float mLadderWidth;
    float mStepHeight;
    orientation ori;
    private ArrayList<line> lineList = new ArrayList<>();
    private ArrayList<line> barList = new ArrayList<>();

    public LadderView(Context context){
        super(context);
    }

    public LadderView(Context context, float sx, float sy, float px, float py, int s, orientation o) {
        super(context);
        startPoint = new point(sx, sy);
        stopPoint  = new point(px, py);
        steps = s;
        ori = o;
        initBars();
        paint = new Paint();
    }

    private void initBars() {
        if (ori == orientation.horizontal) {
            mLadderLength = stopPoint.getX() - startPoint.getX();
            mLadderWidth = stopPoint.getY() - startPoint.getY();
            mStepHeight = mLadderLength / steps;

            lineList.add(new line(startPoint, new point(stopPoint.getX(), startPoint.getY())));
            lineList.add(new line(new point(startPoint.getX(), stopPoint.getY()), stopPoint));

            float barX = startPoint.getX() + (mStepHeight / 2);
            for (int i = 0; i < steps; i++) {
                point start = new point(barX, startPoint.getY());
                point stop = new point(barX, stopPoint.getY());
                barList.add(new line(start, stop));
                barX = barX + mStepHeight;
            }
        } else if (ori == orientation.vertical) {
            mLadderLength = stopPoint.getY() - startPoint.getY();
            mLadderWidth = stopPoint.getX() - startPoint.getX();
            mStepHeight = mLadderLength / steps;

            lineList.add(new line(startPoint, new point(startPoint.getX(), stopPoint.getY())));
            lineList.add(new line(new point(stopPoint.getX(), startPoint.getY()), stopPoint));

            float barY = startPoint.getY() + (mStepHeight / 2);
            for (int i = 0; i < steps; i++) {
                point start = new point(startPoint.getX(), barY);
                point stop = new point(stopPoint.getX(), barY);
                barList.add(new line(start, stop));
                barY = barY + mStepHeight;
            }
        } else {}

    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        for (int i = 0; i < lineList.size(); i++) {
            line tmp = lineList.get(i);
            canvas.drawLine(tmp.getStartPoint().getX(), tmp.getStartPoint().getY(),
                    tmp.getStopPoint().getX(), tmp.getStopPoint().getY(),paint);
        }
        for (int i = 0; i < barList.size(); i++) {
            line tmp = barList.get(i);
            canvas.drawLine(tmp.getStartPoint().getX(), tmp.getStartPoint().getY(),
                    tmp.getStopPoint().getX(), tmp.getStopPoint().getY(),paint);
        }
    }
}
