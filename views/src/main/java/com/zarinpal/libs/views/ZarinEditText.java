package com.zarinpal.libs.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zarinpal.libs.views.param.ZarinEditTextType;
import com.zarinpal.libs.views.utitlity.FontUtility;
import com.zarinpal.libs.views.utitlity.TextUtility;
import com.zarinpal.libs.views.utitlity.UnitUtility;

import me.zhanghai.android.materialedittext.MaterialEditText;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 12/18/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

public class ZarinEditText extends RelativeLayout implements TextWatcher {

//    public static final int TYPE_CURRENCY = 0;
//    public static final int TYPE_PAN      = 1;

    private Context              context;
    private OnTextChangeListener onTextChangeListener;
    private FrameLayout          frmLeftFirstIcon, frmLeftSecondIcon, frmRightIcon, frmArrow;
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
    private int    type;
    private int    tintRightIcon, tintLeftFirstIcon, tintLeftSecondIcon;
    private Integer paddingRightIcon, paddingLeftFirstIcon, paddingLeftSecondIcon;
    private boolean isClickable;
    private boolean isArrowVisible;

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
            this.gravity = array.getInt(R.styleable.ZarinEditText_android_gravity, Gravity.LEFT);
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
            this.type = array.getInt(R.styleable.ZarinEditText_zp_type, -1);
            this.tintRightIcon = array.getColor(R.styleable.ZarinEditText_zp_tint_right_icon, 0);
            this.tintLeftFirstIcon =
                    array.getColor(R.styleable.ZarinEditText_zp_tint_left_first_icon, 0);
            this.tintLeftSecondIcon =
                    array.getColor(R.styleable.ZarinEditText_zp_tint_left_second_icon, 0);
            this.paddingRightIcon =
                    array.getDimensionPixelSize(R.styleable.ZarinEditText_zp_padding_right_icon, 0);
            this.paddingLeftFirstIcon =
                    array.getDimensionPixelSize(R.styleable.ZarinEditText_zp_padding_left_first_icon, 0);
            this.paddingLeftSecondIcon =
                    array.getDimensionPixelSize(R.styleable.ZarinEditText_zp_padding_left_second_icon, 0);
            this.isClickable = array.getBoolean(R.styleable.ZarinEditText_zp_is_clickable, true);
            this.isArrowVisible = array.getBoolean(R.styleable.ZarinEditText_zp_is_arrow_visible,
                    false);
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
        this.frmArrow = layout.findViewById(R.id.frm_arrow);
        this.imgLeftFirstIcon = layout.findViewById(R.id.img_left_first_icon);
        this.imgLeftSecondIcon = layout.findViewById(R.id.img_left_second_icon);
        this.imgRightIcon = layout.findViewById(R.id.img_right_icon);
        this.editText = layout.findViewById(R.id.edit_text);

        this.setIcons();
        this.setFontFace(this.fontFace);
        this.setType(this.type);
        this.setTintColor();
        this.setPadding();

        this.setText(this.text);
        this.editText.setGravity(this.gravity);

        if (this.textSize != 0) {
            this.editText.setTextSize(UnitUtility.pxToDp(this.textSize, getContext()));
        }

        this.editText.setHint(this.hint);

        if (this.maxLines != 0) {
            if (this.maxLines == 1) {
                this.editText.setSingleLine();
            }
            this.editText.setMaxLines(this.maxLines);
        }
        if (this.maxLength != 0) {
            this.setMaxLength(this.maxLength);
        }
        if (this.textColor != 0) {
            this.editText.setTextColor(this.textColor);
        }
        if (inputType != InputType.TYPE_NUMBER_VARIATION_NORMAL) {
            this.editText.setInputType(this.inputType);
        }

        this.editText.setHintTextColor(this.textColorHint);

        if (this.activeColor != 0) {
            ViewCompat.setBackgroundTintList(this.editText, ColorStateList.valueOf(this.activeColor));
        }

        if (!this.isClickable) {
            this.editText.setLongClickable(false);
            this.editText.setFocusableInTouchMode(false);
            this.editText.setCursorVisible(false);
        }

        if (this.isArrowVisible && leftFirstIcon == null && leftSecondIcon == null) {
            frmArrow.setVisibility(VISIBLE);
            this.editText.setPadding(
                    (int) UnitUtility.dpToPx(this.context, 24),
                    this.editText.getPaddingTop(),
                    this.editText.getPaddingRight(),
                    this.editText.getPaddingBottom());
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
    private void setFontFace(int fontFace) {
        this.fontFace = fontFace;
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

    public void setType(@ZarinEditTextType.EditTextType int type) {
        this.type = type;

        this.editText.removeTextChangedListener(this);

        if (type == -1) {
            return;
        }

        if (type == ZarinEditTextType.CURRENCY) {
            this.setMaxLength(13);
        }

        this.editText.addTextChangedListener(this);
        this.editText.setRawInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private void setTintColor() {
        if (this.tintRightIcon != 0) {
            this.imgRightIcon.setColorFilter(this.tintRightIcon);
        }
        if (this.tintLeftFirstIcon != 0) {
            this.imgLeftFirstIcon.setColorFilter(this.tintLeftFirstIcon);
        }
        if (this.tintLeftSecondIcon != 0) {
            this.imgLeftSecondIcon.setColorFilter(this.tintLeftSecondIcon);
        }
    }

    private void setPadding() {
        if (this.paddingRightIcon != 0) {
            this.imgRightIcon.setPadding(this.paddingRightIcon, this.paddingRightIcon,
                    this.paddingRightIcon, this.paddingRightIcon);
        }
        if (this.paddingLeftFirstIcon != 0) {
            this.imgLeftFirstIcon.setPadding(this.paddingLeftFirstIcon, this.paddingLeftFirstIcon,
                    this.paddingLeftFirstIcon, this.paddingLeftFirstIcon);
        }
        if (this.paddingLeftSecondIcon != 0) {
            this.imgLeftSecondIcon.setPadding(this.paddingLeftSecondIcon, this.paddingLeftSecondIcon,
                    this.paddingLeftSecondIcon, this.paddingLeftSecondIcon);
        }

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

    public void setMaxLength(int maxLength) {
        this.editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public void setOnTextChangeListener(@NonNull OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
        this.editText.addTextChangedListener(this);
    }

    public String getText() {
        return this.editText.getText().toString();
    }

    public boolean isValidPhoneNumber() {
        return this.getText().matches("^(09{1})+([1-3]{1})+(\\d{8})$");
    }

    /**
     * Convert a text with currency format into a normal text
     *
     * @return normal text
     */
    public long getCurrencyValue() {
        return TextUtility.getCurrencyValue(getText());
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

    public Boolean isValidPanCard() {

        String pan = this.getText().toString().trim();

        pan = pan.replace("-", "");

        if (!pan.matches("\\d+")) {
            return false;
        }

        if (pan.length() != 16) {
            return false;
        }

        char[] chars = pan.toCharArray();
        int    sum   = 0;
        for (int i = 0; i < chars.length; i++) {
            int indexDigitPan = (((i + 1) % 2 == 0) ? Character.getNumericValue(chars[i]) : Character.getNumericValue(chars[i]) * 2);
            sum += indexDigitPan > 9 ? indexDigitPan - 9 : indexDigitPan;
        }
        return (sum % 10) == 0;
    }

    public boolean isValidCVV() {
        return getText().length() >= 3 && getText().length() <= 4;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {

        this.editText.removeTextChangedListener(this);

        if (this.onTextChangeListener != null) {
            this.onTextChangeListener.onTextChange(editable.toString());
            this.editText.addTextChangedListener(this);
            return;
        }

        editable.replace(0, editable.length(),
                editable.toString().replaceAll("[^\\d]", ""));

        String currentVal = editable.toString();
        int    length     = editable.length();

        if (currentVal.isEmpty()) {
            this.editText.addTextChangedListener(this);
            return;
        }

        if (this.type == ZarinEditTextType.CURRENCY) {

            long currencyVal = Long.parseLong(currentVal);

            if (currencyVal == 0) {
                editable.replace(0, length, "");
                this.editText.addTextChangedListener(this);
                return;
            }

            editable.replace(0, length, TextUtility.convertToCurrency(String.valueOf(currencyVal)));

        } else if (this.type == ZarinEditTextType.PAN) {
            editable.replace(0, editable.length(),
                    TextUtility.convertToPan(editable.toString()));
        }

        this.editText.addTextChangedListener(this);
    }

    public interface OnTextChangeListener {
        void onTextChange(String val);
    }
}
