package com.jy.jz.zbx.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.adapter.CookBookAdapter;
import com.jy.jz.zbx.bean.CookBookBean;
import com.jy.jz.zbx.bean.Data;
import com.jy.jz.zbx.bean.RecipeRoot;
import com.jy.jz.zbx.utils.ConfigUtils;
import com.jy.jz.zbx.utils.EncryptUtil;
import com.jy.jz.zbx.utils.OkHttpUtils;
import com.jy.jz.zbx.utils.T;
import com.jy.jz.zbx.utils.WaitingDialog;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mic-roo on 2016/12/6 0006.
 * 菜谱搜索
 */

public class CooBookSearch extends BaseActivity implements View.OnClickListener {

    protected Context mContext;
    protected View rootView;
    private ImageView img_user_center;
    private PullToRefreshGridView mgd_cookbook_2;
    private CookBookAdapter cookbookAdapter;
    private List<CookBookBean> cookBookList;
    private List<Data> dataList;
    private WaitingDialog waitingDialog;
    private PullToRefreshGridView mm;
    private String times, signs;
    private int temp = 1;
    private int refresh = 0;
    private String keyword;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coobook_search);
        mContext = this;
        waitingDialog = new WaitingDialog(mContext);
        Intent intent = getIntent();
        if (intent != null) {
            keyword = intent.getExtras().getString("chineseIdeograph");
        }

        initView();
        initData();
        getRecipeList(1, 20);
    }

    //菜谱接口
    private void getRecipeList(int page, int pagesize) {
        mHandler.sendEmptyMessage(0);
        OkHttpUtils.getInstance().postForJsonAsynchronization(ConfigUtils.GETRECIPELIST, requestInfo(page, pagesize), new OkHttpUtils.RequestCallBack<RecipeRoot>() {
            @Override
            public void onError(Request request, Exception e) {
                mHandler.sendEmptyMessage(1);
                T.showshort(mContext, request.toString());
                mgd_cookbook_2.onRefreshComplete();
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
                mgd_cookbook_2.onRefreshComplete();
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
            if (keyword != null) {
                object.put("q", keyword);
            }

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
        img_user_center = (ImageView) findViewById(R.id.img_user_center);
        mgd_cookbook_2 = (PullToRefreshGridView) findViewById(R.id.mgd_cookbook_2);
//        cookbookAdapter = new CookBookAdapter(mContext, cookBookList);
        cookbookAdapter = new CookBookAdapter(mContext, dataList);
        mgd_cookbook_2.setAdapter(cookbookAdapter);
    }

    public void initData() {
        img_user_center.setOnClickListener(this);

        mgd_cookbook_2.setMode(PullToRefreshBase.Mode.BOTH);
        times = DateUtils.formatDateTime(mContext, System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        mgd_cookbook_2.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            // 下拉刷新 业务代码
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                mgd_cookbook_2.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
                mgd_cookbook_2.getLoadingLayoutProxy().setPullLabel("下拉刷新");
                mgd_cookbook_2.getLoadingLayoutProxy().setReleaseLabel("开始刷新");
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新时间:" + times);
//                teacherLists.clear();
                refresh = 1;
                getRecipeList(1, 20);
            }

            // 上拉刷新 业务代码
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                mgd_cookbook_2.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
                mgd_cookbook_2.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
                mgd_cookbook_2.getLoadingLayoutProxy().setReleaseLabel("开始加载");
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后加载时间:" + times);
                refresh = 2;
                getRecipeList(++temp, 10);

            }
        });

        //食谱详情页
        mgd_cookbook_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    public void onDestroy() {
        temp = 1;
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_user_center:
                finish();
                break;
        }
    }

}
