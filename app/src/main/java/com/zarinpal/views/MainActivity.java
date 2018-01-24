package com.zarinpal.views;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zarinpal.libs.views.ZarinButton;
import com.zarinpal.libs.views.ZarinItemView;
import com.zarinpal.libs.views.ZarinTimer;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ZarinTimer timer = findViewById(R.id.timer);
        
        timer.startTimer(new ZarinTimer.OnTimerListener() {
            @Override
            public void onTime(String time) {
                Log.d(TAG, time);
            }
        });
        
        timer.setOnTimeCompleteListener(new ZarinTimer.OnTimeCompleteListener() {
            @Override
            public void onTimeComplete() {
                Log.d(TAG, "onTimeComplete: ");
            }
        });
        
        
    }

}
