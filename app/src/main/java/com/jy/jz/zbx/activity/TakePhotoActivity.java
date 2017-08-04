package com.jy.jz.zbx.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.utils.APPUtils;
import com.jy.jz.zbx.utils.FilePathUtil;
import com.jy.jz.zbx.utils.T;
import com.jy.jz.zbx.utils.WaitingDialog;

import java.io.File;
import java.security.Policy;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by Mic-roo on 2017/7/4 0004.
 * 拍照上传页面
 */

public class TakePhotoActivity extends BaseActivity implements View.OnClickListener {
    private Context context;
    private SurfaceView preview_view;
    private TextView tv_light_button, tv_take_photo;
    private DecoratedBarcodeView mDBV;
    private boolean isLightOn = false;
    private Camera camera;
    Camera.Parameters parameter;
    CameraManager mCameraManager;
    CameraDevice cameraDevice;
    int selectType = 0;//是哪个控件的照片
    String headFrontTempPath = null;
    private File userAvatarFile;
    private WaitingDialog waitingDialog;
    /**
     * 调用相机的标识码
     */
    public static final int REQUEST_CODE_PHOTO = 100;
    /**
     * 调用相册的标识码
     */
    public static final int SELECT_IMAGE_FROM_ALBUM = 101;
    /**
     * 调用裁剪图片的标识码
     */
    public static final int CROP_PHOTO = 102;

    //    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_take_photo);
        context = this;
//        camera = Camera.open();
//        camera.startPreview();
        waitingDialog = new WaitingDialog(context);

        initView();
        initData();
    }

    private void initView() {
        tv_take_photo = (TextView) findViewById(R.id.tv_take_photo);
        tv_light_button = (TextView) findViewById(R.id.tv_light_button);
    }

    private void initData() {
        tv_light_button.setOnClickListener(this);
        tv_take_photo.setOnClickListener(this);
        // 如果没有闪光灯功能，就去掉相关按钮
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        if (!hasFlash()) {
            T.showshort(context, "该设备没有闪光灯功能");
            tv_light_button.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    //打开闪光灯
    public void openLight() {
        camera = Camera.open();
        camera.startPreview();
        parameter = camera.getParameters();
        parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameter);
    }

    //关闭闪光灯
    public void closeLight() {
        parameter = camera.getParameters();
        parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_take_photo://拍照
                selectType = 2;
                APPUtils.getCameraAndPhotoPermission(context, APPUtils.cameraRequestPermissionsCode);
                break;
            case R.id.tv_light_button://闪光灯
                if (!isLightOn) {
                    openLight();
                    isLightOn = true;
                } else {
                    closeLight(); //关闪光灯
                    isLightOn = false;
                }
                break;
        }

    }

    @PermissionSuccess(requestCode = APPUtils.cameraRequestPermissionsCode)
    public void getCameraPermissionSuccess() {
//        T.showshort(context, "定位权限获取成功");
        if (selectType == 2) {
            headFrontTempPath = FilePathUtil.getPhotoFile() + "avatarTemp" + System.currentTimeMillis() + FilePathUtil.IMAGE_TYPE;
            APPUtils.requestTakePhoto(TakePhotoActivity.this, REQUEST_CODE_PHOTO, headFrontTempPath);
        } else if (selectType == 1) {
            headFrontTempPath = FilePathUtil.getPhotoFile() + "avatarTemp" + System.currentTimeMillis() + FilePathUtil.IMAGE_TYPE;
            APPUtils.selectImageFromAlbum(TakePhotoActivity.this, SELECT_IMAGE_FROM_ALBUM);
        } else {

        }


    }

    @PermissionFail(requestCode = APPUtils.cameraRequestPermissionsCode)
    private void getCameraPermissionFail() {
        T.showshort(this, "相机权限获取失败");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri originalUri = null;
        if (data != null) {
            if (data.getData() != null) {
                originalUri = data.getData();
            }
        } else {
            originalUri = Uri.fromFile(new File(headFrontTempPath));
        }
        switch (requestCode) {
            case REQUEST_CODE_PHOTO:
                File file = new File(headFrontTempPath);
                Uri photoURI = FileProvider.getUriForFile(TakePhotoActivity.this, "com.jy.jz.zbx.fileprovider", file);
                APPUtils.cropImage(TakePhotoActivity.this, photoURI, headFrontTempPath, 256, 256, CROP_PHOTO, false);
                break;
            case SELECT_IMAGE_FROM_ALBUM:
                if (originalUri == null) {
                    originalUri = Uri.fromFile(new File(headFrontTempPath));
                }
                APPUtils.cropImage(TakePhotoActivity.this, originalUri, headFrontTempPath, 256, 256, CROP_PHOTO, false);
                break;
            case CROP_PHOTO:
                startActivity(new Intent(context,DistinguishResultActivity.class));
//                getUserInfo();
                break;
            default:
                break;

        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                waitingDialog.showWaitingDialog();
            } else if (msg.what == 1) {
                waitingDialog.dismissWaitingDialog();
            } else if (msg.what == 2) {
                Uri originalUri = null;
                Bundle bundle = msg.getData();
                originalUri = bundle.getParcelable("originalUri");
//                getUserInfo();
            } else if (msg.what == 3) {

            }

        }
    };

    @Override
    protected void onDestroy() {
        camera.stopPreview();
        camera.release();
        camera = null;
        super.onDestroy();
    }
}
