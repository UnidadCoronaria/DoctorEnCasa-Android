package com.unidadcoronaria.doctorencasa.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.HashMap;

public class FontUtil {

    public static boolean setCustomFont(View view, Context ctx, Type fontType) {
        if (fontType == null) {
            return false;
        }
        Typeface tf;
        try {
            tf = getFont(ctx, fontType);
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(tf);
            } else if (view instanceof Button) {
                ((Button) view).setTypeface(tf);
            } else if (view instanceof EditText) {
                ((EditText) view).setTypeface(tf);
            }
        } catch (Exception e) {
            Log.e("FontUtil","Could not get typeface: "+fontType, e);
            return false;
        }

        return true;
    }

    private static final HashMap<Type, SoftReference<Typeface>> fontCache = new HashMap<>();

    public static Typeface getFont(Context c, Type fontType) {
        synchronized (fontCache) {
            if (fontCache.get(fontType) != null) {
                SoftReference<Typeface> ref = fontCache.get(fontType);
                if (ref.get() != null) {
                    return ref.get();
                }
            }
            Typeface typeface = Typeface.createFromAsset(c.getAssets(), fontType.mFontPath);
            fontCache.put(fontType, new SoftReference<>(typeface));
            return typeface;
        }
    }

    public enum Type {
        LIGHT("fonts/Raleway-Light.ttf", 1),
        MEDIUM("fonts/Raleway-Medium.ttf", 2),
        REGULAR("fonts/Raleway-Regular.ttf", 3),
        BOLD("fonts/Raleway-Bold.ttf", 4);

        static final SparseArray<Type> mFontMap = new SparseArray<>(Type.values().length);

        static {
            for (Type fontType : Type.values()) {
                mFontMap.put(fontType.mId, fontType);
            }
        }

        private final String mFontPath;
        private final int mId;

        Type(final String fontPath,
             final int fontId) {
            mFontPath = fontPath;
            mId = fontId;
        }

        public static Type findByFontId(int fontId) {
            return mFontMap.get(fontId);
        }

    }
}
