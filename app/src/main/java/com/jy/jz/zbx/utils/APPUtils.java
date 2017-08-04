package com.jy.jz.zbx.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.jy.jz.zbx.activity.TakePhotoActivity;
import com.jy.jz.zbx.application.ZBXApplication;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import kr.co.namee.permissiongen.PermissionGen;

/**
 * Created by lijin on 2016/7/26.
 * 工具类
 */
public class APPUtils {

    public static final int cameraRequestPermissionsCode = 101;//相机权限请求码
    /**
     * 请求权限
     *
     * @param context     上下文
     * @param requestCode 请求code
     * @param permissions 权限名，可以一次多个
     */
    public static void requestPermissions(Context context, int requestCode, String[] permissions) {
        PermissionGen.needPermission((Activity) context, 100,
                new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.WRITE_CONTACTS
                }
        );
    }

    /**
     * 友盟需要的权限
     *
     * @param context     上下文
     * @param requestCode 请求code
     */
    public static void youmengRequestPermissions(Context context, int requestCode) {
        String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS};
        PermissionGen.needPermission((Activity) context, requestCode,
                mPermissionList
        );
    }

    /**
     * 保留两位小数
     *
     * @param src
     * @return
     */
    public static double keepTwoDecimalPlaces(double src) {
        double targe = 0.00d;
        BigDecimal b = new BigDecimal(src);
        targe = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return targe;
    }

    /**
     * 获取手机信息
     *
     * @return
     */
    public static JSONObject getAPPInfo() {

        JSONObject mob_data = new JSONObject();
        try {
            mob_data.put("mobile_id", "sdfsdfsfddf2131");
            mob_data.put("model", android.os.Build.MANUFACTURER + android.os.Build.MODEL);
            mob_data.put("sysName", "Android" + android.os.Build.VERSION.RELEASE);
            mob_data.put("localsizeModel", "Android");
            mob_data.put("name", android.os.Build.MANUFACTURER);
            mob_data.put("systemVersion", android.os.Build.VERSION.SDK);
            mob_data.put("appVersionCode", getVersionCode());
            mob_data.put("appVersion", getAppVersionName());

        } catch (JSONException e) {

            e.printStackTrace();
        }
        return mob_data;
    }


    /**
     * 获取版本号
     *
     * @return
     */
    public static String getVersionCode() {
        // 获取packagemanager的实例
        PackageManager packageManager = ZBXApplication.applicationContext.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(ZBXApplication.applicationContext.getPackageName(), 0);
            int version = packInfo.versionCode;
            return version + "";
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本名
     *
     * @return
     */
    public static String getAppVersionName() {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = ZBXApplication.applicationContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ZBXApplication.applicationContext.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }


    /**
     *
     * @param title 分享标题
     * @param content 分享内容
     * @param imgUrl//分享图片url
     * @param tagurl//分享目标url
     * @param umShareListener 分享回调监听
     */
    public  static void share(Context context,String title,String content,String imgUrl,String tagurl,UMShareListener umShareListener){
        UMImage image=new UMImage(context,imgUrl);
        new ShareAction((Activity)context).setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                .withText(content)
                .withMedia(image)
                .withTitle(title)
                .withTargetUrl(tagurl)
                .setCallback(umShareListener)
                .open();

    }

    /**
     *
     * @param title 分享标题
     * @param content 分享内容
     * @param imgName//分享本地图片的地址
     * @param tagurl//分享目标url
     * @param umShareListener 分享回调监听
     */
    public static  void share(Context context,String title, String content, int imgName, String tagurl, UMShareListener umShareListener){
        UMImage image = new UMImage(context,
                BitmapFactory.decodeResource(context.getResources(),imgName ));
        new ShareAction((Activity) context).setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                .withText(content)
                .withMedia(image)
                .withTitle(title)
                .withTargetUrl(tagurl)
                .setCallback(umShareListener)
                .open();

    }

    // UDP设备发现
    public static byte[] UDP_FIND_MOUDLE = new byte[]{(byte) 0xcc, (byte) 0x00, (byte) 0x00,
            (byte) 0x01, (byte) 0x00,
            (byte) 0x00, (byte) 0x07, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0xdd, (byte) 0x01,
            (byte) 0x00,
            (byte) 0x00, (byte) 0x00};

    public static String bytesToHexString(byte[] src, int len) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < len; i++) {
            long v = src[i] & 0xFF;
            String hv = Long.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static int hexToInt(String value) {
        return Integer.parseInt(value, 16);
    }
    /**
     * 获取相机相册权限
     *
     * @param
     * @param requestCode
     */
    public static void getCameraAndPhotoPermission(Context context, int requestCode) {
        String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        PermissionGen.needPermission((Activity) context, requestCode, mPermissionList);
    }
    /**
     * 调用相机
     *
     * @param activity      当前窗口
     * @param requestCode   请求码
     * @param savedFileName 临时照片的全路径
     * @return
     */

    public static boolean requestTakePhoto(Activity activity, int requestCode, String savedFileName) {

        boolean result = false;
        try {
            Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用摄像头拍照
            // 如果指定了目标uri，在返回data时就没有数据，如果没有指定uri，则data就返回有数据
            //解决7.0调用相机崩溃
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            Log.e("currentapiVersion", "currentapiVersion====>" + currentapiVersion);
            if (currentapiVersion < 24) {
                getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(savedFileName)));
                activity.startActivityForResult(getImageByCamera, requestCode);
            } else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, new File(savedFileName).getAbsolutePath());
                Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                activity.startActivityForResult(getImageByCamera, requestCode);
            }
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 从系统图库里选择图片
     *
     * @param fragment   当前窗口
     * @param resultCode 响应数字
     * @return boolean 执行成功返回true
     */
    public static boolean selectImageFromAlbum(TakePhotoActivity fragment, int resultCode) {
        boolean result = false;
        try {
            Intent getImage = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            fragment.startActivityForResult(getImage, resultCode);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 截取图片
     *
     * @param activityCurrent 当前窗口
     * @param uri             图片的URI
     * @param outputX         返回数据的时候的 X 像素大小
     * @param outputY         返回的时候 Y 的像素大小。
     * @param requestCode     返回标识码
     * @param isSspect        裁剪框是否按照所指定的比例出现，不设定裁剪框就可以随意调整
     */
    public static void cropImage(Activity activityCurrent, Uri uri, String headFrontTempPath, int outputX, int outputY, int requestCode,
                                 Boolean isSspect) {
        File file = new File(headFrontTempPath);
        Uri photoURI = FileProvider.getUriForFile(activityCurrent, "com.jy.jz.zbx.fileprovider", file);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.setDataAndType(photoURI, "image/*"); // 这个参数是确定要选择的内容为图片
        intent.setDataAndType(uri, "image/*"); // 这个参数是确定要选择的内容为图片
//        intent.setDataAndType(getImageContentUri(activityCurrent,file), "image/*"); // 这个参数是确定要选择的内容为图片
        intent.putExtra("crop", "true"); // 设置了参数，就会调用裁剪，如果不设置，就会跳过裁剪的过程。

        // 设置aspectX 与 aspectY 后，裁剪框会按照所指定的比例出现，放大缩小都不会更改。如果不指定，那么 裁剪框就可以随意调整了。
        if (isSspect) {
            intent.putExtra("aspectX", 1); // 这个是裁剪时候的 裁剪框的 X 方向的比例。
            intent.putExtra("aspectY", 1); // 同上Y 方向的比例. (注意： aspectX, aspectY
            // ，两个值都需要为 整数，如果有一个为浮点数，就会导致比例失效。)
        } else {
            intent.putExtra("aspectX", outputX / 100); // 这个是裁剪时候的 裁剪框的 X 方向的比例。
            intent.putExtra("aspectY", outputY / 100); // 同上Y 方向的比例. (注意： aspectX, aspectY
        }

        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边

        intent.putExtra("outputX", outputX); // 返回数据的时候的 X 像素大小。
        intent.putExtra("outputY", outputY); // 返回的时候 Y 的像素大小。
        // 以上两个值，设置之后会按照两个值生成一个Bitmap,
        // 两个值就是这个bitmap的横向和纵向的像素值，如果裁剪的图像和这个像素值不符合，那么空白部分以黑色填充。

//        intent.putExtra("output",Uri.fromFile(new File(FilePathUtil.getPhotoFile() + "temp" + FilePathUtil.IMAGE_TYPE)));
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.putExtra("return-data", false);// 是否要返回值。忘加了，总是取得空值
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 返回格式
        intent.putExtra("noFaceDetection", true); // 是否去除面部检测，
        activityCurrent.startActivityForResult(intent, requestCode);


    }
    public static byte[] readStream(String imagepath) throws Exception {
        FileInputStream fs = new FileInputStream(imagepath);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while (-1 != (len = fs.read(buffer))) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        fs.close();
        return outStream.toByteArray();
    }
    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getDpi(Context context){
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi=displayMetrics.heightPixels;
        }catch(Exception e){
            e.printStackTrace();
        }
        return dpi;
    }
    /**
     * 获取 虚拟按键的高度
     * @param context
     * @return
     */
    public static  int getBottomStatusHeight(Context context){
        int totalHeight = getDpi(context);

        int contentHeight = getScreenHeight(context);

        return totalHeight  - contentHeight;
    }
    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

}
