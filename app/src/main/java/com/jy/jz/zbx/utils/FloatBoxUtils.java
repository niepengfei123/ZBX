package com.jy.jz.zbx.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jy.jz.zbx.R;

/**
 * Created by lijin on 2016/7/26.
 * 浮动的提示栏
 */
public class FloatBoxUtils {
    public static View showNotNetView(final Activity ac, String msg) {
        FrameLayout fl = (FrameLayout) ac.getWindow().getDecorView();
        TextView tv = new TextView(ac);
        tv.setAlpha(1f);
        tv.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        tv.setHeight(ac.getResources().getDimensionPixelSize(R.dimen.float_box_height));
        tv.setPadding(ac.getResources().getDimensionPixelSize(R.dimen.float_box_text_padding), 0, 0, 0);
        tv.setGravity(Gravity.CENTER | Gravity.LEFT);
        tv.setTextColor(Color.WHITE);
        tv.setText(msg);
        tv.setBackgroundColor(ac.getResources().getColor(R.color.gray));
        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = ac.getResources().getDimensionPixelSize(R.dimen.float_box_margintop);
        fl.addView(tv, params);
        return tv;

    }

    public static View dissmisNotNetView(Activity ac, View v) {
        FrameLayout fl = (FrameLayout) ac.getWindow().getDecorView();
        if (fl.indexOfChild(v) >= 0) {
            fl.removeView(v);
            v = null;
        }
        return null;

    }
}
