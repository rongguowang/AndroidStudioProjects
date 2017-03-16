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

    public void setTextString(String s) {
        if ( s != null) {
            textString = s;
        }
    }

    public boolean hasText() {
        boolean ret = true;
        if (textString == null) {
            ret = false;
        }
        return ret;
    }

    public boolean hasPosition() {
        boolean ret = true;
        if (textPosition == null)
            ret = false;
        return ret;
    }
}
