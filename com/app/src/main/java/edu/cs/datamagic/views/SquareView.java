package edu.cs.datamagic.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import edu.cs.datamagic.graphics.point;
import edu.cs.datamagic.graphics.text;

/**
 * Created by GoWang on 2017/3/8.
 */

public class SquareView extends View {
    enum IndexPosition {
        top,
        bottom,
        left,
        right
    };
    private point topLeftPoint;
    private point bottomRightPoint;
    private text innerText;
    private String innerString;
    private text indexText;
    private String indexString;
    private int squareHeight;
    private IndexPosition defaultIndexPosition = IndexPosition.left;
    private IndexPosition indexPos = defaultIndexPosition;
    private Paint textPaint;
    private Paint linePaint;
    private int defaultPadding = 3;
    private String TAGS = "SquareView: ";

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        textPaint = new Paint();
        linePaint = new Paint();
        int[] set = {
                android.R.attr.textSize,
                android.R.attr.actionBarSize,
        };
        TypedArray array = context.obtainStyledAttributes(attrs, set);
        try {
            int textSize = array.getDimensionPixelSize(0, 50);
            textPaint.setTextSize(textSize);
            textPaint.setColor(Color.BLACK);

            squareHeight = array.getDimensionPixelSize(1, 80);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            array.recycle();
        }
    }

    public void setIndexText(String index) {
        indexString = index;
        buildIndexText();
        invalidate();
    }

    public void setInnerText(String inner) {
        innerString = inner;
        buildInnerText();
        invalidate();
    }

    public point getInnerTextPosition() {
        return innerText.getTextPosition();
    }

    private void buildIndexText() {
        int sx = 0;
        int sy = 0;
        if (topLeftPoint != null) {
            sx = (int)topLeftPoint.getX();
            sy = (int)topLeftPoint.getY();
        }


        int px =0, py = 0;
        int textx = 0, texty = 0;

        if( indexString != null) {
            float textWidthMeaure = textPaint.measureText(indexString);
            float textHeightMeaure = textPaint.getTextSize();
            if (indexPos == IndexPosition.left) {
                textx = sx + Math.max(defaultPadding, getPaddingLeft());;
                texty = sy + (squareHeight / 2) + (int)(textHeightMeaure / 2);

                sx = textx + (int) textWidthMeaure + (squareHeight / 2);

                px = sx + squareHeight;
                py = sy + squareHeight;
            } else if (indexPos == IndexPosition.right) {
                textx = sx + squareHeight + (squareHeight / 2);
                texty = sy + squareHeight - (squareHeight / 2) + (int)(textHeightMeaure / 2);

                px = sx + squareHeight;
                py = sy + squareHeight;
            } else if (indexPos == IndexPosition.top) {
                textx = sx + (squareHeight / 2) - (int)(textWidthMeaure / 2);
                texty = sy + (int)(textHeightMeaure) + Math.max(defaultPadding, getPaddingTop());

                sy = texty + (squareHeight / 2);

                px = sx + squareHeight;
                py = sy + squareHeight;
            } else if (indexPos == IndexPosition.bottom) {
                textx = sx + (squareHeight / 2) - (int)(textWidthMeaure / 2);
                texty = sy + squareHeight + (squareHeight / 2);

                px = sx + squareHeight;
                py = sy + squareHeight;
            }
            indexText = new text(new point(textx, texty), indexString);
        }

        topLeftPoint = new point(sx, sy);
        bottomRightPoint = new point(px, py);
    }

    private void buildInnerText() {
        int sx = 0;
        int sy = 0;
        if (topLeftPoint != null) {
            sx = (int)topLeftPoint.getX();
            sy = (int)topLeftPoint.getY();
        }

        if (innerString != null) {
            float textWidthMeaure = textPaint.measureText(innerString);
            float textHeightMeaure = textPaint.getTextSize();
            int textx = sx + (squareHeight / 2) - (int)(textWidthMeaure / 2);
            int texty = sy + (squareHeight / 2) + (int)(textHeightMeaure / 2);
            innerText = new text(new point(textx, texty), innerString);
        }
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int sx = getPaddingLeft();
        int sy = getPaddingTop();


        int px = sx + squareHeight;
        int py = sy + squareHeight;

        topLeftPoint = new point(sx, sy);
        bottomRightPoint = new point(px, py);

        buildIndexText();
        buildInnerText();
    }

    @Override
    protected void onMeasure(int widthMeaureSpec, int heightMesureSpec) {
        int minw = getPaddingLeft() + getPaddingRight() + defaultPadding * 2 + squareHeight * 2;
        int minh = getPaddingTop() + getPaddingBottom() + defaultPadding * 2 + squareHeight * 2;

        int w = resolveSizeAndState(minw, widthMeaureSpec, 1);
        int h = resolveSizeAndState(minh, heightMesureSpec, 1);

        setMeasuredDimension(w,h);
    }

    @Override
    public void onDraw(Canvas canvas) {
        linePaint.setStrokeWidth(3);
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(topLeftPoint.getX(), topLeftPoint.getY(), bottomRightPoint.getX(), bottomRightPoint.getY(), linePaint);

        if (innerText != null) {
            canvas.drawText(innerText.getTextString(), innerText.getTextPosition().getX(), innerText.getTextPosition().getY(), textPaint);
        }

        if (indexText != null) {
            canvas.drawText(indexText.getTextString(), indexText.getTextPosition().getX(),indexText.getTextPosition().getY(), textPaint);
        }
    }
}
