package com.jy.jz.zbx.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.journeyapps.barcodescanner.camera.CameraManager;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.bean.Product;
import com.jy.jz.zbx.bean.ProductBean;
import com.jy.jz.zbx.utils.APPUtils;
import com.jy.jz.zbx.utils.ConfigUtils;
import com.jy.jz.zbx.utils.OkHttpUtils;
import com.jy.jz.zbx.utils.OkHttpUtils2;
import com.jy.jz.zbx.utils.T;
import com.jy.jz.zbx.utils.WaitingDialog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static android.util.Log.e;

/**
 * @autor: guoyibing
 * @time: 2017/5/9/17:48
 * @describe:
 */

public class CameraView extends RelativeLayout implements SurfaceHolder.Callback {

    private static final String TAG = "CameraView";

    /**
     * 中间透明区域以及图片截取的位移距离
     */
    private int margin;

    /**
     * 屏幕宽度
     */
    private int screenWidth;

    /**
     * 屏幕高度
     */
    private int screenHeight;

    /**
     * 照片宽度
     */
    private int pictureWidth;

    /**
     * 照片高度
     */
    private int pictureHeight;
    /**
     * 照片高度
     */
    private int fictitiousHeight;

    /**
     * 预览比例
     */
    private float previewRate = 0.0f;

    /**
     * 闪光灯是否已经打开
     */
    private boolean isOpen = false;

    /**
     * 是否正在预览状态
     */
    private boolean isPreviewing = false;

    /**
     * 是否正在拍照
     */
    public boolean isTaking = false;

    /**
     * 照片保存路径
     */
    private String mFilePath;
    File imgFile;
    File file;
    String fileOts;
    private Camera mCamera;
    private Context mContext;
    private ImageView mReturn;
    private MaskView mMaskView;
    //    private ImageView mUseLight;
    private TextView mUseLight;
    private  TextView tv_distribution;
    private ImageView mTakePhoto;
    private ImageView mBackground;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Camera.Parameters mCameraParans;
    private OnTakePhotoOverListener mListener;
    private Camera.AutoFocusCallback mAutoFousCallback;
    private Product product;
    private WaitingDialog waitingDialog;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                waitingDialog.showWaitingDialog();
            } else if (msg.what == 1) {
                waitingDialog.dismissWaitingDialog();
            }

        }
    };

    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContext = context;
        waitingDialog = new WaitingDialog(mContext);
        init();
        initListener();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.camera_view, this);
        mSurfaceView = (SurfaceView) findViewById(R.id.camera_surfaceview);
        mMaskView = (MaskView) findViewById(R.id.maskview);
        mTakePhoto = (ImageView) findViewById(R.id.take_photo);
        mReturn = (ImageView) findViewById(R.id.take_photo_return);
        mUseLight = (TextView) findViewById(R.id.take_photo_use_light);
        tv_distribution = (TextView) findViewById(R.id.tv_distribution);
//        mUseLight.setBackgroundResource(R.mipmap.ic_launcher);
        mBackground = (ImageView) findViewById(R.id.take_photo_background);

        Point screenMetrics = CommonUtil.getScreenMetrics(mContext);
        screenWidth = screenMetrics.x;
        screenHeight = screenMetrics.y;
        previewRate = (float) screenHeight / screenWidth;
        e(TAG, "screenWidth = " + screenWidth + " screenHeight = " + screenHeight + " previewRate = " +
                previewRate);

        ViewGroup.LayoutParams params = mSurfaceView.getLayoutParams();
//        params.width = 1200;
//        params.height = 1200;
        params.width = screenWidth;
        params.height = screenHeight;
        mSurfaceView.setLayoutParams(params);
        mSurfaceView.setFocusable(true);

//        mSurfaceView.setOnClickListener(this);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.setKeepScreenOn(true);
//        mSurfaceHolder.setFixedSize(256, 256);
        mSurfaceHolder.addCallback(this);

        margin = mContext.getResources().getDimensionPixelOffset(R.dimen.cameraview_margin);
        pictureWidth = (int) (300 * getResources().getDisplayMetrics().density);
        pictureHeight = (int) (300 * getResources().getDisplayMetrics().density);
        fictitiousHeight = APPUtils.getBottomStatusHeight(mContext);
//        pictureWidth = mContext.getResources().getDimensionPixelOffset(R.dimen.maskview_rect_width);
//        pictureHeight = mContext.getResources().getDimensionPixelOffset(R.dimen.maskview_rect_height);


    }

    private void initListener() {
        mTakePhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTaking) {
                    isTaking = true;
                    if (isPreviewing && (mCamera != null)) {
                        mCamera.takePicture(mShutterCallback, null, mPictureCallback);
                    }
                }
            }
        });

        mReturn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
            }
        });

        mUseLight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    openFlashLight();
                    isOpen = true;
//                    mUseLight.setBackgroundResource(R.mipmap.ic_launcher);
                } else {
                    closeFlashLight();
                    isOpen = false;
//                    mUseLight.setBackgroundResource(R.mipmap.ic_launcher);
                }
            }
        });
        mAutoFousCallback = new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {

                } else {
                }
            }
        };

    }

    public void setData(int flag, String path, OnTakePhotoOverListener listener) {
        mFilePath = path;
        mListener = listener;
        if (flag == 0) {
//            mBackground.setBackgroundResource(R.mipmap.ic_launcher);
        } else {
//            mBackground.setBackgroundResource(R.mipmap.ic_launcher);
        }
    }

    private void openFlashLight() {
        if (mCamera == null) {
            return;
        }
        if (mCameraParans == null) {
            return;
        }
        List<String> flashModes = mCameraParans.getSupportedFlashModes();
        if (flashModes == null) {
            return;
        }
        String flashMode = mCameraParans.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_TORCH.equals(flashMode)) {

            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                mCameraParans.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(mCameraParans);
            }
        }
    }

    private void closeFlashLight() {
        if (mCamera == null) {
            return;
        }
        if (mCameraParans == null) {
            return;
        }
        List<String> flashModes = mCameraParans.getSupportedFlashModes();
        if (flashModes == null) {
            return;
        }
        String flashMode = mCameraParans.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                mCameraParans.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(mCameraParans);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        openCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // 实现自动对焦
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    camera.cancelAutoFocus();// 只有加上了这一句，才会自动对焦
//                    doAutoFocus();
                }
            }
        });
        // TODO Auto-generated method stub
    }

    // handle button auto focus
    private void doAutoFocus() {
        mCameraParans = mCamera.getParameters();
        mCameraParans.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.setParameters(mCameraParans);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    camera.cancelAutoFocus();// 只有加上了这一句，才会自动对焦。
                    if (!Build.MODEL.equals("KORIDY H30")) {
                        mCameraParans = camera.getParameters();
                        mCameraParans.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 1连续对焦
                        camera.setParameters(mCameraParans);
                    } else {
                        mCameraParans = camera.getParameters();
                        mCameraParans.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                        camera.setParameters(mCameraParans);
                    }
                }
            }
        });
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        stopCamera();
    }

    /**
     * 打开Camera
     */
    public void openCamera() {
        if (ActivityCompat.checkSelfPermission(
                mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            finishActivity();
        }
        if (mCamera == null) {
            try {
                mCamera = Camera.open();
            } catch (RuntimeException e) {
                e.printStackTrace();
                finishActivity();
            }
            if (mCamera == null) {
                finishActivity();
            } else {
                cameraHasOpened();
            }
        }
    }

    private void finishActivity() {
        Toast.makeText(mContext, "请授权拍摄", Toast.LENGTH_SHORT).show();
        ((Activity) mContext).finish();
        return;
    }

    private void cameraHasOpened() {
        startPreview(mSurfaceHolder);
        if (mMaskView != null) {
            Rect screenCenterRect = createCenterScreenRect();
            mMaskView.setCenterRect(screenCenterRect);
        }
    }

    /**
     * 使用Surfaceview开启预览
     *
     * @param holder
     */
    public void startPreview(SurfaceHolder holder) {
        e(TAG, "startPreview");
        if (isPreviewing) {
            mCamera.stopPreview();
            return;
        }
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            initCamera();
//            mCamera.autoFocus(new Camera.AutoFocusCallback() {
//                @Override
//                public void onAutoFocus(boolean success, Camera camera) {
//                    if (success) {
//
//                        mCamera.cancelAutoFocus();
//                    }
//                }
//            });
//
        }
    }


    /**
     * 停止预览，释放Camera
     */
    public void stopCamera() {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            isPreviewing = false;
            mCamera.release();
            mCamera = null;
        }
    }

    private void initCamera() {
        if (mCamera != null) {
            mCameraParans = mCamera.getParameters();
            mCameraParans.setPictureFormat(PixelFormat.JPEG);//设置拍照后存储的图片格式
            mCameraParans.setJpegQuality(100);// 设置照片质量
//            CameraParamUtil.getInstance().printSupportPictureSize(mCameraParans);
//            CameraParamUtil.getInstance().printSupportPreviewSize(mCameraParans);\
            //          //查询屏幕的宽和高
//                      WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
//                      Display display = wm.getDefaultDisplay();
//                      //查询camera支持的picturesize和previewsize
//                      List<Camera.Size> pictureSizes = mCameraParans.getSupportedPictureSizes();
//                      List<Camera.Size> previewSizes = mCameraParans.getSupportedPreviewSizes();
//                      for(int i=0; i<pictureSizes.size(); i++){
//                          Camera.Size size = pictureSizes.get(i);
//                      }
//            for(int i=0; i<previewSizes.size(); i++){
//                              Camera.Size size = previewSizes.get(i);
//                          }
            //设置PreviewSize和PictureSize
            Camera.Size pictureSize = CameraParamUtil.getInstance().getPropPictureSize(
                    mCameraParans.getSupportedPictureSizes(), previewRate, 800);
            mCameraParans.setPictureSize(screenHeight + fictitiousHeight, screenWidth);

            Camera.Size previewSize = CameraParamUtil.getInstance().getPropPreviewSize(
                    mCameraParans.getSupportedPreviewSizes(), previewRate, 800);
            mCameraParans.setPreviewSize(screenHeight + fictitiousHeight, screenWidth);
//            List<Camera.Size> sizes = mCameraParans.getSupportedPictureSizes();
//            int maxSize = Math.max(display.getWidth(), display.getHeight());
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCamera.cancelAutoFocus();//自动对焦。
            mCamera.setDisplayOrientation(90);

//			CameraParamUtil.getInstance().printSupportFocusMode(mParams);
            List<String> focusModes = mCameraParans.getSupportedFocusModes();
            if (focusModes.contains("continuous-video")) {
                mCameraParans.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                mCameraParans.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }
//            if (!Build.MODEL.equals("KORIDY H30")) {
//                mCameraParans.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 1连续对焦
//            } else {
//                mCameraParans.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//            }
            mCamera.setParameters(mCameraParans);
            mCamera.startPreview();//开启预览
            mCamera.cancelAutoFocus();// 2如果要实现连续的自动对焦，这一句必须加上
            isPreviewing = true;
            mCameraParans = mCamera.getParameters(); //重新get一次
            Log.e(TAG, "最终设置:PreviewSize--With = " + mCameraParans.getPreviewSize().width
                    + " Height = " + mCameraParans.getPreviewSize().height);
            Log.e(TAG, "最终设置:PictureSize--With = " + mCameraParans.getPictureSize().width
                    + " Height = " + mCameraParans.getPictureSize().height);
        }


    }


    /*为了实现拍照的快门声音及拍照保存照片需要下面三个回调变量*/
    Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
        //快门按下的回调，在这里我们可以设置类似播放“咔嚓”声之类的操作。默认的就是咔嚓。
        public void onShutter() {
            Log.e(TAG, "onShutter");
        }
    };

    /**
     * 拍摄指定区域的Rect
     */
    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        //对图像数据的回调,最重要的一个回调
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.e(TAG, "onPictureTaken");
            Bitmap bitmap = null;
            if (null != data) {
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);//data是字节数据，将其解析成位图
//                bitmap = CommonUtil.zoomBitmap(bitmap, 256, 256);
                mCamera.stopPreview();
            }
            if (null != bitmap) {
                bitmap = rotaingImageView(90, bitmap);
                int x, y;
                x = bitmap.getWidth() / 2 - pictureHeight / 2;
                if (fictitiousHeight != 0) {
                    y = bitmap.getHeight() / 2 + fictitiousHeight / 4 - pictureWidth / 2-screenHeight/12;
                } else {
                    y = bitmap.getHeight() / 2 - pictureWidth / 2-screenHeight/12;
                }
                Bitmap rectBitmap = Bitmap.createBitmap(bitmap, x, y, pictureWidth, pictureHeight);
//                bitmap = CommonUtil.zoomBitmap(rectBitmap, 256, 256);
                //图片旋转
//                bitmap = rotaingImageView(90, bitmap);
                file = new File(mFilePath);
                CommonUtil.saveBitmap2File(file, rectBitmap);
                tv_distribution.setVisibility(GONE);
                imgUpLoadProduct();
//                alterUserIcon();
//                CommonUtil.saveBitmap2File(file, rectBitmap);
//                imgUpLoadProduct();
                if (!rectBitmap.isRecycled()) {
                    rectBitmap.recycle();
                }
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            isPreviewing = false;
            mListener.onTakePhotoOver();
        }
    };

    /*
       * 旋转图片
       * @param angle
       * @param bitmap
       * @return Bitmap
       */
    public Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    /**
     * 生成屏幕中间的矩形
     *
     * @return
     */
    private Rect createCenterScreenRect() {
        int left = screenWidth / 2 - pictureWidth / 2;
        int top = screenHeight / 2 - pictureHeight / 2-screenHeight/12;
//        int top = screenHeight / 2 - pictureHeight / 2 - margin;
        int right = left + pictureWidth;
        int bottom = top + pictureHeight;
//        return new Rect(left, top, right, bottom);
        return new Rect(left, top, right, bottom);
    }

    public interface OnTakePhotoOverListener {
        void onTakePhotoOver();
    }

    //上传图片接口
    private void imgUpLoadProduct() {
//        mHandler.sendEmptyMessage(0);
        tv_distribution.setText("正在识别...");
        tv_distribution.setVisibility(VISIBLE);
        OkHttpUtils2.getInstance().postForMapAsynchronization(ConfigUtils.IMGUPLOADPRODUCT, requestInfoDay(), new OkHttpUtils2.RequestCallBack<ProductBean>() {

            @Override
            public void onError(Call call, Exception e) {
//                mHandler.sendEmptyMessage(1);
                T.showshort(mContext, e.toString());
            }

            @Override
            public void onResponse(ProductBean response) {
                if (response != null) {
//                    mHandler.sendEmptyMessage(1);
                    if (response.getCode() == 1) {
//                        Intent intent = new Intent(mContext, DistinguishResultActivity.class);
                        Intent intent = new Intent(mContext, BindingEquipmentActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("product", (Serializable) response.getData());
                        bundle.putString("imgUrl", response.getImgUrl());
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                        ((Activity) mContext).finish();
//                        T.showshort(mContext, response.getMsg());
                    } else if (response.getCode() == 0) {
                        stopCamera();
                        openCamera();
                        isTaking=false;
//                        T.showlong(mContext, "未发现产品,换个角度试试");
                        tv_distribution.setText("未发现产品,换个角度试试");
                        tv_distribution.setVisibility(VISIBLE);
                    }

                } else {
                    T.showshort(mContext, "网络繁忙，请稍后重试！");
                }

            }
        });
    }

    private Map<String, Object> requestInfoDay() {
        Map<String, Object> object = new HashMap<>();
        if (mFilePath != null) {
            imgFile = new File(mFilePath);
        }
        object.put("img1", imgFile);
        return object;
    }

}
