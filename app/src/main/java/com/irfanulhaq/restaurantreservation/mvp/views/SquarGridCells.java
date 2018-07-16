package com.irfanulhaq.restaurantreservation.mvp.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SquarGridCells extends FrameLayout{
    public SquarGridCells(@NonNull Context context) {
        super(context);
    }

    public SquarGridCells(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquarGridCells(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
