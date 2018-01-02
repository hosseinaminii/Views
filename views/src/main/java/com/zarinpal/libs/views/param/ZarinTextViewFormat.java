package com.zarinpal.libs.views.param;

import android.support.annotation.IntDef;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 1/2/18.
 * Copyright Hossein Amini All Rights Reserved.
 */

public class ZarinTextViewFormat {

    public static final int NORMAL      = 0;
    public static final int PAN         = 1;
    public static final int CURRENCY    = 2;
    public static final int JALALI_DATE = 3;

    @IntDef({NORMAL, PAN, CURRENCY, JALALI_DATE})
    public @interface TextViewFormat {}

}
