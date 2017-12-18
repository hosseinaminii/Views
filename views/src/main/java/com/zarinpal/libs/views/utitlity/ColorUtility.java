package com.zarinpal.libs.views.utitlity;

import android.graphics.Color;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 12/18/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

public class ColorUtility {

    public static int lighter(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }

    public static int darker(int color, float factor) {
        int red =  Math.max( (int)((Color.red(color) * factor)), 0 );
        int green =  Math.max( (int)((Color.green(color) * factor)), 0 );
        int blue =  Math.max( (int)((Color.blue(color) * factor)), 0 );
        return Color.argb(Color.alpha(color), red, green, blue);
    }

}
