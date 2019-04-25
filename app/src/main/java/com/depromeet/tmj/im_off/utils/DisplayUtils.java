package com.depromeet.tmj.im_off.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class DisplayUtils {
    public static int dpToPixel(Context context, float dp) {
        return dpToPixel(context.getResources(), dp);
    }

    public static int dpToPixel(Resources resources, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }
}
