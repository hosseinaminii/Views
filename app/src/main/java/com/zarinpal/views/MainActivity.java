package com.zarinpal.views;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.zarinpal.libs.views.ZarinButton;
import com.zarinpal.libs.views.ZarinItemView;
import com.zarinpal.libs.views.ZarinPinView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ZarinButton btn = findViewById(R.id.btn);

        btn.setBackgroundColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent));

        ZarinPinView zarin_pin = findViewById(R.id.zarin_pin);
        zarin_pin.setOnPinEnteredListener(new ZarinPinView.OnPinEnteredListener() {
            @Override
            public void onPinEntered(CharSequence str) {
                Log.i(TAG, "onPinEntered: " + str);
            }
        });

        final ShapeDrawable oval = new ShapeDrawable(new OvalShape());
        oval.setIntrinsicHeight(200);
        oval.setIntrinsicWidth(200);
        oval.setAlpha(30);
        oval.getPaint().setColor(Color.BLUE);

        ZarinItemView item = findViewById(R.id.item);
        item.setIconBg(oval);
        item.setListener(new ZarinItemView.OnClickItemListener() {
            @Override
            public void onClickItem() {
                Log.i(TAG, "onClickItem: ");
            }
        });


    }

}
