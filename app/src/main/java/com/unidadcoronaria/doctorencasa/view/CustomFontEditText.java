package com.unidadcoronaria.doctorencasa.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.EditText;

public class CustomFontEditText extends AppCompatEditText {

    public CustomFontEditText(Context context) {
        super(context);
    }

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
