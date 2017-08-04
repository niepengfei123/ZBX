package com.jy.jz.zbx.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.bean.ZuoFa;

import java.util.List;

/**
 * Created by Mic-roo on 2016/11/29 0029.
 * 做法适配器
 */

public class CookBookMethodAdapter extends BaseAdapter {
    private Context context;
    private List<ZuoFa> zuoFaList;

    public CookBookMethodAdapter(Context context, List<ZuoFa> zuoFaList) {
        this.context = context;
        this.zuoFaList = zuoFaList;
    }

    @Override
    public int getCount() {
        return zuoFaList.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_cookbook_method, null);
            viewHolder.tv_step = (TextView) view.findViewById(R.id.tv_step);
            viewHolder.tv_step_content = (TextView) view.findViewById(R.id.tv_step_content);
            viewHolder.sdv_cookbook_method = (SimpleDraweeView) view.findViewById(R.id.sdv_cookbook_method);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ZuoFa zuoFa = zuoFaList.get(i);


        if (zuoFa != null){
            if(zuoFa.getStep() != null) {
            viewHolder.tv_step.setText("- 第" + zuoFa.getStep() + "步 -");

        }
        if (zuoFa.getD_img() != null) {
            viewHolder.sdv_cookbook_method.setImageURI(Uri.parse(zuoFa.getD_img()));
        }
        if (zuoFa.getD() != null) {
            viewHolder.tv_step_content.setText(zuoFa.getD());
        }

    }
//        if (zuoFa.getDt() == String.valueOf(1)) {
//            viewHolder.tv_step_content.setText(zuoFa.getDt());
//        }

        return view;
    }

    private class ViewHolder {
        private TextView tv_step, tv_step_content;
        private SimpleDraweeView sdv_cookbook_method;
    }
}
