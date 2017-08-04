package com.jy.jz.zbx.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jy.jz.zbx.R;
import com.jy.jz.zbx.adapter.CookBookAdapter;
import com.jy.jz.zbx.bean.Data;
import com.jy.jz.zbx.bean.RecipeRoot;
import com.jy.jz.zbx.service.MyGridView;
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
 * Created by Mic-roo on 2016/12/9 0009.
 * 自主添加的食材详情
 */

public class AddIngredientsActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_foods_names, tv_more;
    private ImageView img_user_center;
    private Context context;
    private MyGridView my_lv_foods;
    private CookBookAdapter cookbookAdapter;
    private List<Data> dataList;
    private WaitingDialog waitingDialog;
    private String chineseIdeograph, signs;
    private EditText tv_surplus;

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
        setContentView(R.layout.activity_add_lngre);
        context = this;
        waitingDialog = new WaitingDialog(context);
        Intent intent = getIntent();
        if (intent != null) {
            chineseIdeograph = intent.getExtras().getString("strs");
        }
        initView();
        initData();
        getRecipeList(1, 3);
    }

    private void initView() {
        tv_foods_names = (TextView) findViewById(R.id.tv_foods_names);
        tv_more = (TextView) findViewById(R.id.tv_more);
        img_user_center = (ImageView) findViewById(R.id.img_user_center);
        tv_surplus = (EditText) findViewById(R.id.tv_surplus);
        my_lv_foods = (MyGridView) findViewById(R.id.my_lv_foods);

    }

    private void initData() {
        dataList = new ArrayList<>();
        img_user_center.setOnClickListener(this);
        tv_more.setOnClickListener(this);
        tv_foods_names.setText(chineseIdeograph);

        my_lv_foods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, CookBookDetails.class);
                intent.putExtra("id", dataList.get(i).getId() + "");
                intent.putExtra("uri", dataList.get(i).getTitlepic());
                intent.putExtra("name", dataList.get(i).getTitle());
                startActivity(intent);
            }
        });
    }

    //菜谱接口
    private void getRecipeList(int page, int pagesize) {
        mHandler.sendEmptyMessage(0);
        OkHttpUtils.getInstance().postForJsonAsynchronization(ConfigUtils.GETRECIPELIST, requestInfo(page, pagesize), new OkHttpUtils.RequestCallBack<RecipeRoot>() {
            @Override
            public void onError(Request request, Exception e) {
                mHandler.sendEmptyMessage(1);
                T.showshort(context, request.toString());

            }

            @Override
            public void onResponse(RecipeRoot response) {
                if (response != null) {
//                    livingList.clear();
                    mHandler.sendEmptyMessage(1);
                    if (response.getCode() != 2) {
                        if (response.getData() != null && response.getData().size() > 0) {
                            dataList.addAll(response.getData());
                            cookbookAdapter = new CookBookAdapter(context, dataList);
                            my_lv_foods.setAdapter(cookbookAdapter);
                        } else {
                            T.showshort(context, "没有更多数据了");
                        }

                    }
                    T.showshort(context, response.getCause());
                } else {
                    T.showshort(context, "网络繁忙，请稍后重试！");
                }

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
            object.put("q", chineseIdeograph);
//            object.put("dotime", "1480386167");
            object.put("page", page);
            object.put("pagesize", pagesize);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return null;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                Intent intent1 = new Intent(context, CooBookSearch.class);
                intent1.putExtra("chineseIdeograph", chineseIdeograph);
                startActivity(intent1);
                break;
            case R.id.img_user_center:
                finish();
                break;
        }
    }
}
