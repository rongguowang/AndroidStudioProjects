package edu.cs.datamagic.graphics;

/**
 * Created by GoWang on 2017/2/28.
 */

public class text {
    private point textPosition;
    private String textString;

    public text(point pos, String s) {
        textPosition = pos;
        textString = s;
    }

    public String getTextString() {
        return textString;
    }

    public point getTextPosition() {
        return textPosition;
    }
}
