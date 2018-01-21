package com.unidadcoronaria.doctorencasa.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import com.unidadcoronaria.doctorencasa.R;


/**
 * @author Agustin.Bala
 * @since 0.1
 * <p>
 * Custom implementation of TextView to handle font
 */
public class CustomButton extends Button {

    private static final int FONT_NOT_FOUND = 1;


    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public CustomButton(Context context) {
        super(context);
    }

    private void init(Context context, AttributeSet attrs) {
        int fontId;

        if (attrs != null) {
            try {
                TypedArray a = context.obtainStyledAttributes(
                        attrs,
                        R.styleable.visual_CustomTextView,
                        0, 0);

                fontId = a.getInteger(R.styleable.visual_CustomTextView_visual_font_name, FONT_NOT_FOUND);

                a.recycle();
            } catch (Exception e) {
                fontId = FONT_NOT_FOUND;
                Log.e("CustomTextView", "Unable to Read Font Attribute", e);
            }

            FontUtil.Type fontType = FontUtil.Type.findByFontId(fontId);
            setFont(fontType);
        }
    }

    public void setFont(FontUtil.Type fontType) {
        FontUtil.setCustomFont(this, getContext(), fontType);
    }
}