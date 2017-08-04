package com.jy.jz.zbx.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jy.jz.zbx.R;
import com.jy.jz.zbx.activity.CornsReportActivity;
import com.jy.jz.zbx.activity.UserCenterActivity;
import com.jy.jz.zbx.adapter.ReportAdapter;
import com.jy.jz.zbx.bean.ReportBean;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表
 */
public class FourthFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "FourthFragment";
    protected Context mContext;
    protected View rootView;
    private ListView lv_report;
    private ReportAdapter reportAdapter;
    private List<ReportBean> reportList;
    private Button btn_left, btn_right;
    private TextView tv_time;
    private ImageView img_user_center;
    private Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fourth_fragment, container, false);
        mContext = getActivity();
        initView();
        initData();
        return rootView;
    }

    public void initView() {
        lv_report = (ListView) rootView.findViewById(R.id.lv_report);
        tv_time = (TextView) rootView.findViewById(R.id.tv_time);
        btn_left = (Button) rootView.findViewById(R.id.btn_left);
        btn_right = (Button) rootView.findViewById(R.id.btn_right);
        img_user_center = (ImageView) rootView.findViewById(R.id.img_user_center);
        reportList = new ArrayList<ReportBean>();
        reportList.add(new ReportBean(R.mipmap.corn_2, "五谷", "5000g"));
        reportList.add(new ReportBean(R.mipmap.vegetables_2, "蔬菜", "2000g"));
        reportList.add(new ReportBean(R.mipmap.fruits_2, "水果", "500g"));
        reportList.add(new ReportBean(R.mipmap.milk_2, "蛋奶", "1000g"));
        reportList.add(new ReportBean(R.mipmap.mast_2, "坚果", "200g"));
        reportList.add(new ReportBean(R.mipmap.meat_2, "肉类", "1000g"));

        reportAdapter = new ReportAdapter(mContext, reportList);
        lv_report.setAdapter(reportAdapter);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initData() {
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        img_user_center.setOnClickListener(this);

        lv_report.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0://五谷
                        intent = new Intent(mContext, CornsReportActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_right://增加
                break;
            case R.id.btn_left://减少
                break;
            case R.id.img_user_center:
                Intent intent = new Intent(mContext, UserCenterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
