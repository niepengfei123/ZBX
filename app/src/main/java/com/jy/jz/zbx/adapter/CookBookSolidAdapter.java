package com.jy.jz.zbx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jy.jz.zbx.R;
import com.jy.jz.zbx.bean.Liaos;
import com.jy.jz.zbx.bean.Lists;

import java.util.List;

/**
 * Created by Mic-roo on 2016/11/29 0029.
 * 食材适配器
 */

public class CookBookSolidAdapter extends BaseAdapter {
    private Context context;
    private List<Lists> liaosList;

    public CookBookSolidAdapter(Context context, List<Lists> liaosList) {
        this.context = context;
        this.liaosList = liaosList;
    }

    @Override
    public int getCount() {
        return liaosList.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_cookbook_solid, null);
            viewHolder.tv_solid_name = (TextView) view.findViewById(R.id.tv_solid_name);
            viewHolder.tv_solid_we = (TextView) view.findViewById(R.id.tv_solid_we);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Lists liaos = liaosList.get(i);
        if (liaos != null) {
            if (liaos.getTitle() != null) {
                viewHolder.tv_solid_name.setText(liaos.getTitle());

            }
            if (liaos.getNum() != null) {
                viewHolder.tv_solid_we.setText(liaos.getNum());
            } else if (liaos.getUnit() != null) {
                viewHolder.tv_solid_we.setText(liaos.getUnit());
            }


        }

        return view;
    }

    private class ViewHolder {
        private TextView tv_solid_name, tv_solid_we;
    }
}
