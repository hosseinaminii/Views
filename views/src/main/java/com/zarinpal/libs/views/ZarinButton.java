package com.zarinpal.libs.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.zarinpal.libs.views.utitlity.ColorUtility;
import com.zarinpal.libs.views.utitlity.FontUtility;

import java.util.Arrays;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 12/17/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

public class ZarinButton extends android.support.v7.widget.AppCompatButton {

    private static final int IRANSANS_LIGHT  = 0;
    private static final int IRANSANS_ULIGHT = 1;
    private static final int IRANSANS_BOLD   = 2;
    private static final int OCRA            = 3;

    private int padding;
    private int fontFace;
    private int rippleColor, firstColor, secondColor, pressColor;
    private int cornerRadius;

    public ZarinButton(Context context) {
        super(context);
        initialize();
    }

    public ZarinButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ZarinButton);

        try {
            this.padding = array.getDimensionPixelSize(R.styleable.ZarinButton_android_padding,
                    20);
            this.fontFace = array.getInt(R.styleable.ZarinButton_zp_fontFace, IRANSANS_LIGHT);
            this.rippleColor = array.getColor(R.styleable.ZarinButton_zp_rippleColor,
                    ContextCompat.getColor(context, R.color.button_ripple));
            this.firstColor = array.getColor(R.styleable.ZarinButton_zp_firstColor,
                    ContextCompat.getColor(context, R.color.button_default));
            this.secondColor = array.getColor(R.styleable.ZarinButton_zp_secondColor,
                    ContextCompat.getColor(context, R.color.button_default));
            this.pressColor = array.getColor(R.styleable.ZarinButton_zp_pressColor, 0);
            this.cornerRadius = array.getInt(R.styleable.ZarinButton_zp_cornerRadius, 7);
        } finally {
            array.recycle();
        }

        initialize();
    }

    public ZarinButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        this.setFontFace();

        setPadding(this.padding, this.padding, this.padding, this.padding);

        setBackgroundColor();
    }

    /**
     * Set font
     */
    private void setFontFace() {
        String fontFamily = FontUtility.IRANSANS_LIGHT;

        switch (fontFace) {
            case IRANSANS_ULIGHT: {
                fontFamily = FontUtility.IRANSANS_ULIGHT;
                break;
            }
            case IRANSANS_BOLD: {
                fontFamily = FontUtility.IRANSANS_BOLD;
                break;
            }
            case OCRA: {
                fontFamily = FontUtility.OCRA;
                break;
            }
        }

        setTypeface(FontUtility.getFont(getContext(), fontFamily));
    }


    private void setBackgroundColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setRippleBackground();
            return;
        }

        setBackground();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setRippleBackground() {
        ColorStateList colors = new ColorStateList(new int[][]{new int[]{}},
                new int[]{this.rippleColor});

        GradientDrawable defaultBg = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{this.firstColor, this.secondColor});
        defaultBg.setCornerRadius(this.cornerRadius);

        float[] outerRadii = new float[8];
        Arrays.fill(outerRadii, this.cornerRadius);
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable  mask           = new ShapeDrawable(roundRectShape);

        RippleDrawable rippleDrawable =
                new RippleDrawable(colors, defaultBg, mask);

        setBackground(rippleDrawable);
    }

    private void setBackground() {
//        int color = Color.parseColor("#fff");
//        color.
     //   this.pressColor = this.pressColor == 0 ? this.firstColor : this.pressColor;
        this.pressColor = ColorUtility.darker(this.firstColor, .9f);
        StateListDrawable stateListDrawable = new StateListDrawable();
        GradientDrawable defaultBg =
                new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{this.firstColor, this.secondColor});
        GradientDrawable pressBg =
                new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[]{this.pressColor, pressColor});
        defaultBg.setCornerRadius(this.cornerRadius);
        pressBg.setCornerRadius(this.cornerRadius);

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressBg);
        stateListDrawable.addState(new int[]{}, defaultBg);

        setBackground(stateListDrawable);
    }


}
