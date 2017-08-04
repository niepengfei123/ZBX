package com.jy.jz.zbx.dms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jy.jz.zbx.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Admin 选择设备型号
 */
public class FindEquiAdapter extends BaseAdapter {

  private Context context;
  private List<ModuleInfo> usableList = new ArrayList<ModuleInfo>();

  public FindEquiAdapter(Context context, List<ModuleInfo> usableList) {
    this.context = context;
    this.usableList = usableList;
  }

  @Override
  public int getCount() {
    if (usableList != null) {
      return usableList.size();
    } else {
      return 0;
    }
  }

  @Override
  public Object getItem(int arg0) {
    return usableList.get(arg0);
  }

  @Override
  public long getItemId(int arg0) {
    return 0;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    final ViewHolder holder;
    if (convertView == null) {
      LayoutInflater inflater = (LayoutInflater) context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.v2_find_dev_list_item, parent, false);
      holder = new ViewHolder();
      holder.equi_usable_tv_name = (TextView) convertView.findViewById(R.id.equi_usable_tv_name);
      holder.equi_usable_tv_descript = (TextView) convertView
          .findViewById(R.id.equi_usable_tv_descript);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    holder.equi_usable_tv_name.setText(usableList.get(position).getName());
    holder.equi_usable_tv_descript
        .setText("设备型号，后台返回 ");
//    holder.equi_usable_tv_descript
//        .setText(DevUtils.getDevType(usableList.get(position).getProductType()) + "");
    return convertView;
  }

  private class ViewHolder {

    public TextView equi_usable_tv_name;
    public TextView equi_usable_tv_descript;
  }

}
