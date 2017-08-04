package com.jy.jz.zbx.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.adapter.SeePhotoAdapter;
import com.jy.jz.zbx.bean.RecipeRoot;
import com.jy.jz.zbx.utils.ConfigUtils;
import com.jy.jz.zbx.utils.L;
import com.jy.jz.zbx.utils.OkHttpUtils;
import com.jy.jz.zbx.utils.T;
import com.jy.jz.zbx.utils.WaitingDialog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Mic-roo on 2016/12/2 0002.
 * 查看冰箱照片
 */

public class SeePhotoActivity extends BaseActivity implements View.OnClickListener {
    private Context context;
    private PullToRefreshListView lv_photo;
    private ImageView img_user_center;
    private WaitingDialog waitingDialog;
    private List<Imgs> imgList;
    private SeePhotoAdapter seePhotoAdapter;
    private String times;
    private TextView tv_seePhoto;
    private int temp = 1;
    private int refresh = 0;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                waitingDialog.showWaitingDialog();
            } else if (msg.what == 1) {
                waitingDialog.dismissWaitingDialog();
            } else if (msg.what == 2) {

            } else if (msg.what == 3) {


            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_photo);
        context = this;
        waitingDialog = new WaitingDialog(context);

        initView();
        getTwoImg();
    }

    private void initView() {
        lv_photo = (PullToRefreshListView) findViewById(R.id.lv_photo);
        img_user_center = (ImageView) findViewById(R.id.img_user_center);
        tv_seePhoto = (TextView) findViewById(R.id.tv_seePhoto);
        lv_photo.setMode(PullToRefreshBase.Mode.BOTH);
        times = DateUtils.formatDateTime(this, System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        lv_photo.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            // 下拉刷新 业务代码
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                lv_photo.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
                lv_photo.getLoadingLayoutProxy().setPullLabel("下拉刷新");
                lv_photo.getLoadingLayoutProxy().setReleaseLabel("开始刷新");
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新时间:" + times);
                refresh = 1;
                getTwoImg();

            }

            // 上拉刷新 业务代码
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                lv_photo.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
                lv_photo.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
                lv_photo.getLoadingLayoutProxy().setReleaseLabel("开始加载");
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后加载时间:" + times);

                refresh = 2;
                getTwoImg();
            }
        });

        imgList = new ArrayList<Imgs>();
        img_user_center.setOnClickListener(this);
        tv_seePhoto.setOnClickListener(this);
        seePhotoAdapter = new SeePhotoAdapter(context, imgList);
        lv_photo.setAdapter(seePhotoAdapter);
    }

    //获取图片接口
    private void getTwoImg() {
        mHandler.sendEmptyMessage(0);
        OkHttpUtils.getInstance().postForJsonAsynchronization(ConfigUtils.GETTWOIMG, requestMap(), new OkHttpUtils.RequestCallBack<ImgRoot>() {
            @Override
            public void onError(Request request, Exception e) {
                mHandler.sendEmptyMessage(1);
                T.showshort(context, request.toString());
                lv_photo.onRefreshComplete();
            }

            @Override
            public void onResponse(ImgRoot response) {
                if (refresh == 1 || refresh == 2) {
                    imgList.clear();
                }
                mHandler.sendEmptyMessage(1);
                if (response != null) {
                    if (response.getCode() == 1) {

                        imgList.addAll(response.getData().getImgs());
                        //倒叙显示
                        Collections.reverse(imgList);
//                        for (Imgs imgs : imgList) {
//                          List<Imgs>  mImgs=new ArrayList<Imgs>();
//                            mImgs.add(imgs);
//                            T.showshort(context,imgs.getId()+"");
//                        }
                        seePhotoAdapter.notifyDataSetChanged();
                    } else {
                        T.showshort(context, "暂无数据");
                    }
                    T.showshort(context, response.getMsg());
                } else {
                    T.showshort(context, "网络繁忙，请稍后重试！");
                }
                lv_photo.onRefreshComplete();
            }
        });
    }

    private String requestMap() {
        JSONObject object = new JSONObject();
        try {
            object.put("img.pid", 1);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return null;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_user_center:
                finish();
                break;
            case R.id.tv_seePhoto://重新拍照
                addUploadIst();
                break;
        }
    }

    //发送重新拍照的指令接口
    private void addUploadIst() {
        mHandler.sendEmptyMessage(0);
        OkHttpUtils.getInstance().postForJsonAsynchronization(ConfigUtils.ADDUPLOADIST, requestMapAdd(), new OkHttpUtils.RequestCallBack<ImgRoot>() {
            @Override
            public void onError(Request request, Exception e) {
                mHandler.sendEmptyMessage(1);
                T.showshort(context, request.toString());
            }

            @Override
            public void onResponse(ImgRoot response) {
                mHandler.sendEmptyMessage(1);
                if (response != null) {
                    if (response.getCode() == 1) {
                        T.showshort(context, "操作成功");
                    } else if (response.getCode() == 0) {
                        T.showshort(context, "操作失败请重试");
                    } else if (response.getCode() == 2) {
                        T.showshort(context, "暂无数据");
                    }

                } else {
                    T.showshort(context, "网络繁忙，请稍后重试！");
                }

            }
        });
    }

    private String requestMapAdd() {
        JSONObject object = new JSONObject();
        try {
            object.put("user.id", 1);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return null;
    }
}
