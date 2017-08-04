package com.jy.jz.zbx.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jy.jz.zbx.R;

/**
 * Created by Mic-roo on 2017/7/4 0004.
 * 智能厨房
 */

public class AiKitchenActivity extends BaseActivity implements View.OnClickListener {
    private Context context;
    private TextView tv_take_photo;
    private ImageView img_user_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_kitchen);
        context = this;
        initView();
        initData();
    }

    private void initView() {
        tv_take_photo = (TextView) findViewById(R.id.tv_take_photo);
        img_user_back = (ImageView) findViewById(R.id.img_user_back);
    }

    private void initData() {
        tv_take_photo.setOnClickListener(this);
        img_user_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_user_back:
                finish();
                break;
            case R.id.tv_take_photo:
                startActivity(new Intent(context, NewTakePhotoActivity.class));
                break;
        }

    }
}
