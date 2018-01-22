package com.zarinpal.views;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zarinpal.libs.views.ZarinButton;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ZarinButton btn = findViewById(R.id.btn);

        btn.setBackgroundColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent));

    }

}
