package com.jy.jz.zbx.mcamera;

/**
 * Created by Mic-roo on 2017/7/7 0007.
 */

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

final class PreviewCallback implements Camera.PreviewCallback {

    private static final String TAG = PreviewCallback.class.getSimpleName();
    private static final int UPDATE_IMAGE = 1;

    private final CameraConfigurationManager configManager;
    private final boolean useOneShotPreviewCallback;
    private Handler previewHandler;
    private static Handler previewHandler_2; //zj
    private static int previewMessage;
//    private static CaptureActivity activity;

    PreviewCallback(CameraConfigurationManager configManager, boolean useOneShotPreviewCallback) {
        this.configManager = configManager;
        this.useOneShotPreviewCallback = useOneShotPreviewCallback;
    }

    void setHandler(Handler previewHandler, int previewMessage) {
        this.previewHandler = previewHandler;
        this.previewMessage = previewMessage;
    }

//    void setHandler(Handler previewHandler, int previewMessage, CaptureActivity activity) {
//        this.previewHandler = previewHandler;
//        this.previewMessage = previewMessage;
//        this.activity = activity;
//    }

    public void onPreviewFrame(final byte[] data, final Camera camera) {
        final Point cameraResolution = configManager.getCameraResolution();
        if (!useOneShotPreviewCallback) {
            camera.setPreviewCallback(null);
        }
        if (previewHandler != null) {
            //compute the time of one piece of photo
//            BaseActivity.one_operation_photo = System.currentTimeMillis();
//            Log.i("test", "-----------------------one piece of photo need time is "+(BaseActivity.one_operation_photo - BaseActivity.one_operation_start)+" ms.");

            previewHandler_2 = previewHandler;
            previewHandler=null;

//            if (previewMessage == R.id.decode) { //进行二维码扫描
//                Log.i("test", "------------------------------now in onPreviewFrame previewMessage == R.id.decode");
//                Message message = previewHandler_2.obtainMessage(previewMessage, cameraResolution.x, cameraResolution.y, data);
//                Log.i("test", "--------------the next step is message.sendToTarget()");
//                message.sendToTarget();
//            } else { //进行九阳检测
//                Log.i("test", "-------------------------------now in on PreviewFrame previewMessage == R.id.decode_joyoung");
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //getDetectImage(data, cameraResolution.x, cameraResolution.y);
//                        Message message = new Message();
//                        message.what = UPDATE_IMAGE;
//                        message.arg1 = cameraResolution.x;
//                        message.arg2 = cameraResolution.y;
//                        message.obj = data;
//                        handler.sendMessage(message);
//                    }
//                }).start();
//            }
//        } else {
//            Log.d(TAG, "Got preview callback, but no handler for it");
        }

        //previewHandler_2 = null;
    }
//
//    private static Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            getDetectImage((byte[])msg.obj, msg.arg1, msg.arg2);
//
//            //compute the time of image dealing
//            BaseActivity.one_operation_imageDetect = System.currentTimeMillis();
//            Log.i("test", "---------------------------------------one piece of image deal need time is "+(BaseActivity.one_operation_imageDetect - BaseActivity.one_operation_start)+" ms.");
//
//            //activity.showImageView();
//            activity.showTextIsDetecting();
//            Message message = previewHandler_2.obtainMessage(previewMessage);
//            Log.i("test", "--------------the next step is message.sendToTarget()");
//            message.sendToTarget();
//        }
//    };
/*
  public void getDetectImage(byte[] data, int width, int height) {
    long time_getDetectImage_start=System.currentTimeMillis();
    Point ScreenResolution = CameraManager.get().getScreenResolution();
    Log.i("test", "---------------------solution width=" + ScreenResolution.x + " height=" + ScreenResolution.y);
    Log.i("test", "-----------data.length = " + data.length + " camera width = " + width + " camera height = " + height);

    Bitmap testBt = rawByteArray2RGBABitmap2(data, width, height);
    Log.i("test", "-----------------------entire image testBt width="+testBt.getWidth()+" height="+testBt.getHeight());
    Matrix matrix = new Matrix();
    matrix.postRotate(90); //向左旋转90度
    Log.i("test", "------------rotate start");
    Bitmap testBt1 = Bitmap.createBitmap(testBt, 0, 0, width, height,
            matrix, true);
    Log.i("test", "------------rotate end");
    Log.i("test", "------------------------entire image testBt1 width="+testBt1.getWidth()+" height="+testBt1.getHeight());

    double zoom_x = ((double)testBt1.getWidth()) / ((double)ScreenResolution.x);
    double zoom_y = ((double)testBt1.getHeight()) / ((double)ScreenResolution.y);

    double zoom = 0;
    if (zoom_x > zoom_y)
      zoom = zoom_y;
    else
      zoom = zoom_x;
    Log.i("test", "zoom_x="+zoom_x+" zoom_y="+zoom_y+" zoom="+zoom);

    Rect rect = CameraManager.get().getFramingRect();
    int rect_hei = rect.bottom - rect.top;
    int rect_wid = rect.right - rect.left;
    Log.i("test", "-------------top="+rect.top+" bottom="+rect.bottom+" left="+rect.left+" right="+rect.right+" wid="+rect_wid+" hei="+rect_hei);

    int testBt2_leftOffset = (int)(rect.left*zoom_x);
    int testBt2_topOffset = (int)(rect.top*zoom_y);
    int testBt2_wid = (int)(rect_wid*zoom_x);
    int testBt2_hei = (int)(rect_hei*zoom_y);

    if (testBt2_wid > testBt2_hei)
      if ((testBt2_wid+testBt2_topOffset) <= testBt1.getHeight())
        testBt2_hei = testBt2_wid;
      else
        testBt2_wid = testBt2_hei;
    else if ((testBt2_hei+testBt2_leftOffset) <= testBt1.getWidth())
      testBt2_wid = testBt2_hei;
    else
      testBt2_hei = testBt2_wid;

    Bitmap testBt2 = Bitmap.createBitmap(testBt1, testBt2_leftOffset, testBt2_topOffset, testBt2_wid, testBt2_hei);
    Log.i("test", "testBt2 ok!!!");
    Log.i("test", "testBt2 x="+testBt2_leftOffset+" y="+testBt2_topOffset+" wid="+testBt2_wid+" hei="+testBt2_hei);

    int center_leftOffset = (int)(testBt2_wid / 10.0);
    int center_topOffset = center_leftOffset;
    int center_wid = (int)(testBt2_wid * 4.0 / 5.0);
    int center_hei = center_wid;
    Bitmap centerBitmap = Bitmap.createBitmap(testBt2, center_leftOffset, center_topOffset, center_wid, center_hei);
    Log.i("test", "centerBitmap ok!!!");
    Log.i("test", "centerBitmap leftOffset="+center_leftOffset+" topOffset="+center_topOffset+" width="+center_wid+" height="+center_hei);

    //store the centerBitmap
    File file = new File("sdcard/caffe_mobile", "bt.png");
    if (file.exists()) {
      file.delete();
    }

    try {
      file.createNewFile();
      FileOutputStream fos = new FileOutputStream(file);
      centerBitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
      fos.flush();
      fos.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    long time_getDetectImage_end=System.currentTimeMillis();
    Log.i("test", "getDetectImage() need time is : "+(time_getDetectImage_end-time_getDetectImage_start)+" ms.");
    Log.i("test", "get the detect image successful");
  }
*/

    /**
     * image crop and save
     * @param data src image
     * @param width
     * @param height
     */
    public static void getDetectImage(byte[] data, int width, int height) {
        long time_getDetectImage_start=System.currentTimeMillis();
        Point ScreenResolution = CameraManager.get().getScreenResolution();
        Log.i("test", "---------------------solution width=" + ScreenResolution.x + " height=" + ScreenResolution.y);
        Log.i("test", "-----------data.length = " + data.length + " camera width = " + width + " camera height = " + height);

        Bitmap detectBt = rawByteArray2RGBABitmap2(data, width, height);  //从字节数组转换成bitmap图像
        Log.i("test", "------------------------------------------------------------" + detectBt.getConfig());
        Log.i("test", "-----------------------entire image detectBt width=" + detectBt.getWidth() + " height=" + detectBt.getHeight());
        data = null;

        Log.i("test", "------------rotate start");
        long time_rotate_start = System.currentTimeMillis();
        Matrix matrix = new Matrix();
        matrix.postRotate(90); //向左旋转90度
        Bitmap rotateBt = Bitmap.createBitmap(detectBt, 0, 0, width, height,
                matrix, true); //旋转bitmap图像
        long time_rotate_end = System.currentTimeMillis();
        Log.i("test", "------------rotate end");
        Log.i("test", "------------------------------------------------------------" + rotateBt.getConfig());
        Log.i("test", "------------------------entire image rotateBt width=" + rotateBt.getWidth() + " height=" + rotateBt.getHeight() + " rotate time is " + (time_rotate_end - time_rotate_start));

        Rect rect = CameraManager.get().getFramingRect();
        int rect_hei = rect.bottom - rect.top;
        int rect_wid = rect.right - rect.left;
        Log.i("test", "-------------top="+rect.top+" bottom="+rect.bottom+" left="+rect.left+" right="+rect.right+" wid="+rect_wid+" hei="+rect_hei);

        //crop center image begin
        long crop_image_start = System.currentTimeMillis();
        double zoom_x = ((double)rotateBt.getWidth()) / ((double)ScreenResolution.x);
        double zoom_y = ((double)rotateBt.getHeight()) / ((double)ScreenResolution.y);
        Log.i("test", "zoom_x="+zoom_x+" zoom_y="+zoom_y);

        int cen_top=(int)(rect.top*zoom_y);
        int cen_left=(int)(rect.left*zoom_x);
        int cen_wid=(int)(rect_wid*zoom_x);
        int cen_hei=(int)(rect_hei*zoom_y);

//        Log.i(MainActivity.TAG, "before comparison cen_top = " + cen_top + " cen_left = " + cen_left + " cen_wid = " + cen_wid + " cen_hei = " + cen_hei);

        if (cen_hei<cen_wid) {
            cen_left += (cen_wid-cen_hei)/2;
            cen_wid = cen_hei;
        } else if (cen_wid<cen_hei) {
            cen_top += (cen_hei-cen_wid)/2;
            cen_hei = cen_wid;
        }

//        if (BaseActivity.resultBt != null && !BaseActivity.resultBt.isRecycled())
//        {
//            //BaseActivity.resultBt.recycle();
//            BaseActivity.resultBt = null;
//        }
        System.gc();

//        BaseActivity.resultBt = Bitmap.createBitmap(rotateBt, cen_left, cen_top, cen_wid, cen_hei);
        long crop_image_end = System.currentTimeMillis();
        Log.i("test", "resultBt ok!!!"+" crop time is "+(crop_image_end - crop_image_start));
//        Log.i(MainActivity.TAG, "after comparison cen_top = "+cen_top+" cen_left = "+cen_left+" cen_wid = "+cen_wid+" cen_hei = "+cen_hei);
//        Log.i("test", "------------------------------------------------------------"+BaseActivity.resultBt.getConfig());
        Log.i("test", "resultBt x="+cen_left+" y="+cen_top+" wid="+cen_wid+" hei="+cen_hei);
/*
    int center_leftOffset = (int)(testBt2_wid / 10.0);
    int center_topOffset = center_leftOffset;
    int center_wid = (int)(testBt2_wid * 4.0 / 5.0);
    int center_hei = center_wid;
    Bitmap centerBitmap = Bitmap.createBitmap(testBt2, center_leftOffset, center_topOffset, center_wid, center_hei);
    Log.i("test", "centerBitmap ok!!!");
    Log.i("test", "centerBitmap leftOffset="+center_leftOffset+" topOffset="+center_topOffset+" width="+center_wid+" height="+center_hei);
*/

//        activity.showImageView(BaseActivity.resultBt);

        //scale image to 256x256
        long scale_image_start = System.currentTimeMillis();
        Matrix matrix_scale = new Matrix();
        float scale = 0;
//        if (BaseActivity.resultBt.getWidth() > 256)
//            scale = (float)256.0 / (float)BaseActivity.resultBt.getWidth();
//        else
//            scale = (float)BaseActivity.resultBt.getWidth() / (float)256.0;
//        matrix_scale.postScale(scale, scale);
//
//        if (BaseActivity.scale_bitmap != null && !BaseActivity.scale_bitmap.isRecycled())
//        {
//            //BaseActivity.scale_bitmap.recycle();
//            BaseActivity.scale_bitmap = null;
//        }
        System.gc();

//        BaseActivity.scale_bitmap = Bitmap.createBitmap(BaseActivity.resultBt, 0, 0, BaseActivity.resultBt.getWidth(), BaseActivity.resultBt.getHeight(), matrix_scale, true);
//        long scale_image_end = System.currentTimeMillis();
//        Log.i("test", "******************************scale image to 256x256 need time is "+(scale_image_end - scale_image_start));
//        Log.i("test", "scale_image's width = "+BaseActivity.scale_bitmap.getWidth()+" scale_image's height = "+BaseActivity.scale_bitmap.getHeight());

        //save the centerBitmap
        long save_image_start = System.currentTimeMillis();
//        File file = new File(MainActivity.PATH, "bt.png");
//        if (file.exists()) {
//            file.delete();
//        }

//        try {
////            file.createNewFile();
////            FileOutputStream fos = new FileOutputStream(file);
////            BufferedOutputStream fosBuffer = new BufferedOutputStream(fos);
////            BaseActivity.scale_bitmap.compress(Bitmap.CompressFormat.PNG, 90, fosBuffer);
//            fosBuffer.flush();
//            fosBuffer.close();
////            fos.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        long save_image_end = System.currentTimeMillis();
        Log.i("test", "-----------------------------save image need time is "+(save_image_end - save_image_start));

        if (detectBt!=null && !detectBt.isRecycled())
        {
            // 回收并且置为null
            //detectBt.recycle();
            detectBt = null;
        }
        if (rotateBt!=null && !rotateBt.isRecycled())
        {
            //rotateBt.recycle();
            rotateBt = null;
        }

        System.gc();


        long time_getDetectImage_end=System.currentTimeMillis();
//        BaseActivity.pretreatment_time = (time_getDetectImage_end - time_getDetectImage_start);
        Log.i("test", "getDetectImage() need time is : "+(time_getDetectImage_end-time_getDetectImage_start)+" ms.");
        Log.i("test", "get the detect image successful");
    }

    /**
     * change raw byte array to RGB bitmap
     * @param data
     * @param width
     * @param height
     * @return
     */
    public static Bitmap rawByteArray2RGBABitmap2(byte[] data, int width, int height) {
        long time_start=System.currentTimeMillis();
        int frameSize = width * height;
        int[] rgba = new int[frameSize];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                int y = (0xff & ((int) data[i * width + j]));
                int u = (0xff & ((int) data[frameSize + (i >> 1) * width + (j & ~1) + 0]));
                int v = (0xff & ((int) data[frameSize + (i >> 1) * width + (j & ~1) + 1]));
                y = y < 16 ? 16 : y;

                int r = Math.round(1.164f * (y - 16) + 1.596f * (v - 128));
                int g = Math.round(1.164f * (y - 16) - 0.813f * (v - 128) - 0.391f * (u - 128));
                int b = Math.round(1.164f * (y - 16) + 2.018f * (u - 128));

                r = r < 0 ? 0 : (r > 255 ? 255 : r);
                g = g < 0 ? 0 : (g > 255 ? 255 : g);
                b = b < 0 ? 0 : (b > 255 ? 255 : b);

                rgba[i * width + j] = 0xff000000 + (b << 16) + (g << 8) + r;
            }

        data = null;
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmp.setPixels(rgba, 0 , width, 0, 0, width, height);
        long time_end=System.currentTimeMillis();
        Log.i("test", "rawByteArray2RGBABitmap2() need time is : "+(time_end - time_start)+" ms.");
        return bmp;
    }
}
