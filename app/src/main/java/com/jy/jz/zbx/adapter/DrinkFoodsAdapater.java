package com.jy.jz.zbx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.bean.MarketBean;

import java.util.List;

/**
 * Created by Mic-roo on 2016/12/2 0002.
 */

public class DrinkFoodsAdapater extends BaseAdapter {
    private Context context;
    private List<MarketBean> marketList;

    public DrinkFoodsAdapater(Context context, List<MarketBean> marketList) {
        this.context = context;
        this.marketList = marketList;
    }

    @Override
    public int getCount() {
        return marketList.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_drink_foods, null);
            viewHolder.sdv_market = (SimpleDraweeView) view.findViewById(R.id.sdv_market);
            viewHolder.tv_foods_name = (TextView) view.findViewById(R.id.tv_foods_name);
            viewHolder.tv_foods_price = (TextView) view.findViewById(R.id.tv_foods_price);
            viewHolder.tv_distance = (TextView) view.findViewById(R.id.tv_distance);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        MarketBean marketBean = marketList.get(i);
        if (marketBean != null) {
            viewHolder.sdv_market.setImageResource(marketBean.getImgs());
            viewHolder.tv_foods_name.setText(marketBean.getName());
            viewHolder.tv_foods_price.setText(marketBean.getPric());
            viewHolder.tv_distance.setText(marketBean.getDistance());
        }

        return view;
    }

    private class ViewHolder {
        private SimpleDraweeView sdv_market;
        private TextView tv_foods_name, tv_foods_price, tv_distance;
    }
}
