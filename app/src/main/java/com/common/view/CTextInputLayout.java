package com.common.view;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;

import com.kartum.utils.Utils;

public class CTextInputLayout extends TextInputLayout {
    public CTextInputLayout(Context context) {
        super(context);
        init();
    }

    public CTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        if (!isInEditMode()) {
            try {
                setTypeface(Utils.getNormal(getContext()));
            } catch (Exception e) {

            }
        }
    }
}
