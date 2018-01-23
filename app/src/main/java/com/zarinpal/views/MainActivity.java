package com.zarinpal.views;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zarinpal.libs.views.ZarinButton;
import com.zarinpal.libs.views.ZarinItemView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ZarinButton btn = findViewById(R.id.btn);

        btn.setBackgroundColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent));
        
       ZarinItemView itemView =  findViewById(R.id.item);
        itemView.setEnabled(true);
       itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Log.i(TAG, "onClick: ");
           }
       });

    }

}
