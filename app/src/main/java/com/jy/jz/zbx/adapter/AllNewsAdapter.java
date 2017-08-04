package com.jy.jz.zbx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jy.jz.zbx.R;
import com.jy.jz.zbx.bean.AllNewsBean;

import java.util.List;

/**
 * Created by Mic-roo on 2016/11/25 0025.
 */

public class AllNewsAdapter extends BaseAdapter {
    private Context context;
    private List<AllNewsBean> allNewsList;


    public AllNewsAdapter(Context context, List<AllNewsBean> allNewsList) {
        this.context = context;
        this.allNewsList = allNewsList;
    }

    @Override
    public int getCount() {
        return allNewsList.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_all_news, null);
            viewHolder.tv_news_name = (TextView) view.findViewById(R.id.tv_news_name);
            viewHolder.tv_news_time = (TextView) view.findViewById(R.id.tv_news_time);
            viewHolder.tv_news_btn = (TextView) view.findViewById(R.id.tv_news_btn);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        AllNewsBean allNewsBean = allNewsList.get(i);
        if (allNewsBean != null) {
            viewHolder.tv_news_name.setText(allNewsBean.getNames());
            viewHolder.tv_news_time.setText(allNewsBean.getTimes());
            viewHolder.tv_news_btn.setText(allNewsBean.getBtn());
        }

        return view;
    }

    private class ViewHolder {
        private TextView tv_news_btn, tv_news_time, tv_news_name;
    }
}
