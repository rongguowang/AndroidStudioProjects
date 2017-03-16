package edu.cs.datamagic.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import edu.cs.datamagic.R;
import edu.cs.datamagic.graphics.line;
import edu.cs.datamagic.graphics.point;
import edu.cs.datamagic.graphics.text;

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
    float offsetHorizontal;
    float offsetVertical;
    //int defaultPadding = R.dimen.ladder_view_default_padding;
    int defaultPadding = 5;
    int defaultIndexHeight;
    private ArrayList<line> lineList = new ArrayList<>();
    private ArrayList<line> barList = new ArrayList<>();
    private ArrayList<text> textList = new ArrayList<>();
    private String[] texts;
    private String TAGS = "LadderView: ";

    public LadderView(Context context, AttributeSet attrs){
        super(context, attrs);
        paint = new Paint();
        int[] set = {
                android.R.attr.textSize,
                android.R.attr.orientation,
                android.R.attr.actionBarSize,
        };
        TypedArray array = context.obtainStyledAttributes(attrs, set);
        try {

            Log.e(TAGS, "function LadderView after arry was get");
            int textSize = array.getDimensionPixelSize(0, 50);
            Log.e(TAGS, "function LadderView textsize = " + textSize);
            paint.setTextSize(textSize);

            int orie = array.getInteger(1, 1);
            ori = (orie == 1) ? orientation.vertical:orientation.horizontal;
            Log.e(TAGS, "function LadderView ori = " + ori);

            int barHeight = array.getDimensionPixelSize(2, 40);
            defaultIndexHeight = barHeight / 2;
            Log.e(TAGS, "function LadderView barheight = " + barHeight);
            array.recycle();

            array = context.obtainStyledAttributes(attrs, R.styleable.LadderView);
            steps = array.getInt(R.styleable.LadderView_steps, 9);
            Log.e(TAGS, "function LadderView steps = " + steps);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            array.recycle();
        }
    }

    public LadderView(Context context, float sx, float sy, float px, float py, int s, orientation o, String[] slist) {
        super(context);
        startPoint = new point(sx, sy);
        stopPoint  = new point(px, py);
        steps = s;
        ori = o;
        paint = new Paint();
        paint.setTextSize(50);
        texts = slist;
        calculateBars();
    }

    private void onDataChanged() {
        calculateBars();
    }

    public void setTextList(String[] slist) {
        texts = slist;
        if (barList.size() > 0) {
            buildTextList(texts);
            invalidate();
        }
    }

    private void buildTextList(String[] slist) {
        textList.clear();
        float textMeasure = 0;
        float textSize = paint.getTextSize();
        float x = 0, y = 0;
        float inx = 0, iny = 0, indexMeausre = 0;
        if (slist == null) {
            for (int i = 0; i < steps; i++) {
                indexMeausre = paint.measureText(String.valueOf(i));
                if (ori == orientation.vertical) {
                    offsetHorizontal = mLadderWidth / 2;
                    offsetVertical = mStepHeight / 2;

                    inx = barList.get(i).getStartPoint().getX() - defaultIndexHeight;
                    iny = barList.get(i).getStartPoint().getY() + offsetVertical + (textSize / 2);
                } else if (ori == orientation.horizontal) {
                    offsetHorizontal = mStepHeight / 2;
                    offsetVertical = mLadderWidth / 2;

                    inx = barList.get(i).getStartPoint().getX() + offsetHorizontal - (indexMeausre / 2);
                    iny = barList.get(i).getStopPoint().getY() + defaultIndexHeight;
                } else {
                }
                textList.add(new text(new point(inx, iny), String.valueOf(i + 1)));
            }
        } else {
            for (int i = 0; i < steps; i++) {
//                if (i < slist.length && slist[i] != null) {
//                    textMeasure = paint.measureText(slist[i]);
//                }else {
                    textMeasure = paint.measureText(slist[0]);
//                }
                indexMeausre = paint.measureText(String.valueOf(i));
                if (ori == orientation.vertical) {
                    offsetHorizontal = mLadderWidth / 2;
                    offsetVertical = mStepHeight / 2;
                    if (textMeasure < mLadderWidth) {
                        x = barList.get(i).getStartPoint().getX() + offsetHorizontal - (textMeasure / 2);
                    } else {
                        x = barList.get(i).getStartPoint().getX();
                    }
                    y = barList.get(i).getStartPoint().getY() + offsetVertical + (textSize / 2);

                    inx = barList.get(i).getStartPoint().getX() - defaultIndexHeight;
                    iny = y;
                } else if (ori == orientation.horizontal) {
                    offsetHorizontal = mStepHeight / 2;
                    offsetVertical = mLadderWidth / 2;
                    if (textMeasure < mStepHeight) {
                        x = barList.get(i).getStartPoint().getX() + offsetHorizontal - (textMeasure / 2);
                    } else {
                        x = barList.get(i).getStartPoint().getX();
                    }
                    y = barList.get(i).getStartPoint().getY() + offsetVertical + (textSize / 2);

                    inx = barList.get(i).getStartPoint().getX() + offsetHorizontal - (indexMeausre / 2);
                    iny = barList.get(i).getStopPoint().getY() + defaultIndexHeight;
                } else {
                }

                textList.add(new text(new point(inx, iny), String.valueOf(i)));
                if (i < slist.length && slist[i] != null) {
                    text t = new text(new point(x, y), slist[i]);
                    textList.add(t);
                } else {
                    continue;
                }
            }
        }
    }

    private void calculateBars() {
        if (ori == orientation.horizontal) {
            mLadderLength = stopPoint.getX() - startPoint.getX();
            mLadderWidth = stopPoint.getY() - startPoint.getY();
            mStepHeight = mLadderLength / steps;

            lineList.add(new line(startPoint, new point(stopPoint.getX(), startPoint.getY())));
            lineList.add(new line(new point(startPoint.getX(), stopPoint.getY()), stopPoint));

            float barX = startPoint.getX();
            for (int i = 0; i < steps + 1; i++) {
                point start = new point(barX, startPoint.getY());
                point stop = new point(barX, stopPoint.getY());
                barList.add(new line(start, stop));
                barX = barX + mStepHeight;
                if (barX >= stopPoint.getX()) {
                    barX = stopPoint.getX();
                }
            }
            if (steps == 1) {
                point start = new point(stopPoint.getX(), startPoint.getY());
                point stop = stopPoint;
                barList.add(new line(start, stop));
            }
        } else if (ori == orientation.vertical) {
            mLadderLength = stopPoint.getY() - startPoint.getY();
            mLadderWidth = stopPoint.getX() - startPoint.getX();
            mStepHeight = mLadderLength / steps;

            lineList.add(new line(startPoint, new point(startPoint.getX(), stopPoint.getY())));
            lineList.add(new line(new point(stopPoint.getX(), startPoint.getY()), stopPoint));

            float barY = startPoint.getY();
            for (int i = 0; i < steps + 1; i++) {
                point start = new point(startPoint.getX(), barY);
                point stop = new point(stopPoint.getX(), barY);
                barList.add(new line(start, stop));
                barY = barY + mStepHeight;
                if (barY >= stopPoint.getY()) {
                    barY = stopPoint.getY();
                }
            }
            if (steps == 0) {
                point start = new point(startPoint.getX(), stopPoint.getY());
                point stop = stopPoint;
                barList.add(new line(start, stop));
            }
        } else {}



        buildTextList(texts);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int sx = Math.max(defaultPadding, getPaddingLeft());
        int sy = Math.max(defaultPadding, getPaddingTop());

        int px = Math.min((w - defaultPadding), (w - getPaddingRight()));
        int py = Math.min((h - defaultPadding), (h - getPaddingBottom()));

        if ( ori == orientation.horizontal) {
            py = py - defaultIndexHeight;
            py = Math.min(py, (sy + defaultIndexHeight * 2));
        } else if (ori == orientation.vertical) {
            sx = sx + defaultIndexHeight;
            px = Math.min(px, (sx + defaultIndexHeight * 2));
        }

        startPoint = new point(sx, sy);
        stopPoint = new point(px, py);

        onDataChanged();
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        Log.e(TAGS, "**************************************************");
        for (int i = 0; i < lineList.size(); i++) {
            line tmp = lineList.get(i);
            Log.e(TAGS, tmp.toString());
            canvas.drawLine(tmp.getStartPoint().getX(), tmp.getStartPoint().getY(),
                    tmp.getStopPoint().getX(), tmp.getStopPoint().getY(),paint);
        }
        Log.e(TAGS, "--------------------------------------------------");
        for (int i = 0; i < barList.size(); i++) {
            line tmp = barList.get(i);
            Log.e(TAGS, tmp.toString());
            canvas.drawLine(tmp.getStartPoint().getX(), tmp.getStartPoint().getY(),
                    tmp.getStopPoint().getX(), tmp.getStopPoint().getY(),paint);
        }

        Log.e(TAGS, "=================================================");
        for (int i = 0; i < textList.size(); i++) {
            text t = textList.get(i);
            canvas.drawText(t.getTextString(), t.getTextPosition().getX(), t.getTextPosition().getY(), paint);
        }
    }
}
