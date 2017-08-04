package com.jy.jz.zbx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.jy.jz.zbx.R;

/**
 * Created by Mic-roo on 2016/11/23 0023.
 * 各种消息
 */

public class AllNewsActivity extends BaseActivity {
    private ListView ptr_all_news;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);

        initView();
    }

    private void initView() {
        ptr_all_news = (ListView) findViewById(R.id.ptr_all_news);
    }
}
