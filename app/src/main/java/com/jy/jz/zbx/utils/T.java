package com.jy.jz.zbx.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Toast帮助类
 * Created by lijin on 2016/7/27.
 *
 */
public class T {

    /**
     *
     * @param context 上下文
     * @param info  显示信息
     */
    public static void showshort(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param context 上下文
     * @param info  显示信息
     */
    public static void showlong(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }

    /**
     *
     * @param context 上下文
     * @param info  显示信息
     * @param gravity  在屏幕中显示的位置
     * @param xOffset  相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移
     * @param yOffset  相对于第一个参数设置toast位置的横向Y轴的偏移量，正数向上偏移，负数向下偏移
     * @param showTime toast显示时间
     */
    public static void showLocation(Context context, String info,Integer gravity,Integer xOffset,Integer yOffset,Integer showTime) {
        Toast toast = Toast.makeText(context, info, showTime);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.show();
    }

    /**
     *
     * @param context 上下文
     * @param info  显示信息
     * @param gravity  在屏幕中显示的位置
     * @param xOffset  相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移
     * @param yOffset  相对于第一个参数设置toast位置的横向Y轴的偏移量，正数向上偏移，负数向下偏移
     * @param resourceId 图片资源
     * @param showTime toast显示时间
     */
    public static void showLocationAndImage(Context context, String info,
                                            Integer gravity, Integer xOffset, Integer yOffset, Integer resourceId,Integer showTime) {
        Toast toast = Toast.makeText(context, info, showTime);
        toast.setGravity(gravity, xOffset, yOffset);
        ImageView imageView = new ImageView(context);
        // 设置图片
        imageView.setImageResource(resourceId);
        // 获得toast的布局
        LinearLayout toastView = (LinearLayout) toast.getView();
        // 设置此布局为横向的
//		toastView.setOrientation(LinearLayout.HORIZONTAL);
        toastView.setOrientation(LinearLayout.VERTICAL);
        // 将ImageView在加入到此布局中的第一个位置
        toastView.addView(imageView, 0);
        toast.show();
    }
}
