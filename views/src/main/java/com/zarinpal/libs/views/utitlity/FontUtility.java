package com.zarinpal.libs.views.utitlity;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 12/17/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

public class FontUtility {

    public static Typeface getFont(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    public static final String IRANSANS_LIGHT  = "sansLight.ttf";
    public static final String IRANSANS_ULIGHT = "sansULight.ttf";
    public static final String IRANSANS_BOLD   = "sansBold.ttf";
    public static final String OCRA            = "ocra.ttf";

}
