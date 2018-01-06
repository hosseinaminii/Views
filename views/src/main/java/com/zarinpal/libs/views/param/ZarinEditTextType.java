package com.zarinpal.libs.views.param;

import android.support.annotation.IntDef;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 1/2/18.
 * Copyright Hossein Amini All Rights Reserved.
 */

public class ZarinEditTextType {

    public static final int CURRENCY = 0;
    public static final int PAN      = 1;

    @IntDef({CURRENCY, PAN})
    public @interface EditTextType {}
}
