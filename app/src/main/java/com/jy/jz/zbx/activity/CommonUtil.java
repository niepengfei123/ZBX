package com.jy.jz.zbx.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @autor: guoyibing
 * @time: 2017/7/5/15:19
 * @describe:
 */

public class CommonUtil {

    private static final String TAG = "CommonUtil";

    public static final String DOWNLOAD_DIR_PATH = "/PhotoDemo";
    public static final String IMAGE_CACHE_PATH = DOWNLOAD_DIR_PATH + "/cache/image";

    public static void saveBitmap2File(File file, Bitmap bitmap) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            //          //如果需要改变大小(默认的是宽960×高1280),如改成宽600×高800
                      Bitmap newBM = bitmap.createScaledBitmap(bitmap, 256, 256, false);
            newBM.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Log.e(TAG, "保存图片成功 filePath = " + file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screentHeight = dm.heightPixels;

        return new Point(screenWidth, screentHeight);
    }

    public static File getCachePath() {
        String cachePath = getSDPath() + IMAGE_CACHE_PATH;

        File cacheFile = new File(cachePath);
        if (!cacheFile.exists()) {
            cacheFile.mkdirs();
        }
        return cacheFile;
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        } else {
            return "";
        }
        return sdDir.toString();
    }
}
