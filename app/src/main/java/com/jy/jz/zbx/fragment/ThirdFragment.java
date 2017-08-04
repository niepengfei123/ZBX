package com.jy.jz.zbx.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jy.jz.zbx.R;
import com.jy.jz.zbx.activity.UserCenterActivity;
import com.jy.jz.zbx.adapter.AllNewsAdapter;
import com.jy.jz.zbx.bean.AllNewsBean;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息
 */
public class ThirdFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ThirdFragment";
    protected Context mContext;
    protected View rootView;
    protected ImageView img_user_center;
    private ListView lv_all_news;
    private LinearLayout footView;
    private AllNewsAdapter allNewsAdapter;
    private List<AllNewsBean> allNewsList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.third_fragment, container, false);
        mContext = getActivity();
        initView();
        return rootView;
    }

    public void initView() {
        img_user_center = (ImageView) rootView.findViewById(R.id.img_user_center);
        img_user_center.setOnClickListener(this);
        lv_all_news = (ListView) rootView.findViewById(R.id.lv_all_news);
        footView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.footview_item, null);
        lv_all_news.addFooterView(footView);
        allNewsList = new ArrayList<AllNewsBean>();
        allNewsList.add(new AllNewsBean("猪肉到期", "2016/11/23", "今天"));
        allNewsList.add(new AllNewsBean("老爸在家庭菜单中加入新菜", "2016/11/25", "查看明细"));
        allNewsList.add(new AllNewsBean("采购提醒", "2016/11/28", "查看明细"));
        allNewsAdapter = new AllNewsAdapter(mContext, allNewsList);
        lv_all_news.setAdapter(allNewsAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            case R.id.img_user_center:
                Intent intent = new Intent(mContext, UserCenterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
