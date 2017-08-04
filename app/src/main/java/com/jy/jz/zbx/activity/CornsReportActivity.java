package com.jy.jz.zbx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jy.jz.zbx.R;

/**
 * Created by Mic-roo on 2016/11/25 0025.
 * 五谷报表
 */

public class CornsReportActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_left_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corns_report);
        initView();
    }

    private void initView() {
        img_left_back = (ImageView) findViewById(R.id.img_left_back);
        img_left_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_left_back:
                finish();
                break;
        }
    }
}
