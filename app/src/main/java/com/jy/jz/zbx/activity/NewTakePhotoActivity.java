package com.jy.jz.zbx.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.jy.jz.zbx.R;
import com.jy.jz.zbx.utils.APPUtils;
import com.jy.jz.zbx.utils.FilePathUtil;


public class NewTakePhotoActivity extends AppCompatActivity implements CameraView.OnTakePhotoOverListener {

    private CameraView mCameraView;
    private String mPath= FilePathUtil.getPhotoFile() + "avatarTemp" + System.currentTimeMillis() + FilePathUtil.IMAGE_TYPE;
    private int mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_take_photos);
        getSupportActionBar().hide();//继承AppCompatActivity 中这一句可以隐藏标题栏
        APPUtils.getCameraAndPhotoPermission(NewTakePhotoActivity.this, APPUtils.cameraRequestPermissionsCode);
        initView();
        initData();
    }

    private void initView() {
        mCameraView = (CameraView) findViewById(R.id.uzi_cameraview);
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
//            mFlag = intent.getIntExtra("flag", 0);
//            mPath = intent.getStringExtra("path");
            mCameraView.setData(mFlag, mPath, this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(NewTakePhotoActivity.this, "请授权拍摄", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }

    @Override
    public void onTakePhotoOver() {
        setResult(Activity.RESULT_OK);
//        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraView.isTaking = false;
    }
}
