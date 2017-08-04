package com.jy.jz.zbx.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.jz.zbx.R;
import com.jy.jz.zbx.application.ZBXApplication;
import com.jy.jz.zbx.bean.AddNames;
import com.jy.jz.zbx.service.Address;
import com.jy.jz.zbx.service.SlideDelete;
import com.jy.jz.zbx.utils.ConfigUtils;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Mic-roo on 2016/12/9 0009.
 */

public class AddAdapter extends BaseAdapter {
    private Context context;
    List<Address> data = new ArrayList<>();
    private List<SlideDelete> slideDeleteArrayList = new ArrayList<>();
    private int num;
    private float upX, downX;

    public AddAdapter(Context context, List<Address> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null || convertView.getTag() == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.solids_item1, parent, false);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_names);
            viewHolder.tv_days = (TextView) convertView.findViewById(R.id.tv_days);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (data.get(i).getStartAdd() != null) {
            viewHolder.tv_name.setText(data.get(i).getStartAdd());
            viewHolder.tv_days.setText("已存1天  剩余6天");
//            viewHolder.mTvContent.setText(data.get(i).getStartAdd());
        }

        return convertView;
    }


    class ViewHolder {
                TextView tv_name;
        TextView tv_days;

    }

}
