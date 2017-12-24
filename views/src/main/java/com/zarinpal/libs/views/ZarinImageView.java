package com.zarinpal.libs.views;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zarinpal.libs.views.utitlity.DrawableResource;

/**
 * Android Views Project at ZarinPal
 * Created by hosseinAmini on 12/23/17.
 * Copyright Hossein Amini All Rights Reserved.
 */

public class ZarinImageView extends android.support.v7.widget.AppCompatImageView {

    private Context context;
    private boolean isBlur;
    private boolean isGradient;

    public ZarinImageView(Context context) {
        super(context);
        this.context = context;
    }

    public ZarinImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ZarinImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    public interface OnTouchCompactListener {
        void onActionDown();

        void onActionUp();
    }

    public void setOnTouchCompactListener(final OnTouchCompactListener listener) {
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    listener.onActionUp();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    listener.onActionDown();
                }
                return true;
            }
        });
    }

    public void setTintColor(int color) {
        this.setColorFilter(color);
    }

    public void isBlur(boolean isBlur) {
        this.isBlur = isBlur;
    }

    public void loadAsyncIssuerLogoBitmap(String slug) {
        new DrawableResource(context).getIssuerBankLogo(slug, new DrawableResource.OnFetchIssuerBankLogoListener() {
            @Override
            public void onBitmap(Bitmap bitmap) {
                ZarinImageView.this.setImageBitmap(bitmap);
            }
        });

    }

    public void setTransitionNameCompact(String transitionName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setTransitionName(transitionName);
        }
    }


    public int getPixelColor() {
        BitmapDrawable drawable = ((BitmapDrawable) this.getDrawable());
        if (drawable != null) {
            return drawable.getBitmap().getPixel(10, 10);
        }
        return 0;
    }

    public void loadAsyncBitmap(final String url) {

        Picasso.with(getContext()).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                if (isGradient) {
                    setGradient(bitmap);
                    return;
                }

                if (isBlur) {
                    bitmap = BlurBuilder.blur(getContext(), bitmap);
                    ZarinImageView.this.setImageBitmap(bitmap);
                    return;
                }


                ZarinImageView.this.setImageBitmap(bitmap);


            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                loadAsyncBitmap(url);

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    public void isGradient(boolean isGradient) {
        this.isGradient = isGradient;
    }

    private void setGradient(Bitmap bitmap) {
//        Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
//        if(bitmap == null) {
//            return;
//        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int bottomCenterColor = bitmap.getPixel(width / 2, height - 1);
        int topLeftColor = bitmap.getPixel(0, 0);
        int topRightColor = bitmap.getPixel(width - 1, 0);
        int centerColor = bitmap.getPixel(width / 2, height / 2);

        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), bottomCenterColor,
                topLeftColor, centerColor, topRightColor);

        animator.setDuration(20000);
        animator.setRepeatCount(Animation.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                GradientDrawable gradient = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM, new int[]{(
                        int)valueAnimator.getAnimatedValue(), Color.WHITE, Color.WHITE, Color.WHITE}
                );

                gradient.setCornerRadius(0);
                gradient.setAlpha(190);
                setImageDrawable(gradient);
            }
        });

        animator.start();
    }

    public void setGradient(final int alpha, final int lastColor, final int[] colors) {

        this.clearAnimation();
        this.clearColorFilter();
        this.postDelayed(new Runnable() {
            @Override
            public void run() {

                ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), lastColor, colors[1]);
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        GradientDrawable gradient = new GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM, new int[]{(int) valueAnimator.getAnimatedValue(),
                                Color.WHITE, Color.WHITE, Color.WHITE}
                        );
                        gradient.setCornerRadius(0);
                        gradient.setAlpha(alpha);
                        setImageDrawable(gradient);
                    }
                });

                valueAnimator.start();
            }
        }, 250);
    }
}

class BlurBuilder {

    private static final float BITMAP_SCALE = 0.7f;
    private static final float BLUR_RADIUS  = 25f;


    public static Bitmap blur(Context ctx, Bitmap image) {

        int width  = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap  = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript        rs           = RenderScript.create(ctx);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation          tmpIn        = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation          tmpOut       = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

}
