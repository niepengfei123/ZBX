package com.jy.jz.zbx.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.adapter.CookBookMethodAdapter;
import com.jy.jz.zbx.adapter.CookBookSolidAdapter;
import com.jy.jz.zbx.bean.CookBookRoot;
import com.jy.jz.zbx.bean.Liaos;
import com.jy.jz.zbx.bean.Lists;
import com.jy.jz.zbx.bean.Recipe_detial;
import com.jy.jz.zbx.bean.ZuoFa;
import com.jy.jz.zbx.bean.ZuoFaRoot;
import com.jy.jz.zbx.service.MyListView;
import com.jy.jz.zbx.utils.ConfigUtils;
import com.jy.jz.zbx.utils.EncryptUtil;
import com.jy.jz.zbx.utils.OkHttpUtils;
import com.jy.jz.zbx.utils.T;
import com.jy.jz.zbx.utils.WaitingDialog;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mic-roo on 2016/11/29 0029.
 * 食谱详情页
 */

public class CookBookDetails extends BaseActivity implements View.OnClickListener {
    private ImageView img_user_center;
    private SimpleDraweeView sdv_cookbook;
    private TextView tv_cookbook_names;
    private MyListView my_lv_solid, my_lv_method;
    private Context context;
    private String uri, name;
    private int id;
    private WaitingDialog waitingDialog;

    private List<ZuoFa> zuoFaList;
    private Recipe_detial recipe_detial;
    private List<Lists> liaosList;
    private CookBookMethodAdapter cookBookMethodAdapter;
    private CookBookSolidAdapter cookBookSolidAdapter;

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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookbook_details);
        context = this;
        waitingDialog = new WaitingDialog(context);
        Intent intent = getIntent();
        if (intent != null) {
            id = Integer.parseInt(intent.getExtras().getString("id"));
            uri = intent.getExtras().getString("uri");
            name = intent.getExtras().getString("name");
        }
        initView();
        initData();
        recipeDetail();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initView() {
        liaosList = new ArrayList<>();
        zuoFaList = new ArrayList<>();

        img_user_center = (ImageView) findViewById(R.id.img_user_center);
        sdv_cookbook = (SimpleDraweeView) findViewById(R.id.sdv_cookbook);
        tv_cookbook_names = (TextView) findViewById(R.id.tv_cookbook_names);
        my_lv_solid = (MyListView) findViewById(R.id.my_lv_solid);
        my_lv_method = (MyListView) findViewById(R.id.my_lv_method);
    }

    private void initData() {
        img_user_center.setOnClickListener(this);
        sdv_cookbook.setImageURI(Uri.parse(uri));
        tv_cookbook_names.setText(name);
    }

    //菜谱详情
    private void recipeDetail() {
        mHandler.sendEmptyMessage(0);
        OkHttpUtils.getInstance().postForJsonAsynchronization(ConfigUtils.RECIPEDETAIL, requestInfo(), new OkHttpUtils.RequestCallBack<CookBookRoot>() {
            @Override
            public void onError(Request request, Exception e) {
                mHandler.sendEmptyMessage(1);
                T.showshort(context, request.toString());
            }

            @Override
            public void onResponse(CookBookRoot response) {
                if (response != null) {
                    mHandler.sendEmptyMessage(1);
                    if (response.getCode() != 2) {
                        recipe_detial = response.getRecipe_detial();
                        if (recipe_detial != null) {
                            Gson gson = new Gson();
                            Liaos liaos = gson.fromJson(recipe_detial.getLiaos(), Liaos.class);
                            List<Lists> lists = liaos.getList();
                            List<Lists> list_f = liaos.getList_f();
                            List<Lists> list_t = liaos.getList_t();
                            if (lists != null) {
                                liaosList.addAll(lists);
                            }
                            if (list_f != null) {
                                liaosList.addAll(list_f);
                            }
                            if (list_t != null) {
                                liaosList.addAll(list_t);
                            }
                            cookBookSolidAdapter = new CookBookSolidAdapter(context, liaosList);
                            my_lv_solid.setAdapter(cookBookSolidAdapter);
//                          zuoFaList=recipe_detial.getZuofa();
//                            Gson gson1=new Gson();
//                            List<JsonElement> list = new ArrayList();
//                            JsonParser jsonParser = new JsonParser();
//                            JsonElement jsonElement = jsonParser.parse(recipe_detial.getZuofa()); // 将json字符串转换成JsonElement
//                            JsonArray jsonArray = jsonElement.getAsJsonArray(); // 将JsonElement转换成JsonArray
//                            Iterator it = jsonArray.iterator(); // Iterator处理
//                            while (it.hasNext()) { // 循环
//                                jsonElement = (JsonElement) it.next(); // 提取JsonElement
//                                String json = jsonElement.toString(); // JsonElement转换成String
//                                ZuoFa zuoFa = gson.fromJson(json, ZuoFa.class);
////                                List<Zuofas> zuofases=gson.fromJson(zuoFa,Zuofas.class);
//                                zuoFaList.add(zuoFa);
//                            }
                            //数据重构
                            List<ZuoFa> zuofaList = new ArrayList<>();
                            String str = "{'zuofa':" + recipe_detial.getZuofa() + "}";
                            ZuoFaRoot zuoFaRoot = gson.fromJson(str, ZuoFaRoot.class);
                            if (zuoFaRoot != null) {
                                System.out.println(zuoFaRoot.getZuofa().size());
                                for (int i = 0; i < zuoFaRoot.getZuofa().size(); i++) {
                                    if (zuoFaRoot.getZuofa().get(i).getDt().equals("0")) {
                                        zuofaList.add(zuoFaRoot.getZuofa().get(i));
                                    } else if (zuoFaRoot.getZuofa().get(i).getDt().equals("1")) {
                                        zuofaList.get(zuofaList.size() - 1).setD_img(zuoFaRoot.getZuofa().get(i).getD());
                                        zuofaList.get(zuofaList.size() - 1).setW(zuoFaRoot.getZuofa().get(i).getW());
                                        zuofaList.get(zuofaList.size() - 1).setH(zuoFaRoot.getZuofa().get(i).getH());
                                    }
                                }
                                System.out.println(zuofaList.size());
                                zuoFaList.addAll(zuofaList);
                            }
                            cookBookMethodAdapter = new CookBookMethodAdapter(context, zuoFaList);
                            my_lv_method.setAdapter(cookBookMethodAdapter);
                        }
//                        cookbookAdapter.notifyDataSetChanged();
                    }
                    T.showshort(context, response.getCause());
                } else {
                    T.showshort(context, "网络繁忙，请稍后重试！");
                }

            }
        });
    }

    private String requestInfo() {
        String dotime = String.valueOf(System.currentTimeMillis() / 1000);//当前时间戳
        String Secret = "jfjdAX8sjl002jhgGDFgds235jfe54646jreqreqla13N62kl";

        String sign = EncryptUtil.md5(Secret + "dotime" + dotime + Secret);//MD5加密
//        String sign = EncryptUtil.md5(Secret + "dotime" + "1480386167" + Secret);//MD5加密
        String signs = sign.toUpperCase();//转大写

        JSONObject object = new JSONObject();
        try {
            object.put("sign", signs);
            object.put("dotime", dotime);
            object.put("id", id);
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
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CookBookDetails Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
