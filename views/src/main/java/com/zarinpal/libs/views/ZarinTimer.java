package com.zarinpal.libs.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * Zarin Progress View Horizontal Created by farshid roohi on 1/15/18.
 */

public class ZarinTimer extends LinearLayout {

    public static final String TAG = ZarinTimer.class.getSimpleName();

    private OnTimeCompleteListener timeCompleteListener;
    private ProgressBar            progressView;
    private int                    time;
    private int                    counter;
    private Drawable               drawable;

    public ZarinTimer(Context context) {
        super(context);
    }

    public ZarinTimer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZarinTimer);
        this.time = typedArray.getInteger(R.styleable.ZarinTimer_zp_time, 5);
        this.drawable = typedArray.getDrawable(R.styleable.ZarinTimer_zp_background);
        typedArray.recycle();

        if (this.drawable == null) {
            this.drawable = ContextCompat.getDrawable(getContext(), R.drawable.yellow_progress_bar);
        }

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
        this.progressView.setProgressDrawable(drawable);
    }

    public void setTime(int timeSec) {
        this.time = timeSec;
    }

    public void setOnTimeCompleteListener(OnTimeCompleteListener listener) {
        this.timeCompleteListener = listener;
    }

    int millTime = 0;

    public void startTimer(final OnTimerListener listener) {

        if (this.progressView == null) {
            return;
        }

        millTime = this.time * 1000;

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
                                    listener.onTime(formatsMilliSeconds((time * -1)));
                                }

                            }
                        });
                        Thread.sleep(100);
                    }

                    if (timeCompleteListener != null) {
                        progressView.post(new Runnable() {
                            @Override
                            public void run() {
                                timeCompleteListener.onTimeComplete();
                            }
                        });
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
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

    public interface OnTimerListener {
        void onTime(String time);
    }

    public interface OnTimeCompleteListener {
        void onTimeComplete();
    }

}

