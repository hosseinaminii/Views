package com.zarinpal.libs.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zarinpal.libs.views.utitlity.FontUtility;
import com.zarinpal.libs.views.utitlity.UnitUtility;

/**
 * Zarin Pan TextView Created by farshid roohi on 12/23/17.
 */

@SuppressLint("AppCompatCustomView")
public class ZarinPanTextView extends LinearLayout {

    private TextView txtSliceOne, txtSliceTwo, txtSliceThree, txtSliceFour;

    private static final int IRANSANS_LIGHT = 0;
    private static final int IRANSANS_ULIGHT = 1;
    private static final int IRANSANS_BOLD = 2;
    private static final int OCRA = 3;

    private int fontFace;
    private String cardStr;
    private Drawable backgroundDrawable;
    private float textSize;
    private float marginItem;

    private Integer textColor;


    public ZarinPanTextView(Context context) {
        super(context);
    }

    public ZarinPanTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ZarinPanTextView);
        try {
            this.fontFace = array.getInt(R.styleable.ZarinPanTextView_zp_fontFace, IRANSANS_LIGHT);
            this.cardStr = array.getString(R.styleable.ZarinPanTextView_zp_setCardNumber);
            this.backgroundDrawable = array.getDrawable(R.styleable.ZarinPanTextView_zp_backgroundNumber);
            this.textSize = array.getDimension(R.styleable.ZarinPanTextView_zp_textSize, 12.0f);
            this.marginItem = array.getDimension(R.styleable.ZarinPanTextView_zp_textSize, 4.0f);
            this.textColor = array.getColor(R.styleable.ZarinPanTextView_android_textColor, Color.BLACK);
        } finally {
            array.recycle();
        }
        if (this.backgroundDrawable == null) {
            this.backgroundDrawable = ContextCompat.getDrawable(getContext(), R.drawable.default_bg_pan);
        }

        this.initializeView();
    }


    private void initializeView() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.zarin_pan_text_view, this);
        //initialize recyclerView
        this.txtSliceOne = view.findViewById(R.id.txt_slice_one);
        this.txtSliceTwo = view.findViewById(R.id.txt_slice_two);
        this.txtSliceThree = view.findViewById(R.id.txt_slice_three);
        this.txtSliceFour = view.findViewById(R.id.txt_slice_four);

        this.txtSliceOne.setBackground(this.backgroundDrawable);
        this.txtSliceTwo.setBackground(this.backgroundDrawable);
        this.txtSliceThree.setBackground(this.backgroundDrawable);
        this.txtSliceFour.setBackground(this.backgroundDrawable);

        setFont();
        setText(cardStr);


    }

    public void setText(String str) {
        if (str == null) {
            return;
        }
        if (str.length() != 19) {
            return;
        }
        if (!str.contains("-")) {
            return;
        }

        this.cardStr = str;
        String[] cardNumberSlice = this.cardStr.split("-");
        this.txtSliceOne.setText(cardNumberSlice[0]);
        this.txtSliceTwo.setText(cardNumberSlice[1]);
        this.txtSliceThree.setText(cardNumberSlice[2]);
        this.txtSliceFour.setText(cardNumberSlice[3]);
    }


    private void setFont() {

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

        // set font
        this.txtSliceOne.setTypeface(FontUtility.getFont(getContext(), fontFamily));
        this.txtSliceTwo.setTypeface(FontUtility.getFont(getContext(), fontFamily));
        this.txtSliceThree.setTypeface(FontUtility.getFont(getContext(), fontFamily));
        this.txtSliceFour.setTypeface(FontUtility.getFont(getContext(), fontFamily));

        // set text size
        this.textSize = UnitUtility.pxToDp(this.textSize, getContext());
        this.txtSliceOne.setTextSize(this.textSize);
        this.txtSliceTwo.setTextSize(this.textSize);
        this.txtSliceThree.setTextSize(this.textSize);
        this.txtSliceFour.setTextSize(this.textSize);

//        // set margin
        int margin = (int) UnitUtility.pxToDp(this.marginItem, getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(margin, margin, margin, margin);
        this.txtSliceOne.setLayoutParams(params);
        this.txtSliceTwo.setLayoutParams(params);
        this.txtSliceThree.setLayoutParams(params);
        this.txtSliceFour.setLayoutParams(params);

        // set text color
        this.txtSliceOne.setTextColor(this.textColor);
        this.txtSliceTwo.setTextColor(this.textColor);
        this.txtSliceThree.setTextColor(this.textColor);
        this.txtSliceFour.setTextColor(this.textColor);


    }
}