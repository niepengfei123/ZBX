package com.jy.jz.zbx.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.jy.jz.zbx.R;

/**
 * Created by Mic-roo on 2016/12/6 0006.
 */
public class CustomScanActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener, View.OnClickListener { // 实现相关接口
    // 添加一个按钮用来控制闪光灯，同时添加两个按钮表示其他功能，先用Toast表示

//    @BindView(R.id.btn_switch) Button swichLight;
//    @BindView(R.id.btn_hint1) Button hint1Show;
//    @BindView(R.id.btn_hint2) Button hint2Show;
//    @BindView(R.id.dbv_custom) DecoratedBarcodeView mDBV;

    private CaptureManager captureManager;
    private boolean isLightOn = false;
    private Button swichLight, hint1Show, hint2Show;
    private DecoratedBarcodeView mDBV;


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scan);
        getSupportActionBar().hide();
        initView();

        //重要代码，初始化捕获
        captureManager = new CaptureManager(this, mDBV);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
    }

    private void initView() {
        swichLight = (Button) findViewById(R.id.btn_switch);
        hint1Show = (Button) findViewById(R.id.btn_hint1);
        hint2Show = (Button) findViewById(R.id.btn_hint2);
        mDBV = (DecoratedBarcodeView) findViewById(R.id.dbv_custom);

//        ButterKnife.bind(this);

        mDBV.setTorchListener(this);
        swichLight.setOnClickListener(this);
        hint1Show.setOnClickListener(this);
        hint2Show.setOnClickListener(this);

        // 如果没有闪光灯功能，就去掉相关按钮
        if (!hasFlash()) {
            swichLight.setVisibility(View.GONE);
        }
    }

    // torch 手电筒
    @Override
    public void onTorchOn() {
//        Toast.makeText(this,"torch on",Toast.LENGTH_LONG).show();
        isLightOn = true;
    }

    @Override
    public void onTorchOff() {
//        Toast.makeText(this,"torch off", Toast.LENGTH_LONG).show();
        isLightOn = false;
    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    // 点击切换闪光灯
//    @OnClick(R.id.btn_switch)
//    public void swichLight(){
//        if(isLightOn){
//            mDBV.setTorchOff();
//        }else{
//            mDBV.setTorchOn();
//        }
//    }
    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_switch:    // 点击切换闪光灯
                if (isLightOn) {
                    mDBV.setTorchOff();
                } else {
                    mDBV.setTorchOn();
                }
                break;
            case R.id.btn_hint2:
                finish();
                break;

        }

    }
}