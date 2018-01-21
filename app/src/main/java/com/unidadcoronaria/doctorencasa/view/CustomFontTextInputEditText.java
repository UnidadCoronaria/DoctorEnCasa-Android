package com.unidadcoronaria.doctorencasa.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class CustomFontTextInputEditText extends TextInputEditText {

    public CustomFontTextInputEditText(Context context) {
        super(context);
    }

    public CustomFontTextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFontTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTypeface(Typeface typeface, int style) {
        super.setTypeface(FontUtil.getFont(getContext(), FontUtil.Type.MEDIUM), style);
    }

    @Override
    public void setTypeface(Typeface typeface) {
        super.setTypeface(FontUtil.getFont(getContext(), FontUtil.Type.MEDIUM));
    }
}
