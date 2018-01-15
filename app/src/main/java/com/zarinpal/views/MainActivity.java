package com.zarinpal.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zarinpal.libs.views.ZarinButton;
import com.zarinpal.libs.views.ZarinProgressViewHorizontal;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ZarinButton btn = findViewById(R.id.btn);

        btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent));


        ZarinProgressViewHorizontal zarinProgress = findViewById(R.id.zarin_progress);
        zarinProgress.startProgress(new ZarinProgressViewHorizontal.CounterProgressListener() {
            @Override
            public void onCounter(String time) {

            }
        });

    }

}
