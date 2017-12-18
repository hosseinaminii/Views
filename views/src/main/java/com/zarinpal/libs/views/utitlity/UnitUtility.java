package com.zarinpal.libs.views.utitlity;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 12/18/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

public class UnitUtility {

    public static float dpToPx(Context context, int pixel) {
        Resources r  = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, r.getDisplayMetrics());
    }

    public static float pxToDp(float px, Context context){
        Resources      resources = context.getResources();
        DisplayMetrics metrics   = resources.getDisplayMetrics();
        float          dp        = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

}
