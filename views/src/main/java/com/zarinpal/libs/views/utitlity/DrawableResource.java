package com.zarinpal.libs.views.utitlity;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 12/23/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zarinpal.libs.views.R;

/**
 * This class is for ZarinImageView
 */
public class DrawableResource {

    private Context context;

    public interface OnFetchIssuerBankLogoListener {
        void onBitmap(Bitmap bitmap);
    }

    public DrawableResource(Context context) {
        this.context = context;
    }

    public void getIssuerBankLogo(String slug, final OnFetchIssuerBankLogoListener listener) {

        if (slug.equals("ZarinCard")) {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.zarin_tiny);
            listener.onBitmap(bmp);
            return;
        }

        int drawableResource = (context.getResources().getIdentifier(slug.toLowerCase(),
                "drawable", context.getPackageName()));
        if (drawableResource != 0) {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), drawableResource);
            listener.onBitmap(bmp);
            return;
        }

        Picasso.with(context).load(getIssuerBankLogo(slug)).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                listener.onBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    private String getIssuerBankLogo(String slug) {
        return String.format("https://cdn.zarinpal.com/logos/banks/80x80/%s.png", slug);
    }

}
