package com.zarinpal.libs.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zarinpal.libs.views.utitlity.FontUtility;
import com.zarinpal.libs.views.utitlity.UnitUtility;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialedittext.MaterialEditText;

/**
 * Section EditText Created by farshid roohi on 12/19/17.
 */

public class SectionEditText extends LinearLayout {

    public static final String TAG = SectionEditText.class.getSimpleName();

    private ViewGroup          layoutRoot;
    private int                itemCount;
    private int                maxLen;
    private Integer            itemWidth;
    private Integer            itemHeight;
    private Integer            itemMargin;
    private boolean            hasPassword;
    private Drawable           backgroundDrawable;
    private Integer            textColor;
    private List<EditText>     editTextList;
    private OnCompleteListener listener;

    public SectionEditText(Context context) {
        super(context);
    }

    public SectionEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SectionEditText);
        this.itemCount = typedArray.getInteger(R.styleable.SectionEditText_zp_pinCount, 4);
        this.itemHeight = typedArray.getDimensionPixelSize(R.styleable.SectionEditText_zp_heightItem, 30);
        this.itemWidth = typedArray.getDimensionPixelSize(R.styleable.SectionEditText_zp_widthItem, 20);
        this.itemMargin = typedArray.getDimensionPixelSize(R.styleable.SectionEditText_zp_marginItem, 2);
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

        for (int i = 0; i < this.itemCount; i++) {
            final int index = i;
            // initialize View Item
            RelativeLayout relativeLayout = new RelativeLayout(getContext());
            final EditText edt            = new EditText(getContext());
//            final MaterialEditText edt = editText.getEditText();
            final ImageView imageView = new ImageView(getContext());
            edt.setTypeface(FontUtility.getFont(getContext(), FontUtility.IRANSANS_BOLD));

            // set (Max length , Color Hint , Gravity , TextColor , inputType,action keyboard) ->  To Edit Text
            this.setAttrEditText(edt, index);


            // handled background user
            this.handledBackground(edt, relativeLayout, imageView);


            // Add EditText and getParam()  -> {size , margin}
            relativeLayout.addView(edt, getParam());

            // add view to root layout
            this.layoutRoot.addView(relativeLayout);
            this.editTextList.add(edt);

            // Check focus EditText for Visibility ImageView
            edt.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean isFocused) {
                    if (backgroundDrawable != null && isFocused) {
                        imageView.setVisibility(GONE);
                        if (!getText().isEmpty()) {
                            return;
                        }
                        editTextList.get(0).requestFocus();
                        return;
                    }
                    if (edt.getText().toString().isEmpty()) {
                        imageView.setVisibility(VISIBLE);
                    }
                }
            });


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

                    handledUserType(editable.toString(), index);
                }
            });

            edt.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                    if (keyEvent.getAction() != KeyEvent.ACTION_DOWN) {
                        return false;
                    }
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (edt.getText().toString().isEmpty() && (index != 0)) {
                            getLastItem(index);
                        }
                    }
                    return false;
                }
            });
        }
    }

    private void handledUserType(String value, int index) {

        if ((!value.isEmpty()) && (index + 1 == editTextList.size())) {
            if (this.listener != null) {
                this.listener.onComplete(getText());
            }

        }

        // check if user input empty And not first Item requested last Item focus
        if ((value.isEmpty()) && (index != 0)) {
            this.getLastItem(index);
        }

        // check if user input empty Or  user input length fewer max Length return
        if ((value.isEmpty()) || (value.length() < maxLen)) {
            return;
        }

        // check EditText ArrayList Size Bigger off (index + 1) requested next Item focus
        if (!(value.isEmpty()) && (editTextList.size() > (index + 1))) {
            this.getNextItem(index);
        }

    }

    private void getLastItem(int index) {
        this.editTextList.get((index - 1)).requestFocus();
    }

    private void getNextItem(int index) {
        editTextList.get(index + 1).requestFocus();
    }

    private LayoutParams getParam() {

        // set Size Item , Margin
        LayoutParams params = new LayoutParams((int) UnitUtility.dpToPx(getContext(), this.itemWidth)
                , (int) UnitUtility.dpToPx(getContext(), this.itemHeight));

        params.setMargins(this.itemMargin, this.itemMargin, this.itemMargin, this.itemMargin);

        return params;
    }

    private void handledBackground(EditText edt, RelativeLayout layout, ImageView img) {
        // Check Exist Drawable Background for EditText
        if (this.backgroundDrawable != null) {
            img.setBackground(this.backgroundDrawable);
            // set height , width imageView
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams((int) UnitUtility.dpToPx(getContext(),
                            30), (int) UnitUtility.dpToPx(getContext(), 2));
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            img.setLayoutParams(params);
            edt.setBackground(null);
            layout.addView(img);
        }
    }

    private void setAttrEditText(EditText edt, int index) {

        // set Max length To Edit Text
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(this.maxLen);

        // set Color Hint And Gravity , TextColor
        edt.setFilters(filters);
        edt.setGravity(Gravity.CENTER);
        edt.setTextColor(this.textColor);

        // Check User input Password
        if (this.hasPassword) {
            edt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            edt.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        // set Action soft keyboard
        if (index == itemCount) {
            edt.setImeOptions(EditorInfo.IME_ACTION_DONE);
        } else {
            edt.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        }
    }

    public String getText() {
        String str = "";
        if (this.editTextList == null || this.editTextList.size() <= 0) {
            return str;
        }
        for (EditText edt : this.editTextList) {
            str = str + edt.getText();
        }

        return str;
    }

    public void setListener(OnCompleteListener listener) {
        this.listener = listener;
    }

    public interface OnCompleteListener {
        void onComplete(String value);
    }
}
