package com.zarinpal.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zarinpal.libs.views.ZarinEditText;
import com.zarinpal.libs.views.utitlity.TextUtility;

public class MainActivity extends AppCompatActivity {

    boolean condition = true;
    ZarinEditText zarinEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zarinEditText = findViewById(R.id.edt_zarin);
        zarinEditText.getLeftFirstIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

//        Log.d("WATCHER", TextUtility.convertToCurrency("۲").toString());


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("WATCHER", TextUtility.convertToCurrency(zarinEditText.getText()).toString());
            }
        });

    }

}
