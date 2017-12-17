package com.zarinpal.libs.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zarinpal.libs.views.utitlity.FontUtility;
import com.zarinpal.libs.views.utitlity.TextUtility;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 12/17/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

public class ZarinTextView extends android.support.v7.widget.AppCompatTextView {

    private static final int IRANSANS_LIGHT  = 0;
    private static final int IRANSANS_ULIGHT = 1;
    private static final int IRANSANS_BOLD   = 2;
    private static final int OCRA            = 3;

    private static final int FORMAT_NORMAL      = 0;
    private static final int FORMAT_PAN         = 1;
    private static final int FORMAT_CURRENCY    = 2;
    private static final int FORMAT_JALALI_DATE = 3;

    private boolean useTextFormat           = true;
    private boolean activeLongPressCopyText = false;
    private int fontFace;
    private int textFormat;


    public ZarinTextView(Context context) {
        super(context);
        initialize();
    }

    public ZarinTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ZarinTextView);
        try {
            this.fontFace = array.getInt(R.styleable.ZarinTextView_zp_fontFace, IRANSANS_LIGHT);
            this.textFormat = array.getInt(R.styleable.ZarinTextView_zp_textFormat, IRANSANS_LIGHT);
            this.activeLongPressCopyText = array.getBoolean(
                    R.styleable.ZarinTextView_zp_activeLongPressCopyText, false);
        } finally {
            array.recycle();
        }

        initialize();
    }

    public ZarinTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        this.changeFontFace();

        // Set text of textView Again for formatting text
        // Todo: Fix later
//        if (!(getText().toString().isEmpty()) && textFormat != FORMAT_NORMAL) {
//            setText(getText());
//        }

        this.setActiveLongPressCopyText(this.activeLongPressCopyText, null);
    }

    /**
     * Change font
     */
    private void changeFontFace() {
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

    /**
     * Active long press
     * @param state true/false for activate and deactivate
     * @param listener
     */
    public void setActiveLongPressCopyText(boolean state,
                                           @Nullable final TextUtility.OnTextCopyListener listener) {
        if (getText().toString().isEmpty()) {
            return;
        }

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                TextUtility.copyTextIntoClipboard(getContext(), getText().toString(), listener);
                return true;
            }
        });
    }

    /**
     * Add prefix
     *
     * @param prefix for text
     */
    public void addPrefix(String prefix) {
        String result = String.format("%s %s", getText().toString(), prefix);
        this.useTextFormat = false;
        setText(result);
    }

    /**
     * Add suffix
     *
     * @param suffix for text
     */
    public void addSuffix(String suffix) {
        String result = String.format("%s %s", suffix, getText().toString());
        this.useTextFormat = false;
        setText(result);
    }

    /**
     * Convert a text with currency format into a normal text
     *
     * @return normal text
     */
    public String getCurrencyValue() {
        return TextUtility.getRawValue(getText().toString(), ",");
    }

    /**
     * Set format for text
     *
     * @param textFormat format of text
     */
    public void setTextFormat(int textFormat) {
        this.textFormat = textFormat;
    }

    // override setText for formatting text
    @Override
    public void setText(CharSequence text, BufferType type) {
        if (this.useTextFormat) {
            if (this.textFormat == FORMAT_CURRENCY) {
                text = TextUtility.convertToCurrency(text.toString());
            } else if (this.textFormat == FORMAT_PAN) {
                text = TextUtility.convertToPan(text.toString());
            } else if (this.textFormat == FORMAT_JALALI_DATE) {
                text = TextUtility.convertToJalaliDate(text.toString());
            }
        }

        this.useTextFormat = true;
        super.setText(text, type);
    }
}
