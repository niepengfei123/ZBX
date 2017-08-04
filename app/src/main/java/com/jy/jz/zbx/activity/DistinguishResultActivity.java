package com.jy.jz.zbx.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.bean.Product;
import com.jy.jz.zbx.service.WebViewActivity;

/**
 * Created by Mic-roo on 2017/7/5 0005.
 * 识别结果
 */

public class DistinguishResultActivity extends BaseActivity implements View.OnClickListener {
    private Context context;
    private SimpleDraweeView sdv_result_photo;
    private TextView tv_result_describe, tv_distribution_network, tv_instructions;
    private ImageView img_user_back;
    private Product product;
    private String productName;
    private String productCode;
    private String wifiCore;
    private String url;//说明书地址
    private String imgUrl;//

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distinguish_result);
        context = this;
        Intent get = getIntent();
        if (get != null) {
            product = (Product) get.getExtras().getSerializable("product");
            imgUrl = get.getExtras().getString("imgUrl");
        }
        initView();
        initData();
    }

    private void initView() {
        sdv_result_photo = (SimpleDraweeView) findViewById(R.id.sdv_result_photo);
        tv_distribution_network = (TextView) findViewById(R.id.tv_distribution_network);
        tv_result_describe = (TextView) findViewById(R.id.tv_result_describe);
        tv_instructions = (TextView) findViewById(R.id.tv_instructions);
        img_user_back = (ImageView) findViewById(R.id.img_user_back);
    }

    private void initData() {
        img_user_back.setOnClickListener(this);
        tv_distribution_network.setOnClickListener(this);
        tv_instructions.setOnClickListener(this);
        if (product.getProductName() != null) {
            productName = product.getProductName();
        }
        if (product.getProductCode() != null) {
            productCode = product.getProductCode();
        }
        if (product.getWifiCore() != null) {
            wifiCore = product.getWifiCore();
        }
        if (product.getUrl() != null) {
            url = product.getUrl();
        }
        if (productName != null && productCode != null) {
            tv_result_describe.setText("产品名称:" + productName + " 产品型号:" + productCode);
        }
        if (imgUrl != null) {
            sdv_result_photo.setImageURI(Uri.parse(imgUrl));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_user_back:
                finish();
                break;
            case R.id.tv_distribution_network://配网
                startActivity(new Intent(context, BindingEquipmentActivity.class));
                break;
            case R.id.tv_instructions://查看说明书
                Intent webH5 = new Intent(context, WebViewActivity.class);
                webH5.putExtra("url", url);
                webH5.putExtra("webName", 0);
                startActivity(webH5);
                break;
        }
    }
}
