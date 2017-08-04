package com.jy.jz.zbx.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.camera.simplewebcam2.CaffeMobile;
import com.camera.simplewebcam2.CameraPreview;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.activity.AddIngredientsActivity;
import com.jy.jz.zbx.activity.AddSolidActivity;
import com.jy.jz.zbx.activity.AiKitchenActivity;
import com.jy.jz.zbx.activity.ImgData;
import com.jy.jz.zbx.activity.ImgObjects;
import com.jy.jz.zbx.activity.ImgRoot;
import com.jy.jz.zbx.activity.IngredientsDetails;
import com.jy.jz.zbx.activity.Recognitioninfos;
import com.jy.jz.zbx.activity.SeePhotoActivity;
import com.jy.jz.zbx.activity.UserCenterActivity;
import com.jy.jz.zbx.adapter.AddAdapter;
import com.jy.jz.zbx.adapter.ImageRecognitionAdapter;
import com.jy.jz.zbx.application.ZBXApplication;
import com.jy.jz.zbx.bean.Data;
import com.jy.jz.zbx.service.Address;
import com.jy.jz.zbx.service.MyListView;
import com.jy.jz.zbx.service.SlideDelete;
import com.jy.jz.zbx.utils.APPUtils;
import com.jy.jz.zbx.utils.ConfigUtils;
import com.jy.jz.zbx.utils.L;
import com.jy.jz.zbx.utils.OkHttpUtils;
import com.jy.jz.zbx.utils.SPUtils;
import com.jy.jz.zbx.utils.T;
import com.jy.jz.zbx.utils.WaitingDialog;
import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.media.Constant;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static com.facebook.common.internal.ByteStreams.copy;

/**
 * Created by lijin on 2016/7/26.
 * 我的
 */
public class FirstFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "FirstFragment";
    protected Context mContext;
    protected View rootView;
    private ImageView img_user_center, img_min_add, img_ai_kitchen;
    private TextView tv_see_photo, tv_sort, tv_ai_kitchen;
    private PullToRefreshListView prlv_min;
    private AttributeSet attrs;
    private Bitmap bitmap = null;
    private CameraPreview cameraPreview;
    private CaffeMobile caffeMobile;
    private WaitingDialog waitingDialog;
    private int temp = 1;
    private int refresh = 0;
    private String times;
    List<ImgObjects> imgData;
    //    private PullToRefreshListView my_lv_loc;
    private List<Recognitioninfos> recognitioninfosList;
    private ImageRecognitionAdapter imageRecognitionAdapter;
    private String solid;
    private String[] arr = new String[]{};
    List<String> lists = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    private AddAdapter addAdapter;
    List<Address> data = new ArrayList<>();
    List<Address> data2 = new ArrayList<>();

    SwipeMenuListView smlv;
    private List<ApplicationInfo> mAppList;


    // 继续有多少个条目的delete被展示出来的集合
    private List<SlideDelete> slideDeleteArrayList = new ArrayList<>();

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            if (ZBXApplication.db.findAll(Address.class).size() > 0) {
                data2 = ZBXApplication.db.findAll(Address.class);
            }
        } catch (DbException e) {
        } finally {
            rootView = inflater.inflate(R.layout.first_fragment, container, false);
            mContext = getActivity();
            waitingDialog = new WaitingDialog(mContext);
//            mAppList =mContext.getPackageManager().getInstalledApplications(0);
            initView();
            initData();
            getTwoImg();
            return rootView;
        }
    }

    public void initView() {
        img_user_center = (ImageView) rootView.findViewById(R.id.img_user_center);
        img_min_add = (ImageView) rootView.findViewById(R.id.img_min_add);
        img_ai_kitchen = (ImageView) rootView.findViewById(R.id.img_ai_kitchen);
        tv_see_photo = (TextView) rootView.findViewById(R.id.tv_see_photo);
        tv_sort = (TextView) rootView.findViewById(R.id.tv_sort);
        tv_ai_kitchen = (TextView) rootView.findViewById(R.id.tv_ai_kitchen);
        prlv_min = (PullToRefreshListView) rootView.findViewById(R.id.prlv_min);

        smlv = (SwipeMenuListView) rootView.findViewById(R.id.my_lv_loc);

        img_user_center.setOnClickListener(this);
        img_min_add.setOnClickListener(this);
        tv_see_photo.setOnClickListener(this);
        tv_sort.setOnClickListener(this);
        img_ai_kitchen.setOnClickListener(this);
        tv_ai_kitchen.setOnClickListener(this);

//        for (int i = data.size() - 1; i >= 0; i--) {
//            data2.add(data.get(i));
//            if (data2.size() > 30) {
//                break;
//            }
//        }
        imgData = new ArrayList<>();
        imageRecognitionAdapter = new ImageRecognitionAdapter(mContext, imgData);
        prlv_min.setAdapter(imageRecognitionAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

    }

    public void initData() {

        prlv_min.setMode(PullToRefreshBase.Mode.BOTH);
        times = DateUtils.formatDateTime(mContext, System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        prlv_min.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            // 下拉刷新 业务代码
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                prlv_min.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
                prlv_min.getLoadingLayoutProxy().setPullLabel("下拉刷新");
                prlv_min.getLoadingLayoutProxy().setReleaseLabel("开始刷新");
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新时间:" + times);
                refresh = 1;
                getTwoImg();

            }

            // 上拉刷新 业务代码
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                prlv_min.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
                prlv_min.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
                prlv_min.getLoadingLayoutProxy().setReleaseLabel("开始加载");
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后加载时间:" + times);



                refresh = 2;
                getTwoImg();
            }
        });
//        String solids = (String) SPUtils.get(mContext, "solid", "");
//        if (solids != null) {
//            lists.add(solids);
//        }
        //添加数据
//        try {
//            if (ZBXApplication.db.findAll(Address.class).size() > 0) {
//                data2 = ZBXApplication.db.findAll(Address.class);
//            } 此段加了就会崩掉
//        } catch (DbException e) {
//        }
        addAdapter = new AddAdapter(mContext, data2);
//        addAdapter.notifyDataSetChanged();
        smlv.setAdapter(addAdapter);
//        my_lv_loc.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
//                if (i == SCROLL_STATE_FLING || i == SCROLL_STATE_TOUCH_SCROLL) {
//                    closeOtherItem();
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//
//            }
//        });
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // 创建一个删除item
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
                deleteItem.setTitle("删除");
                deleteItem.setTitleColor(getResources().getColor(R.color.white));
                deleteItem.setTitleSize(14);
                // 设置删除按钮的背景色
                deleteItem.setBackground(new ColorDrawable(Color.rgb(255, 0, 0)));
                // 设置删除按钮的宽度，必须要设置，不然不显示
                deleteItem.setWidth(130);
        /*// 设置图标
        deleteItem.setIcon(R.drawable.ic_delete);*/
                // 最后必须add进menu里
                menu.addMenuItem(deleteItem);
            }
        };
        smlv.setMenuCreator(creator);
        smlv.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        smlv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            // 事件响应，switch判断的是index，此处的index，
//            就是你上面的 menu.addMenuItem的元素的位置的下标，不难理解
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                ApplicationInfo item = mAppList.get(position);
                switch (index) {
//                    case 0:
//
//                        break;
                    default:
                        // 删除

//                        ConfigUtils.address = data2.get(position);
//                        String strs = ConfigUtils.address.getStartAdd();
                        try {
                            //删除数据库中的
                            List<Address> name = ZBXApplication.db.findAll(Address.class);
                            ZBXApplication.db.delete(name.get(position));
//                            T.showshort(mContext, "删除" + name.get(position));
                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                        data2.remove(position);
                        addAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
        smlv.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });
        smlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ConfigUtils.address = data2.get(i);
                String strs = ConfigUtils.address.getStartAdd();
                Intent intents = new Intent(mContext, AddIngredientsActivity.class);
                intents.putExtra("strs", strs);
                startActivity(intents);
            }
        });


//        my_lv_loc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                ConfigUtils.address = data2.get(i - 1);
//                String strs = ConfigUtils.address.getStartAdd();
//                Intent intents = new Intent(mContext, AddIngredientsActivity.class);
//                intents.putExtra("strs", strs);
//                startActivity(intents);
//            }
//        });

        prlv_min.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, IngredientsDetails.class);
                intent.putExtra("names", imgData.get(i - 1).getName());
                intent.putExtra("chineseIdeograph", imgData.get(i - 1).getChineseIdeograph());
                intent.putExtra("expDay", imgData.get(i - 1).getExpDay());
                intent.putExtra("saveDays", imgData.get(i - 1).getSaveDays());
//                intent.putExtra("id", imgData.get(i - 1).getId());
                startActivity(intent);
            }
        });
    }


    //    图像识别
    private void getTwoImg() {
        mHandler.sendEmptyMessage(0);
        OkHttpUtils.getInstance().postForJsonAsynchronization(ConfigUtils.GETTWOIMG, requestMap(), new OkHttpUtils.RequestCallBack<ImgRoot>() {
            @Override
            public void onError(Request request, Exception e) {
                mHandler.sendEmptyMessage(1);
                T.showshort(mContext, request.toString());
                prlv_min.onRefreshComplete();
            }

            @Override
            public void onResponse(ImgRoot response) {
                if (refresh == 1 || refresh == 2 || refresh == 3) {
                    imgData.clear();
                }
                mHandler.sendEmptyMessage(1);
                if (response != null) {
                    if (response.getCode() == 1) {
//                        imgData=response.getData();
//                        if (imgData != null) {
//                            List<Recognitioninfos>  recognitioninfos=imgData.get(0).getRecognitioninfos();
                        imgData.addAll(response.getData().getObjects());

//                        }
                        imageRecognitionAdapter.notifyDataSetChanged();
                    } else {
                        T.showshort(mContext, "暂无数据");
                    }
                    T.showshort(mContext, response.getMsg());
                } else {
                    T.showshort(mContext, "网络繁忙，请稍后重试！");
                }
                prlv_min.onRefreshComplete();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {

        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 101) {
                solid = data.getStringExtra("solid");
//                data2.clear();

                addAdapter.notifyDataSetChanged();
                try {
                    if (ZBXApplication.db.findAll(Address.class).size() > 0) {
                        data2.clear();
                        data2 = ZBXApplication.db.selector(Address.class).findAll();
                    }
                } catch (DbException e) {
                }
                addAdapter = new AddAdapter(mContext, data2);
                smlv.setAdapter(addAdapter);

            }

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_user_center:
                Intent intent = new Intent(mContext, UserCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_sort://按时间排序
                refresh = 3;
                getTwoImg();
//                Intent intent4 = new Intent(mContext, IngredientsDetails.class);
//                intent4.putExtra("names", "li");
//                intent4.putExtra("chineseIdeograph", "梨");
//                intent4.putExtra("expDay", "21");
//                intent4.putExtra("saveDays", "2");
//                startActivity(intent4);
                break;
            case R.id.tv_see_photo://查看照片
                Intent intentPhoto = new Intent(mContext, SeePhotoActivity.class);
                startActivity(intentPhoto);
                break;
            case R.id.img_min_add://添加食材
                Intent intentadd = new Intent(mContext, AddSolidActivity.class);
                startActivityForResult(intentadd, 100);
                break;
            case R.id.tv_ai_kitchen://智能厨房
                Intent intentai = new Intent(mContext, AiKitchenActivity.class);
                startActivity(intentai);
                break;
        }
    }


    private void closeOtherItem() {
        // 采用Iterator的原因是for是线程不安全的，迭代器是线程安全的
        ListIterator<SlideDelete> slideDeleteListIterator = slideDeleteArrayList.listIterator();

        while (slideDeleteListIterator.hasNext()) {
            SlideDelete slideDelete = slideDeleteListIterator.next();
            slideDelete.isShowDelete(false);
        }
        slideDeleteArrayList.clear();

    }

}
