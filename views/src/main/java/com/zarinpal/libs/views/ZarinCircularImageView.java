package com.zarinpal.libs.views;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 12/23/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

public class ZarinCircularImageView extends CircularImageView {

    public ZarinCircularImageView(Context context) {
        super(context);
    }

    public ZarinCircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZarinCircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void loadImage(String URL) {
        try {
            Picasso.with(getContext()).load(URL).into(this);
        } catch (Exception ex) {
            Picasso.with(getContext()).load(URL).into(this);
        }
    }

    public void loadImage(String URL, @DrawableRes int errorImage) {
        try {
            Picasso.with(getContext()).load(URL).error(errorImage).into(this);
        } catch (Exception ex) {
            Picasso.with(getContext()).load(URL).error(errorImage).into(this);
        }
    }

}
