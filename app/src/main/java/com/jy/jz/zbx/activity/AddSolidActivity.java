package com.jy.jz.zbx.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.application.ZBXApplication;
import com.jy.jz.zbx.bean.AddNames;
import com.jy.jz.zbx.service.Address;
import com.jy.jz.zbx.utils.ConfigUtils;
import com.jy.jz.zbx.utils.SPUtils;
import com.jy.jz.zbx.utils.T;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;


/**
 * Created by Mic-roo on 2016/11/25 0025.
 * 添加食材
 */

public class AddSolidActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_ok, img_scan, img_add_back;
    private Context context;
    private EditText ed_add_solid;
    private TextView tv_test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_solid);
        context = this;
        initView();
        initData();
    }

    private void initView() {
        img_ok = (ImageView) findViewById(R.id.img_ok);
        img_scan = (ImageView) findViewById(R.id.img_scan);
        img_add_back = (ImageView) findViewById(R.id.img_add_back);
        ed_add_solid = (EditText) findViewById(R.id.ed_add_solid);
        tv_test = (TextView) findViewById(R.id.tv_test);
    }

    private void initData() {
        img_ok.setOnClickListener(this);
        img_scan.setOnClickListener(this);
        img_add_back.setOnClickListener(this);
    }

    @Override
// 通过 onActivityResult的方法获取 扫描回来的 值
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "内容为空", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "扫描成功", Toast.LENGTH_LONG).show();
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                if (ScanResult != null) {
                    tv_test.setText(ScanResult);
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Intent intent = new Intent();
                    intent.putExtra("solid", ed_add_solid.getText().toString());
                    setResult(101, intent);
                    finish();
                    break;

            }
            return false;
        }
    });

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_ok://保存数据
                boolean isSeam = false;
                Address entity = new Address();
                if (ed_add_solid.getText().toString().trim() != null) {
                    entity.setStartAdd(ed_add_solid.getText().toString().trim());
                    ConfigUtils.address = entity;
                    try {
                        List<Address> list = ZBXApplication.db.findAll(Address.class);
                        if (list != null) {
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getStartAdd().equals(ed_add_solid.getText().toString().trim())) {
                                    isSeam = true;
                                    break;
                                }
                            }
                        }
                        if (!isSeam) {
                            ZBXApplication.db.saveBindingId(entity);

                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                }
                new Thread(new Runnable() {

                    public void run() {

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        myHandler.sendEmptyMessage(0); //告诉主线程执行任务

                    }

                }).start();

                break;
            case R.id.img_scan://扫描
                new IntentIntegrator(this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                        .initiateScan(); // 初始化扫描
                break;

            case R.id.img_add_back:
                finish();
                break;
        }
    }
}
