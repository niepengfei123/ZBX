package com.jy.jz.zbx.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jy.jz.zbx.R;

/**
 * Created by lijin on 2016/7/27.
 * 弹框管理类
 */
public class DialogUtil {


    private static DialogUtil instance;
    public static Dialog builder;

    private DialogUtil() {
    }

    public static synchronized DialogUtil getInstance(Context context) {
        if (instance == null) {
            instance = new DialogUtil();
            builder = new AlertDialog.Builder(context).create();
        }
        return instance;
    }


    /**
     * 对话框
     * @param mContext
     * @param dialogType  弹框类型 0是通知类型的，无操作  ；1是带有确定取消按钮
     * @param callback
     * @param title  标题
     * @param isShowTitle  是否显示标题
     * @param titleSize  标题文字大小
     * @param description  提醒的文字
     * @param i_kown_btn_text  提醒的文字
     */
    @SuppressWarnings("deprecation")
    public void showIKnowDialog(Context mContext , int dialogType, final SelectCallBack callback, String title, boolean isShowTitle , int titleSize, String description, String i_kown_btn_text) {
        LayoutInflater factory = LayoutInflater.from(mContext);
        View view = factory.inflate(R.layout.dialog_util, null);
        builder.setCanceledOnTouchOutside(false);
        LinearLayout cancel_ok_box = (LinearLayout) view.findViewById(R.id.cancel_ok_box);
        LinearLayout i_kown_box = (LinearLayout) view.findViewById(R.id.i_kown_box);
        TextView i_kown = (TextView) view.findViewById(R.id.i_kown);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView ok = (TextView) view.findViewById(R.id.ok);
        TextView dialog_title = (TextView) view.findViewById(R.id.dialog_title);
        View dialog_title_lin = view.findViewById(R.id.dialog_title_lin);
        TextView description1 = (TextView) view.findViewById(R.id.description1);
        dialog_title.setText(title);
        description1.setText(description);
        if(!builder.isShowing()){
            if(dialogType == 0){//异常提醒
                cancel_ok_box.setVisibility(View.GONE);
                i_kown_box.setVisibility(View.VISIBLE);
                i_kown.setText(i_kown_btn_text);
            }else{//停止弹框提醒
                cancel_ok_box.setVisibility(View.VISIBLE);
                i_kown_box.setVisibility(View.GONE);
            }
            if(isShowTitle){
                dialog_title.setVisibility(View.VISIBLE);
//				dialog_title_lin.setVisibility(View.VISIBLE);
            }else{
                dialog_title.setVisibility(View.GONE);
                dialog_title_lin.setVisibility(View.GONE);
                description1.setTextSize(titleSize);
                description1.setTextColor(0xff656565);
            }
            builder.show();//必须在调用了dialog.setContentView()或者dialog.getwindow()之前调用
            Window window = builder.getWindow();
            window.setGravity(Gravity.CENTER);
            WindowManager m = ((Activity) mContext).getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
            p.width = (int) (d.getWidth() * 0.85); // 宽度设置为屏幕的0.65
            p.alpha = 1.00f;
            window.setAttributes(p);
            window.setContentView(view);
            window.setWindowAnimations(R.style.dialogWindowAnim);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                instance = null;
                builder = null;
                callback.selectNo();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                instance = null;
                builder = null;
                callback.selectYes();
            }
        });

        i_kown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                instance = null;
                builder = null;
                callback.selectNormal();
            }
        });
    }

    /**
     * 点击按钮后的回调
     * @author Administrator
     *
     */
    public interface SelectCallBack {
        /**
         * 点击确认按钮后的回调
         */
        void selectYes();

        /**
         * 点击取消按钮后的回调
         */
        void selectNo();

        /**
         * 点击其他按钮后的回调
         */
        void selectNormal();
    }
}
