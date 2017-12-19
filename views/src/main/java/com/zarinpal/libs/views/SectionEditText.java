package com.zarinpal.libs.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zarinpal.libs.views.utitlity.UnitUtility;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialedittext.MaterialEditText;

/**
 * Section EditText Created by farshid roohi on 12/19/17.
 */

public class SectionEditText extends LinearLayout {

    private ViewGroup layoutRoot;
    private int itemCount;
    private int maxLen;
    private Integer itemWidth;
    private Integer itemHeight;
    private Integer itemMargin;
    private boolean hasPassword;
    private Drawable backgroundDrawable;
    private Integer textColor;
    private List<ZarinEditText> editTextList;

    public SectionEditText(Context context) {
        super(context);
    }

    public SectionEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SectionEditText);
        this.itemCount = typedArray.getInteger(R.styleable.SectionEditText_zp_pinCount, 4);
        this.itemHeight = typedArray.getDimensionPixelSize(R.styleable.SectionEditText_zp_heightItem, 50);
        this.itemWidth = typedArray.getDimensionPixelSize(R.styleable.SectionEditText_zp_widthItem, 50);
        this.itemMargin = typedArray.getDimensionPixelSize(R.styleable.SectionEditText_zp_marginItem, 10);
        this.maxLen = typedArray.getInt(R.styleable.SectionEditText_android_maxLength, 1);
        this.hasPassword = typedArray.getBoolean(R.styleable.SectionEditText_zp_hasPassword, false);
        this.backgroundDrawable = typedArray.getDrawable(R.styleable.SectionEditText_zp_backgroundDrawable);
        this.textColor = typedArray.getColor(R.styleable.SectionEditText_android_textColor, Color.BLACK);

        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.initializeView();
    }

    private void initializeView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pin_view, this, true);
        this.layoutRoot = view.findViewById(R.id.layout_root);
        this.editTextList = new ArrayList<>();
        addView();
    }

    private void addView() {

        for (int i = 0; i <= this.itemCount; i++) {
            final ZarinEditText editText = new ZarinEditText(getContext());
            MaterialEditText edt = editText.getEditText();
            editText.setTag(i);

            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter.LengthFilter(this.maxLen);
            edt.setFilters(filters);
            edt.setGravity(Gravity.CENTER);
            edt.setTextColor(this.textColor);
            // handled background user
            edt.setBackground(null);
            if (this.backgroundDrawable == null) {

            } else {
                edt.setBackgroundDrawable(this.backgroundDrawable);
            }


            // check final item for done action
            if (i == itemCount) {
                edt.setImeOptions(EditorInfo.IME_ACTION_DONE);
            } else {
                edt.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            }

            // Check User input Password
            if (this.hasPassword) {
                edt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                edt.setInputType(InputType.TYPE_CLASS_TEXT);
            }

            // set item size
            LayoutParams params = new LayoutParams((int) UnitUtility.dpToPx(getContext(), this.itemWidth)
                    , (int) UnitUtility.dpToPx(getContext(), this.itemHeight));

            params.setMargins(this.itemMargin, this.itemMargin, this.itemMargin, this.itemMargin);
            this.layoutRoot.addView(editText, params);

            // add view to root layout
            this.editTextList.add(editText);

            final int index = i;

            // handled event type user
            edt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String value = editable.toString();

                    if ((value.isEmpty()) && (index != 0)) {
                        editTextList.get((index - 1)).requestFocus();
                    }

                    if ((value.isEmpty()) || (value.length() < maxLen)) {
                        return;
                    }

                    if (editTextList.size() > (index + 1)) {
                        editTextList.get(index + 1).requestFocus();
                    }

                }
            });
        }
    }
}
