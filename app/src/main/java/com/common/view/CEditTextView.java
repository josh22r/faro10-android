package com.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;

import com.kartum.R;
import com.kartum.utils.Utils;


/**
 * @author VaViAn Labs.
 */
public class CEditTextView extends AppCompatEditText {

    private boolean disableEmoji = true; // disable emoji and some special symbol input.
    private int maxLength = -1;

    public CEditTextView(Context context) {
        super(context);
        init();
    }

    public CEditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle); // Attention here !
        init();
    }

    public CEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XEditText, defStyleAttr, 0);

        disableEmoji = a.getBoolean(R.styleable.XEditText_x_disableEmoji, true);
        maxLength = a.getInteger(R.styleable.XEditText_x_length, -1);
        a.recycle();

        init();
    }

    public void init() {
        if (!isInEditMode()) {
            try {
                setTypeface(Utils.getNormal(getContext()));
            } catch (Exception e) {

            }
        }

        if (maxLength != -1) {
            setFilters(new InputFilter[]{new EmojiExcludeFilter(), new InputFilter.LengthFilter(maxLength)});
        } else if (disableEmoji) {
            setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        }
    }

    /**
     * disable emoji and special symbol input
     */
    private class EmojiExcludeFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    }

};