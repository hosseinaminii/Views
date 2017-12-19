package com.zarinpal.libs.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import com.zarinpal.libs.views.utitlity.UnitUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Zarin Pin View Created by farshid roohi on 12/19/17.
 */


public class ZarinPinView extends LinearLayout {

    private ViewGroup layoutRoot;
    private int pinCount;
    private int maxLen;
    private Integer itemWidth;
    private Integer itemHeight;
    private Integer itemMargin;
    private boolean hasPassword;

    private List<ZarinEditText> editTextList;

    public ZarinPinView(Context context) {
        super(context);
    }

    public ZarinPinView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PinView);
        this.pinCount = typedArray.getInteger(R.styleable.PinView_zp_pinCount, 4);
        this.itemHeight = typedArray.getDimensionPixelSize(R.styleable.PinView_zp_heightItem, 50);
        this.itemWidth = typedArray.getDimensionPixelSize(R.styleable.PinView_zp_widthItem, 50);
        this.itemMargin = typedArray.getDimensionPixelSize(R.styleable.PinView_zp_marginItem, 10);
        this.maxLen = typedArray.getInt(R.styleable.PinView_zp_maxLen, 1);
        this.hasPassword = typedArray.getBoolean(R.styleable.PinView_zp_hasPassword, false);

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

        for (int i = 0; i <= this.pinCount; i++) {
            final ZarinEditText pin = new ZarinEditText(getContext());
            pin.setTag(i);

            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter.LengthFilter(this.maxLen);
            pin.getEditText().setFilters(filters);
            pin.getEditText().setGravity(Gravity.CENTER);

            if (i == pinCount) {
                pin.getEditText().setImeOptions(EditorInfo.IME_ACTION_DONE);
            } else {
                pin.getEditText().setImeOptions(EditorInfo.IME_ACTION_NEXT);
            }

            if (this.hasPassword) {
                pin.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                pin.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
            }

            LayoutParams params = new LayoutParams((int) UnitUtility.dpToPx(getContext(), this.itemWidth)
                    , (int) UnitUtility.dpToPx(getContext(), this.itemHeight));
            params.setMargins(this.itemMargin, this.itemMargin, this.itemMargin, this.itemMargin);
            this.layoutRoot.addView(pin, params);

            this.editTextList.add(pin);

            final int index = i;
            pin.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {

                    if (!(editable.toString().isEmpty()) && editable.toString().length() >= maxLen) {
                        if (editTextList.size() > index + 1) {
                            editTextList.get(index + 1).requestFocus();
                        }
                    } else if (editable.toString().isEmpty()) {
                        int last_index = (index - 1) < 0 ? 0 : (index - 1);
                        editTextList.get(last_index).requestFocus();
                    }
                }
            });
            pin.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    return false;
                }
            });
        }

    }
}
