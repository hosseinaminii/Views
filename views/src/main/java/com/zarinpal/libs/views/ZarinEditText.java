package com.zarinpal.libs.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zarinpal.libs.views.utitlity.FontUtility;
import com.zarinpal.libs.views.utitlity.UnitUtility;

import java.lang.reflect.Field;

import me.zhanghai.android.materialedittext.MaterialEditText;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 12/18/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

public class ZarinEditText extends RelativeLayout {

    private Context     context;
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
    private int    activeColor;

    public ZarinEditText(Context context) {
        super(context);
        this.context = context;
        this.initialize();
    }

    public ZarinEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

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
            this.activeColor = array.getColor(R.styleable.ZarinEditText_zp_activeColor, 0);
        } finally {
            array.recycle();
        }

        this.initialize();
    }

    public ZarinEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
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
        this.setFontFace();

        this.editText.setGravity(this.gravity);

        if (this.textSize != 0) {
            this.editText.setTextSize(UnitUtility.pxToDp(this.textSize, getContext()));
        }

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
        if (inputType != InputType.TYPE_NUMBER_VARIATION_NORMAL) {
            this.editText.setInputType(this.inputType);
        }
        this.editText.setText(this.text);
        this.editText.setHintTextColor(this.textColorHint);

        if (this.activeColor != 0) {
            ViewCompat.setBackgroundTintList(this.editText, ColorStateList.valueOf(this.activeColor));
        }
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

        this.editText.setLayoutParams(params);
        this.editText.setPadding(leftPadding, this.editText.getPaddingTop(),
                this.editText.getPaddingRight(), this.editText.getPaddingBottom());

    }

    /**
     * Set font
     */
    private void setFontFace() {
        String fontFamily = FontUtility.IRANSANS_LIGHT;

        switch (fontFace) {
            case FontUtility.INDEX_IRANSANS_ULIGHT: {
                fontFamily = FontUtility.IRANSANS_ULIGHT;
                break;
            }
            case FontUtility.INDEX_IRANSANS_BOLD: {
                fontFamily = FontUtility.IRANSANS_BOLD;
                break;
            }
            case FontUtility.INDEX_OCRA: {
                fontFamily = FontUtility.OCRA;
                break;
            }
        }

        this.editText.setTypeface(FontUtility.getFont(getContext(), fontFamily));
    }

    public void setRightIcon(@DrawableRes int icon) {
        this.rightIcon = this.context.getResources().getDrawable(icon);
        this.setIcons();
    }

    public void setLeftFirstIcon(@DrawableRes int icon) {
        this.leftFirstIcon = this.context.getResources().getDrawable(icon);
    }

    public void setLeftSecondIcon(@DrawableRes int icon) {
        this.leftSecondIcon = this.context.getResources().getDrawable(icon);
    }

    public FrameLayout getRightIcon() {
        return this.frmRightIcon;
    }

    public FrameLayout getLeftFirstIcon() {
        return this.frmLeftFirstIcon;
    }

    public FrameLayout getFrmLeftSecondIcon() {
        return this.frmLeftSecondIcon;
    }

    public MaterialEditText getEditText() {
        return this.editText;
    }

    public void setText(String text) {
        this.editText.setText(text);
    }

    public void setText(@StringRes int text) {
        this.editText.setText(text);
    }

    public String getText() {
        return this.editText.getText().toString();
    }

    public boolean isValidPhoneNumber() {
        return this.getText().matches("^(09{1})+([1-3]{1})+(\\d{8})$");
    }

    public boolean isValidLandLine() {
        String value = this.getText();
        return (value.matches("\\d+") && value.length() == 11);
    }

    public boolean isValidPostalCode() {
        String value = this.getText();
        return (value.matches("\\d+") && value.length() == 10);
    }

    public boolean isValidURL() {
        String value = this.getText();
        return Patterns.WEB_URL.matcher(value).matches();
    }

    public boolean isValidAddress() {
        return (this.getText().length() > 15);
    }

    public boolean isValidIBan() {

        String iBan = this.getText().trim();

        if (!iBan.matches("\\d+")) {
            return false;
        }

        iBan = "IR" + iBan;

        return iBan.length() == 26;
    }

    public boolean isValidEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(this.getText().trim()).matches();
    }

    public boolean isValidPassword() {
        String value = this.getText();
        return value.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).+$");
    }


}
