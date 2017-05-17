package com.qiming.wcq.mymapdemo.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/12/15.
 */

public class FontsTextView extends TextView {
    public FontsTextView(Context context) {
        super(context);
    }

    public FontsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        changeTypeFace(context,attrs);
    }

    public FontsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 改变字体类型
     * @param context
     * @param attrs
     */
    private void changeTypeFace(Context context, AttributeSet attrs)
    {
        if (attrs != null)
        {
            //TypedArray a = context.obtainStyledAttributes(attrs,
            //R.styleable.TextView_Typefaces);
            //            tf = a.getInt(R.styleable.TextView_Typefaces_tf, tf);
            Typeface mtf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/hyxls.ttf");
            super.setTypeface(mtf);
        }
    }
}
