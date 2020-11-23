package com.example.pkmusic.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WEqualsHImageView extends androidx.appcompat.widget.AppCompatImageView {
    public WEqualsHImageView(@NonNull Context context) {
        super(context);
    }

    public WEqualsHImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WEqualsHImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
     /*
        //获取View宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //获取View模式
        int mode = MeasureSpec.getMode(widthMeasureSpec)
      */

    }

}
