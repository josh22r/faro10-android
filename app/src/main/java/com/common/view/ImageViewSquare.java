package com.common.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


/**
 * @author VaViAn Labs.
 */
public class ImageViewSquare extends AppCompatImageView {

    public ImageViewSquare(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public ImageViewSquare(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewSquare(Context context) {
        super(context);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
};