package com.zarinpal.libs.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zarinpal.libs.views.utitlity.FontUtility;
import com.zarinpal.libs.views.utitlity.UnitUtility;

import me.zhanghai.android.materialedittext.MaterialEditText;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 12/18/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

public class ZarinEditText extends RelativeLayout {

    private FrameLayout frmLeftFirstIcon, frmLeftSecondIcon, frmRightIcon;
    private ImageView imgLeftFirstIcon, imgLeftSecondIcon, imgRightIcon;
    private MaterialEditText editText;

    private int      fontFace;
    private Drawable leftFirstIcon, leftSecondIcon, rightIcon;
    private float  textSize;
    private int    gravity;
    private String hint;
    private int    maxLines;
    private int    maxLength;
    private int    textColor;
    private int    inputType;
    private String text;
    private int    textColorHint;

    public ZarinEditText(Context context) {
        super(context);
        this.initialize();
    }

    public ZarinEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ZarinEditText);

        try {
            this.fontFace = array.getInt(R.styleable.ZarinEditText_zp_fontFace,
                    FontUtility.INDEX_IRANSANS_LIGHT);
            this.leftFirstIcon = array.getDrawable(R.styleable.ZarinEditText_zp_leftFirstIcon);
            this.leftSecondIcon = array.getDrawable(R.styleable.ZarinEditText_zp_leftSecondIcon);
            this.rightIcon = array.getDrawable(R.styleable.ZarinEditText_zp_rightIcon);

            this.textSize = array.getDimension(R.styleable.ZarinEditText_android_textSize,
                    0);
            this.gravity = array.getInt(R.styleable.ZarinEditText_android_gravity, Gravity.RIGHT);
            this.hint = array.getString(R.styleable.ZarinEditText_android_hint);
            this.maxLines = array.getInt(R.styleable.ZarinEditText_android_maxLines, 0);
            this.maxLength = array.getInt(R.styleable.ZarinEditText_android_maxLength, 0);
            this.textColor = array.getColor(R.styleable.ZarinEditText_android_textColor, 0);
            this.inputType = array.getInt(R.styleable.ZarinEditText_android_inputType,
                    InputType.TYPE_TEXT_VARIATION_NORMAL);
            this.text = array.getString(R.styleable.ZarinEditText_android_text);
            this.textColorHint = array.getColor(R.styleable.ZarinEditText_android_textColorHint,
                    0);
        } finally {
            array.recycle();
        }

        this.initialize();
    }

    public ZarinEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initialize();
    }

    private void initialize() {
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.zarin_edit_text,
                this, true);

        this.frmLeftFirstIcon = layout.findViewById(R.id.frm_left_first_icon);
        this.frmLeftSecondIcon = layout.findViewById(R.id.frm_left_second_icon);
        this.frmRightIcon = layout.findViewById(R.id.frm_right_icon);
        this.imgLeftFirstIcon = layout.findViewById(R.id.img_left_first_icon);
        this.imgLeftSecondIcon = layout.findViewById(R.id.img_left_second_icon);
        this.imgRightIcon = layout.findViewById(R.id.img_right_icon);
        this.editText = layout.findViewById(R.id.edit_text);

        this.setIcons();

        if (this.textSize != 0) {
            this.editText.setTextSize(UnitUtility.pxToDp(this.textSize, getContext()));
        }
        this.editText.setGravity(this.gravity);
        this.editText.setHint(this.hint);
        if (this.maxLines != 0) {
            this.editText.setMaxLines(this.maxLines);
        }
        if (this.maxLength != 0) {
            this.editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(this.maxLength)});
        }
        if (this.textColor != 0) {
            this.editText.setTextColor(this.textColor);
        }
        this.editText.setInputType(this.inputType);
        this.editText.setText(this.text);
        this.editText.setHintTextColor(this.textColorHint);
    }

    /**
     * Set all icons
     */
    private void setIcons() {
        int rightMargin = 0;
        int leftPadding = 0;

        if (this.rightIcon != null) {
            this.frmRightIcon.setVisibility(VISIBLE);
            this.imgRightIcon.setImageDrawable(this.rightIcon);
            rightMargin = (int) UnitUtility.dpToPx(getContext(), 50);
        }
        if (this.leftFirstIcon != null) {
            this.frmLeftFirstIcon.setVisibility(VISIBLE);
            this.imgLeftFirstIcon.setImageDrawable(this.leftFirstIcon);
            leftPadding = (int) UnitUtility.dpToPx(getContext(), 50);
        }
        if (this.leftSecondIcon != null) {
            this.frmLeftSecondIcon.setVisibility(VISIBLE);
            this.imgLeftSecondIcon.setImageDrawable(this.leftSecondIcon);
            leftPadding += UnitUtility.dpToPx(getContext(), 45);
        }

        if (rightMargin == 0 && leftPadding == 0) {
            return;
        }

        LayoutParams params = (LayoutParams) this.editText.getLayoutParams();
        params.setMargins(0, 0, rightMargin, 0);
//
        this.editText.setLayoutParams(params);
        this.editText.setPadding(leftPadding, this.editText.getPaddingTop(),
                this.editText.getPaddingRight(), this.editText.getPaddingBottom());

    }


}
