package com.jy.jz.zbx.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.activity.CookBookDetails;
import com.jy.jz.zbx.activity.SolidSearchActivity;
import com.jy.jz.zbx.activity.UserCenterActivity;
import com.jy.jz.zbx.adapter.CookBookAdapter;
import com.jy.jz.zbx.bean.CookBookBean;
import com.jy.jz.zbx.bean.Data;
import com.jy.jz.zbx.bean.RecipeRoot;
import com.jy.jz.zbx.service.MyGridView;
import com.jy.jz.zbx.utils.ConfigUtils;
import com.jy.jz.zbx.utils.EncryptUtil;
import com.jy.jz.zbx.utils.L;
import com.jy.jz.zbx.utils.OkHttpUtils;
import com.jy.jz.zbx.utils.T;
import com.jy.jz.zbx.utils.WaitingDialog;
import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by lijin on 2016/7/26.
 * 食谱
 */
public class SecondFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SecondFragment";
    protected Context mContext;
    protected View rootView;
    private ImageView img_user_center;
    private PullToRefreshGridView mgd_cookbook;
    private CookBookAdapter cookbookAdapter;
    private List<CookBookBean> cookBookList;
    private List<Data> dataList;
    private WaitingDialog waitingDialog;
    private PullToRefreshGridView mm;
    private String times, signs;
    private int temp = 1;
    private int refresh = 0;
    private TextView tv_search_more;
    private RelativeLayout rl_search;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.second_fragment, container, false);
        mContext = getActivity();
        waitingDialog = new WaitingDialog(mContext);
        initView();
        initData();
        getRecipeList(1, 20);
        return rootView;
    }


    //菜谱接口
    private void getRecipeList(int page, int pagesize) {
        mHandler.sendEmptyMessage(0);
        OkHttpUtils.getInstance().postForJsonAsynchronization(ConfigUtils.GETRECIPELIST, requestInfo(page, pagesize), new OkHttpUtils.RequestCallBack<RecipeRoot>() {
            @Override
            public void onError(Request request, Exception e) {
                mHandler.sendEmptyMessage(1);
                T.showshort(mContext, request.toString());
                mgd_cookbook.onRefreshComplete();
            }

            //            @Override
//            public void onError(Call call, Exception e) {
//                mHandler.sendEmptyMessage(1);
//                T.showshort(mContext, call.request().toString());
//                L.e("错误信息：" + call.request().toString() + e.toString());
////                scrollview_data.onRefreshComplete();
//            }
            @Override
            public void onResponse(RecipeRoot response) {
                if (response != null) {
                    if (refresh == 1) {
                        dataList.clear();
                    }
//                    livingList.clear();
                    mHandler.sendEmptyMessage(1);
                    if (response.getCode() != 2) {
                        if (response.getData() != null && response.getData().size() > 0) {
                            dataList.addAll(response.getData());
                        } else {
                            T.showshort(mContext, "没有更多数据了");
                        }
                        cookbookAdapter.notifyDataSetChanged();
                    }
                    T.showshort(mContext, response.getCause());
                } else {
                    T.showshort(mContext, "网络繁忙，请稍后重试！");
                }
                mgd_cookbook.onRefreshComplete();
            }
        });
    }

    private String requestInfo(int page, int pagesize) {
        String dotime = String.valueOf(System.currentTimeMillis() / 1000);//当前时间戳
        String Secret = "jfjdAX8sjl002jhgGDFgds235jfe54646jreqreqla13N62kl";

        String sign = EncryptUtil.md5(Secret + "dotime" + dotime + Secret);//MD5加密
//        String sign = EncryptUtil.md5(Secret + "dotime" + "1480386167" + Secret);//MD5加密
        signs = sign.toUpperCase();//转大写

//        Map<String, Object> object = new HashMap<String, Object>();
        JSONObject object = new JSONObject();
        try {
            object.put("sign", signs);
            object.put("dotime", dotime);
//            object.put("test",true);
//            if (tv_search_more != null) {
//                object.put("q", tv_search_more.getText().toString());
//            }

//            object.put("dotime", "1480386167");
            object.put("page", page);
            object.put("pagesize", pagesize);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return null;

    }


    public void initView() {
        dataList = new ArrayList<Data>();
//        cookBookList = new ArrayList<CookBookBean>();
//        cookBookList.add(new CookBookBean(R.mipmap.img_2, "红烧鲫鱼"));
//        cookBookList.add(new CookBookBean(R.mipmap.img_1, "鲫鱼豆腐汤"));
//        cookBookList.add(new CookBookBean(R.mipmap.img_3, "葱蒜鲫鱼"));
        img_user_center = (ImageView) rootView.findViewById(R.id.img_user_center);
        tv_search_more = (TextView) rootView.findViewById(R.id.tv_search_more);
        rl_search = (RelativeLayout) rootView.findViewById(R.id.rl_search);
        mgd_cookbook = (PullToRefreshGridView) rootView.findViewById(R.id.mgd_cookbook);
//        cookbookAdapter = new CookBookAdapter(mContext, cookBookList);
        cookbookAdapter = new CookBookAdapter(mContext, dataList);
        mgd_cookbook.setAdapter(cookbookAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initData() {
        img_user_center.setOnClickListener(this);
        rl_search.setOnClickListener(this);

        mgd_cookbook.setMode(PullToRefreshBase.Mode.BOTH);
        times = DateUtils.formatDateTime(mContext, System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        mgd_cookbook.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            // 下拉刷新 业务代码
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                mgd_cookbook.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
                mgd_cookbook.getLoadingLayoutProxy().setPullLabel("下拉刷新");
                mgd_cookbook.getLoadingLayoutProxy().setReleaseLabel("开始刷新");
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新时间:" + times);
//                teacherLists.clear();
                refresh = 1;
                getRecipeList(1, 20);
            }

            // 上拉刷新 业务代码
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                mgd_cookbook.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
                mgd_cookbook.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
                mgd_cookbook.getLoadingLayoutProxy().setReleaseLabel("开始加载");
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后加载时间:" + times);
                refresh = 2;
                getRecipeList(++temp, 10);

            }
        });

        //食谱详情页
        mgd_cookbook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, CookBookDetails.class);
                intent.putExtra("id", dataList.get(i).getId() + "");
                intent.putExtra("uri", dataList.get(i).getTitlepic());
                intent.putExtra("name", dataList.get(i).getTitle());
                startActivity(intent);

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
//        refresh=4;
//        getRecipeList(1, 20);
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
        temp = 1;
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_user_center:
                startActivity(new Intent(mContext, UserCenterActivity.class));
                break;
            case R.id.rl_search:
                startActivity(new Intent(mContext, SolidSearchActivity.class));
                break;
        }
    }

}
