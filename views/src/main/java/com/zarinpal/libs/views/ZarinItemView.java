package com.zarinpal.libs.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.zarinpal.libs.views.utitlity.FontUtility;

/**
 * Item View Created by farshid roohi on 1/21/18.
 */

public class ZarinItemView extends LinearLayout implements View.OnClickListener {

    private ZarinImageView imgIconRight, imgIconLeft;
    private ZarinTextView       txtTitle;
    private LinearLayout        layoutIconRight;
    private CardView            cardView;
    private OnClickItemListener listener;

    private int     bgIconRight;
    private int     fontFace;
    private Integer iconRight;
    private Integer iconLeft;
    private float   textSize;
    private String  title;
    private Integer iconRightPadding;
    private Integer iconLeftPadding;
    private int     bgColor;
    private int     textColor;
    private int     iconRightTint;
    private int     iconLeftTint;
    private boolean hasActive;

    public ZarinItemView(Context context) {
        super(context);
    }

    public ZarinItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initializeView(context, attrs);
    }

    public ZarinItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initializeView(context, attrs);
    }

    private void initializeView(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZarinItemView);
        try {
            this.bgIconRight = typedArray.getInteger(R.styleable.ZarinItemView_zp_bgIconRight, 0);
            this.fontFace = typedArray.getInt(R.styleable.ZarinItemView_zp_fontFace, FontUtility.INDEX_IRANSANS_BOLD);
            this.iconLeft = typedArray.getResourceId(R.styleable.ZarinItemView_zp_iconLeft, 0);
            this.iconRight = typedArray.getResourceId(R.styleable.ZarinItemView_zp_iconRight, 0);
            this.textSize = typedArray.getDimensionPixelSize(R.styleable.ZarinItemView_zp_titleSize, 12);
            this.title = typedArray.getString(R.styleable.ZarinItemView_zp_setTitle);
            this.iconRightPadding = typedArray.getDimensionPixelSize(R.styleable.ZarinItemView_zp_paddingIconRight, 5);
            this.iconLeftPadding = typedArray.getDimensionPixelSize(R.styleable.ZarinItemView_zp_paddingIconLeft, 5);
            this.bgColor = typedArray.getColor(R.styleable.ZarinItemView_zp_bgColor, Color.WHITE);
            this.textColor = typedArray.getColor(R.styleable.ZarinItemView_android_textColor, Color.BLACK);
            this.iconLeftTint = typedArray.getColor(R.styleable.ZarinItemView_zp_tintLeftColor, Color.TRANSPARENT);
            this.iconRightTint = typedArray.getColor(R.styleable.ZarinItemView_zp_tintRightColor, Color.TRANSPARENT);
            this.hasActive = typedArray.getBoolean(R.styleable.ZarinItemView_zp_hasActive, true);
        } finally {
            typedArray.recycle();
        }


        View view = LayoutInflater.from(getContext()).inflate(R.layout.zarin_item_view, this, true);

        this.cardView = view.findViewById(R.id.card_view);
        this.layoutIconRight = view.findViewById(R.id.layout_icon_right);
        this.imgIconLeft = view.findViewById(R.id.img_icon_left);
        this.imgIconRight = view.findViewById(R.id.img_icon_right);
        this.txtTitle = view.findViewById(R.id.txt_title);

        this.cardView.setCardBackgroundColor(this.bgColor);
        this.imgIconRight.setPadding(this.iconRightPadding, this.iconRightPadding, this.iconRightPadding, this.iconRightPadding);
        this.imgIconLeft.setPadding(this.iconLeftPadding, this.iconLeftPadding, this.iconLeftPadding, this.iconLeftPadding);
        this.txtTitle.setTextColor(this.textColor);
        this.imgIconLeft.setTintColor(this.iconLeftTint);
        this.imgIconRight.setTintColor(this.iconRightTint);

        this.setIconBg(this.bgIconRight);
        this.setIconLeft(this.iconLeft);
        this.setIconRight(this.iconRight);
        this.setTitle(this.title);
        this.setTextSize(this.textSize);
        this.setFontFace();
        this.setEnabled(this.hasActive);

        this.cardView.setOnClickListener(this);


    }

    public void setIconRight(@DrawableRes int value) {
        if (this.imgIconRight == null) {
            return;
        }
        if (value == 0) {
            return;
        }
        this.imgIconRight.setImageResource(value);
    }

    public void setIconLeft(@DrawableRes int value) {
        if (this.imgIconLeft == null) {
            return;
        }
        if (value == 0) {
            return;
        }
        this.imgIconLeft.setImageResource(value);
    }

    public void setIconBg(int value) {
        if (this.layoutIconRight == null) {
            return;
        }
        if (value == 0) {
            return;
        }
        this.layoutIconRight.setBackgroundResource(value);
    }

    public void setIconBg(ShapeDrawable shapeDrawable) {
        if (this.layoutIconRight == null) {
            return;
        }
        if (shapeDrawable == null) {
            return;
        }
        this.layoutIconRight.setBackground(shapeDrawable);
    }

    public void setTitle(String value) {
        if (this.txtTitle == null) {
            return;
        }
        this.txtTitle.setText(value);
    }

    public void setTextSize(float value) {
        if (this.txtTitle == null) {
            return;
        }
        this.txtTitle.setTextSize(value);
    }

    public void setListener(OnClickItemListener listener) {
        if (listener == null) {
            return;
        }
        this.listener = listener;
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        cardView.setEnabled(enabled);
        if (enabled) {
            return;
        }
        this.imgIconRight.setTintColor(ContextCompat.getColor(getContext(), R.color.disable_color));
        this.imgIconLeft.setTintColor(ContextCompat.getColor(getContext(), R.color.disable_color));
        this.txtTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.disable_color));

    }

    @Override
    public void onClick(View view) {
        if (this.listener != null) {
            this.listener.onClickItem();
        }
    }

    /**
     * Change font
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

        this.txtTitle.setTypeface(FontUtility.getFont(getContext(), fontFamily));
    }

    public interface OnClickItemListener {
        void onClickItem();
    }

}
