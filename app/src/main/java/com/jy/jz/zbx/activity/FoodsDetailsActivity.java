package com.jy.jz.zbx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jy.jz.zbx.R;

/**
 * Created by Mic-roo on 2016/12/2 0002.
 * 食物详情
 */

public class FoodsDetailsActivity extends BaseActivity implements View.OnClickListener {

    private SimpleDraweeView sdv_foods_details, sdv_author;
    private ImageView img_user_center;
    private Button btn_borrow, btn_buy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods_details);
        initView();
        initData();
    }

    private void initView() {
        btn_borrow = (Button) findViewById(R.id.btn_borrow);
        btn_buy = (Button) findViewById(R.id.btn_buy);
        img_user_center = (ImageView) findViewById(R.id.img_user_center);
        sdv_author = (SimpleDraweeView) findViewById(R.id.sdv_author);
        sdv_foods_details = (SimpleDraweeView) findViewById(R.id.sdv_foods_details);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(R.color.light_green, 0.0f);
        roundingParams.setRoundAsCircle(true);
        sdv_author.getHierarchy().setRoundingParams(roundingParams);
    }

    private void initData() {
        sdv_foods_details.setImageResource(R.mipmap.big_milk_2);
        sdv_author.setImageResource(R.mipmap.touxiang);
        img_user_center.setOnClickListener(this);
        btn_borrow.setOnClickListener(this);
        btn_buy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_user_center:
                finish();
                break;
            case R.id.btn_buy://买

                break;
            case R.id.btn_borrow://借

                break;
        }
    }
}
