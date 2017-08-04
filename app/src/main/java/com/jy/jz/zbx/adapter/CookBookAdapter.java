package com.jy.jz.zbx.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.bean.CookBookBean;
import com.jy.jz.zbx.bean.Data;

import java.util.List;

/**
 * Created by Mic-roo on 2016/11/24 0024.
 */

public class CookBookAdapter extends BaseAdapter {
    private Context context;
    //    private List<CookBookBean> cookBookList;
    private List<Data> cookBookList;

    public CookBookAdapter(Context context, List<Data> cookBookList) {
        this.context = context;
        this.cookBookList = cookBookList;
    }

    @Override
    public int getCount() {
        return cookBookList.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_cookbook, null);
            viewHolder.img_cookbook = (SimpleDraweeView) view.findViewById(R.id.img_cookbook);
            viewHolder.tv_cookbook_name = (TextView) view.findViewById(R.id.tv_cookbook_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Data cookBookBean = cookBookList.get(i);
        if (cookBookBean != null) {
            if (cookBookBean.getTitlepic() != null) {
                viewHolder.img_cookbook.setImageURI(Uri.parse(cookBookBean.getTitlepic()));
            }
            viewHolder.tv_cookbook_name.setText(cookBookBean.getTitle());
        }

        return view;
    }

    private class ViewHolder {
        public SimpleDraweeView img_cookbook;
        public TextView tv_cookbook_name;

    }
}
