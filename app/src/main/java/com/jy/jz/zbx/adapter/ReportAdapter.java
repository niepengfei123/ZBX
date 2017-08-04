package com.jy.jz.zbx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jy.jz.zbx.R;
import com.jy.jz.zbx.bean.ReportBean;

import java.util.List;

/**
 * Created by Mic-roo on 2016/11/24 0024.
 */

public class ReportAdapter extends BaseAdapter {
    private Context context;
    private List<ReportBean> reportList;

    public ReportAdapter(Context context, List<ReportBean> reportList) {
        this.context = context;
        this.reportList = reportList;
    }

    @Override
    public int getCount() {
        return reportList.size();
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
            viewHolder=new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_report, null);
            viewHolder.img_report = (ImageView) view.findViewById(R.id.img_report);
            viewHolder.tv_names = (TextView) view.findViewById(R.id.tv_names);
            viewHolder.tv_weight = (TextView) view.findViewById(R.id.tv_weight);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ReportBean reportBean = reportList.get(i);
        if (reportBean != null) {
            viewHolder.img_report.setBackgroundResource(reportBean.getImgs());
            viewHolder.tv_names.setText(reportBean.getNames());
            viewHolder.tv_weight.setText(reportBean.getWeight());
        }
        return view;
    }

    private class ViewHolder {
        private TextView tv_names, tv_weight;
        private ImageView img_report;
    }
}
