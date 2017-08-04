package com.jy.jz.zbx.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.activity.ImgData;
import com.jy.jz.zbx.activity.Imgs;
import com.umeng.socialize.media.Constant;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by Mic-roo on 2016/12/2 0002.
 */

public class SeePhotoAdapter extends BaseAdapter {
    private Context context;
    private List<Imgs> imgList;

    public SeePhotoAdapter(Context context, List<Imgs> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_see_photo, null);
            viewHolder.sdv_photo = (SimpleDraweeView) view.findViewById(R.id.sdv_photo);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Imgs imgData = imgList.get(i);
        imgData.getId();
        if (imgData.getUrl() != null) {
            viewHolder.sdv_photo.setImageURI(Uri.parse(imgData.getUrl()));
        }
//        if (imgList.get(i) != null) {
//            if (imgList.get(0) != null) {
//                Matrix matrix = new Matrix();
//                Bitmap bt = BitmapFactory.decodeFile(imgList.get(0).getUrl());
////                Bitmap bt=   getBitmapFromUri(imgList.get(0).getUrl());
////            Bitmap bitmap = ((BitmapDrawable) context.getResources().getDrawable().getBitmap();
//                // 设置旋转角度
//                matrix.setRotate(90);
//                // 重新绘制Bitmap
//                bt = Bitmap.createBitmap(bt, 0, 0, bt.getWidth(), bt.getHeight(), matrix, true);
//                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bt, null,null));
//                viewHolder.sdv_photo.setImageURI(uri);
//
//            }
//
//            if (imgList.get(1) != null) {
//                viewHolder.sdv_photo.setImageURI(Uri.parse(imgList.get(1).getUrl()));
//            }
//
//        }

        return view;
    }

//    /* uri转化为bitmap */
//    private Bitmap getBitmapFromUri(Ur uri) {
//        try {
//// 读取uri所在的图片
//            Bitmap bitmaps = MediaStore.Images.Media.getBitmap(
//                    context.getContentResolver(), uri);
//            return bitmaps;
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            return null;
//        }

//
//    }

    private class ViewHolder {
        private SimpleDraweeView sdv_photo;
    }
}
