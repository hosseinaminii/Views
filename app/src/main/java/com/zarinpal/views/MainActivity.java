package com.zarinpal.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zarinpal.libs.views.ZarinEditText;
import com.zarinpal.libs.views.ZarinTextView;
import com.zarinpal.libs.views.param.ZarinEditTextType;
import com.zarinpal.libs.views.utitlity.TextUtility;

public class MainActivity extends AppCompatActivity {

    boolean condition = true;
    ZarinEditText zarinEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zarinEditText = findViewById(R.id.edt_zarin);
        final ZarinTextView txtTest = findViewById(R.id.txt_test);
       //zarinEditText.setType(ZarinEditTextType.PAN);


        zarinEditText.setOnTextChangeListener(new ZarinEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(String val) {
                Log.d("TEXTTTTTTTT", "onTextChange: ");
                txtTest.setText(val);
            }
        });

    }

}
