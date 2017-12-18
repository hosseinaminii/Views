package com.zarinpal.libs.views;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 12/18/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

public class ZarinEditText extends android.support.v7.widget.AppCompatEditText{

    public ZarinEditText(Context context) {
        super(context);
        this.initialize();
    }

    public ZarinEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialize();
    }

    public ZarinEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initialize();
    }

    private void initialize() {

    }


}
