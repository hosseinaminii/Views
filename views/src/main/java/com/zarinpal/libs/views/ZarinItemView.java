package com.zarinpal.libs.views;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private Integer iconRightPadding;
    private Integer iconLeftPadding;
    private Integer iconRight;
    private Integer iconLeft;
    private String  title;
    private boolean hasActive;
    private float   textSize;
    private int     bgIconRight;
    private int     fontFace;
    private int     bgColor;
    private int     textColor;
    private int     iconRightTint;
    private int     iconLeftTint;
    private int     rotateLeftIcon;
    private int     rotateRightIcon;
    private int     disableColor;

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
            this.rotateLeftIcon = typedArray.getInt(R.styleable.ZarinItemView_zp_rotateLeftIcon, 0);
            this.rotateRightIcon = typedArray.getInt(R.styleable.ZarinItemView_zp_rotateRightIcon, 0);
            this.disableColor = typedArray.getColor(R.styleable.ZarinItemView_zp_disableColor, ContextCompat.getColor(getContext(), R.color.disable_color));
        } finally {
            typedArray.recycle();
        }


        View view = LayoutInflater.from(getContext()).inflate(R.layout.zarin_item_view, this, true);

        this.cardView = view.findViewById(R.id.card_view);
        this.layoutIconRight = view.findViewById(R.id.layout_icon_right);
        this.imgIconLeft = view.findViewById(R.id.img_icon_left);
        this.imgIconRight = view.findViewById(R.id.img_icon_right);
        this.txtTitle = view.findViewById(R.id.txt_title);
        this.cardView.setOnClickListener(this);
        this.setEnabled(this.hasActive);
        this.updateView();

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

    public ZarinImageView getImageViewRight() {
        return this.imgIconRight;
    }

    public ZarinImageView getImageViewLeft() {
        return this.imgIconLeft;
    }

    public ViewGroup getLayoutIconRight() {
        return this.layoutIconRight;
    }

    public void setTintRightIcon(@ColorInt int color) {
        if (this.imgIconRight == null) {
            return;
        }
        this.imgIconRight.setTintColor(color);
    }

    public void setTintLeftIcon(@ColorInt int color) {
        if (this.imgIconLeft == null) {
            return;
        }
        this.imgIconLeft.setTintColor(color);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        cardView.setEnabled(enabled);
        if (!enabled) {
            this.setViewDisableColor();
        }
    }

    private void setViewDisableColor() {

        this.layoutIconRight.setBackground(null);
        this.imgIconRight.setTintColor(this.disableColor);
        this.imgIconLeft.setTintColor(this.disableColor);
        this.txtTitle.setTextColor(this.disableColor);
    }

    private void updateView() {

        this.cardView.setCardBackgroundColor(this.bgColor);
        this.imgIconRight.setPadding(this.iconRightPadding, this.iconRightPadding, this.iconRightPadding, this.iconRightPadding);
        this.imgIconLeft.setPadding(this.iconLeftPadding, this.iconLeftPadding, this.iconLeftPadding, this.iconLeftPadding);
        this.txtTitle.setTextColor(this.textColor);
        this.imgIconLeft.setTintColor(this.iconLeftTint);
        this.imgIconRight.setTintColor(this.iconRightTint);
        this.imgIconRight.setRotation(this.rotateRightIcon);
        this.imgIconLeft.setRotation(this.rotateLeftIcon);

        this.setIconBg(this.bgIconRight);
        this.setIconLeft(this.iconLeft);
        this.setIconRight(this.iconRight);
        this.setTitle(this.title);
        this.setTextSize(this.textSize);
        this.setFontFace();

        if (!this.cardView.isEnabled() || !this.hasActive) {
            this.setViewDisableColor();
        }


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

    private int color;

    public void setAnimationColorsItem(final int[] colors) {

        final ShapeDrawable oval = new ShapeDrawable(new OvalShape());
        oval.setIntrinsicHeight(200);
        oval.setIntrinsicWidth(200);
        oval.setAlpha(30);

        if (imgIconRight.getTintColor() == 0) {
            color = colors[0];
        } else {
            color = colors[1];
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), color, colors[0]);
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int color = (int) valueAnimator.getAnimatedValue();
                        if (!cardView.isEnabled()) {
                            setViewDisableColor();
                            return;
                        }

                        oval.getPaint().setColor(color);
                        setIconBg(oval);
                        imgIconRight.setTintColor(color);
                        txtTitle.setTextColor(textColor);

                    }
                });
                valueAnimator.start();
            }
        }, 250);

    }

    public interface OnClickItemListener {
        void onClickItem();
    }

}