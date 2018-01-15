package com.zarinpal.libs.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.concurrent.TimeUnit;

/**
 * Zarin Progress View Horizontal Created by farshid roohi on 1/15/18.
 */

public class ZarinProgressViewHorizontal extends LinearLayout {

    public static final String TAG = ZarinProgressViewHorizontal.class.getSimpleName();

    private ProgressBar             progressView;
    private int                     time;
    private int                     counter;
    private CounterProgressListener listener;
    private Drawable                drawable;

    public ZarinProgressViewHorizontal(Context context) {
        super(context);
    }

    public ZarinProgressViewHorizontal(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZarinProgressViewHorizontal);
        this.time = typedArray.getInteger(R.styleable.ZarinProgressViewHorizontal_zp_time, 5);
        this.drawable = typedArray.getDrawable(R.styleable.ZarinProgressViewHorizontal_zp_resource);
        typedArray.recycle();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initializeView();
    }


    private void initializeView() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.progress_view_horizontal, this,
                true);
        this.progressView = view.findViewById(R.id.custom_progress_view);

        if (this.drawable == null) {
            return;
        }
        this.progressView.setProgressDrawable(drawable);
    }

    public void setTime(int timeSec) {
        this.time = timeSec;
    }

    int millTime = 0;

    public void startProgress(final CounterProgressListener listener) {

        if (this.progressView == null) {
            return;
        }

        millTime = this.time * 1000;

        this.listener = listener;
        this.progressView.setMax(millTime);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (counter <= millTime) {
                        progressView.post(new Runnable() {
                            @Override
                            public void run() {
                                progressView.setProgress(counter);
                                if (listener != null) {
                                    counter = counter + 100;
                                    time = ((time - 100));
                                    listener.onCounter(formatsMilliSeconds((time * -1)));

                                }

                            }
                        });
                        Thread.sleep(100);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public interface CounterProgressListener {
        void onCounter(String time);
    }

    private String formatsMilliSeconds(final long milliseconds) {

        String finalTimerString = "";
        String secondsString;

        // Convert total duration into time
        int hours   = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + minutes + " : " + secondsString;

        return finalTimerString;


    }


}

