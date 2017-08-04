package com.jy.jz.zbx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jy.jz.zbx.R;
import com.jy.jz.zbx.activity.ImgData;
import com.jy.jz.zbx.activity.ImgObjects;
import com.jy.jz.zbx.activity.Recognitioninfos;

import java.util.List;

/**
 * Created by Mic-roo on 2016/12/6 0006.
 * 图像识别适配器
 */

public class ImageRecognitionAdapter extends BaseAdapter {
    private Context context;
    private List<ImgObjects> imgDataList;

    public ImageRecognitionAdapter(Context context, List<ImgObjects> imgDataList) {
        this.context = context;
        this.imgDataList = imgDataList;
    }

    @Override
    public int getCount() {
        return imgDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_image_recognition, null);
            viewHolder.tv_recognition_name = (TextView) view.findViewById(R.id.tv_recognition_name);
            viewHolder.tv_recognition_days = (TextView) view.findViewById(R.id.tv_recognition_days);
            viewHolder.tv_recognition_day = (TextView) view.findViewById(R.id.tv_recognition_day);
            viewHolder.img_recognition = (ImageView) view.findViewById(R.id.img_recognition);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ImgObjects recognitioninfos = imgDataList.get(i);

        if (recognitioninfos != null) {
            if (recognitioninfos.getChineseIdeograph() != null) {
                if (recognitioninfos.getChineseIdeograph().equals("灌装啤酒-哈尔滨")) {
                    viewHolder.tv_recognition_name.setText("啤酒");
                } else {
                    viewHolder.tv_recognition_name.setText(recognitioninfos.getChineseIdeograph());
                }
            }
            if (String.valueOf(recognitioninfos.getSaveDays()) != null) {
                viewHolder.tv_recognition_days.setText("已存" + recognitioninfos.getSaveDays() + "天");
            }
            if (String.valueOf(recognitioninfos.getExpDay()) != null) {
                viewHolder.tv_recognition_day.setText("剩余" + recognitioninfos.getExpDay() + "天");
            }
            if (recognitioninfos.getName() != null) {
                switch (recognitioninfos.getName()) {
                    case "li":
                        viewHolder.img_recognition.setBackgroundResource(R.mipmap.li);
                        break;
                    case "pingguo":
                        viewHolder.img_recognition.setImageResource(R.mipmap.pingguo);
                        break;
                    case "qiezi":
                        viewHolder.img_recognition.setImageResource(R.mipmap.qiezi);
                        break;
                    case "yumi":
                        viewHolder.img_recognition.setImageResource(R.mipmap.yumi);
                        break;
                    case "xilanhua":
                        viewHolder.img_recognition.setImageResource(R.mipmap.xilanhua);
                        break;
                    case "doufu":
                        viewHolder.img_recognition.setImageResource(R.mipmap.doufu);
                        break;
                    case "jidan":
                        viewHolder.img_recognition.setImageResource(R.mipmap.jidan);
                        break;
                    case "xuebi":
                        viewHolder.img_recognition.setImageResource(R.mipmap.yingliao);
                        break;
                    case "haerbin":
                        viewHolder.img_recognition.setImageResource(R.mipmap.pijiu);
                        break;
                    case "hongjiu":
                        viewHolder.img_recognition.setImageResource(R.mipmap.putaojiu);
                        break;
                    case "yiliniunai":
                        viewHolder.img_recognition.setImageResource(R.mipmap.niunai);
                        break;
                    case "wawacai":
                        viewHolder.img_recognition.setImageResource(R.mipmap.baicai);
                        break;
                }
            }


        }
        return view;
    }

    private class ViewHolder {
        private TextView tv_recognition_name, tv_recognition_day, tv_recognition_days;
        private ImageView img_recognition;

    }
}
