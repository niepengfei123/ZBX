package com.jy.jz.zbx.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jy.jz.zbx.R;
import com.jy.jz.zbx.adapter.CookBookAdapter;
import com.jy.jz.zbx.bean.Data;
import com.jy.jz.zbx.bean.RecipeRoot;
import com.jy.jz.zbx.service.MyGridView;
import com.jy.jz.zbx.utils.APPUtils;
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
 * Created by Mic-roo on 2016/12/7 0007.
 * 食材详情
 */

public class IngredientsDetails extends BaseActivity implements View.OnClickListener, TextWatcher {
    private ImageView img_user_center, img_names_change;
    private TextView tv_foods_names, tv_more, tv_shelf_life, tv_11, tv_changes_ok;
    private EditText tv_surplus, ed_weight;
    private Button btn_heat, btn_fat, btn_protein, btn_sugar;
    private MyGridView my_lv_foods;
    private WaitingDialog waitingDialog;
    private Context context;
    private CookBookAdapter cookbookAdapter;
    private List<Data> dataList;
    private String times, signs;
    private String names, chineseIdeograph;
    private int expDay, saveDays, id, num;
    Double heat, fat, sugar, protein;
    private Boolean isOk = true;

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
        setContentView(R.layout.activity_ingredients_details);
        context = this;
        waitingDialog = new WaitingDialog(context);
        Intent intent = getIntent();
        if (intent != null) {
            names = intent.getExtras().getString("names");
            chineseIdeograph = intent.getExtras().getString("chineseIdeograph");
            saveDays = intent.getExtras().getInt("saveDays");
            expDay = intent.getExtras().getInt("expDay");
//            id = intent.getExtras().getInt("id");
        }
        initView();
        initData();
        getRecipeList(1, 3);
    }

    private void initView() {
        tv_11 = (TextView) findViewById(R.id.tv_11);
        tv_foods_names = (TextView) findViewById(R.id.tv_foods_names);
        tv_shelf_life = (TextView) findViewById(R.id.tv_shelf_life);
        tv_changes_ok = (TextView) findViewById(R.id.tv_changes_ok);
        tv_surplus = (EditText) findViewById(R.id.tv_surplus);
        tv_more = (TextView) findViewById(R.id.tv_more);
        my_lv_foods = (MyGridView) findViewById(R.id.my_lv_foods);
        img_user_center = (ImageView) findViewById(R.id.img_user_center);
        img_names_change = (ImageView) findViewById(R.id.img_names_change);
        btn_sugar = (Button) findViewById(R.id.btn_sugar);
        btn_protein = (Button) findViewById(R.id.btn_protein);
        btn_fat = (Button) findViewById(R.id.btn_fat);
        btn_heat = (Button) findViewById(R.id.btn_heat);
        ed_weight = (EditText) findViewById(R.id.ed_weight);

        tv_surplus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String trim = null;
                if (TextUtils.isEmpty(tv_surplus.getText().toString().trim())) {
                    trim = null;
                } else {
                    trim = tv_surplus.getText().toString().trim();

                    int i3 = Integer.parseInt(trim);
                    if (i3 > expDay + saveDays) {
                        num = Integer.parseInt(trim);
                        T.showshort(context, expDay + saveDays + "");
                    } else {
                        num = Integer.parseInt(trim);
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void initData() {
        dataList = new ArrayList<Data>();
        ed_weight.addTextChangedListener(this);
        tv_11.addTextChangedListener(this);
        if (chineseIdeograph.equals("灌装啤酒-哈尔滨")) {
            tv_foods_names.setText("啤酒");
        } else {
            tv_foods_names.setText(chineseIdeograph);
        }
        int days = expDay + saveDays;
        tv_surplus.setText(expDay + "");
        tv_shelf_life.setText(days + "天");
        //根据食材名字做出相应的数据变化
//        names = tv_foods_names.getText().toString();
        switch (names) {
            case "jiyu":
                img_names_change.setBackgroundResource(R.mipmap.jiyu_2);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 108) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 2.7) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 3.8) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 17.1) + "");
                break;
            case "qiezi":
                img_names_change.setBackgroundResource(R.mipmap.qiezi);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 23) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0.2) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 3.6) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 1.1) + "");

                break;
            case "li":
                img_names_change.setBackgroundResource(R.mipmap.li);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 50) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0.2) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 10.2) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0.4) + "");
                break;
            case "pingguo":
                img_names_change.setBackgroundResource(R.mipmap.pingguo);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 54) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0.2) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 12.3) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0.2) + "");
                break;
            case "yumi":
                img_names_change.setBackgroundResource(R.mipmap.yumi);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 108) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 2.7) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 3.8) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 17.1) + "");
                break;
            case "xilanhua":
                img_names_change.setBackgroundResource(R.mipmap.xilanhua);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 36) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0.6) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 2.7) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 4.1) + "");

                break;
            case "wawacai":
                img_names_change.setBackgroundResource(R.mipmap.baicai);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 18) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0.1) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 2.4) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 1.5) + "");

                break;
            case "doufu":
                img_names_change.setBackgroundResource(R.mipmap.doufu);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 82) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 3.7) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 3.8) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 8.1) + "");
                break;
            case "jidan":
                img_names_change.setBackgroundResource(R.mipmap.jidan);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 144) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 8.8) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 2.8) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 13.3) + "");
                break;
            case "haerbin":
                img_names_change.setBackgroundResource(R.mipmap.pijiu);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 32) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0.4) + "");
                break;
            case "hongjiu":
                img_names_change.setBackgroundResource(R.mipmap.putaojiu);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 74) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0.1) + "");
                break;
            case "yiiliniunai":
                img_names_change.setBackgroundResource(R.mipmap.niunai);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 54) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 3.2) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 3.4) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 3) + "");
                break;
            case "zhurou":
                img_names_change.setBackgroundResource(R.mipmap.zhurou);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 395) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 37) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 2.4) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 13.2) + "");
                break;
            case "jiaozi":
                img_names_change.setBackgroundResource(R.mipmap.jiaozi);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 870) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 11.9) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 18.2) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 7.2) + "");
                break;
            case "xuebi":
                img_names_change.setBackgroundResource(R.mipmap.yingliao);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 191) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 11) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0) + "");
                break;
            case "kele":
                img_names_change.setBackgroundResource(R.mipmap.yingliao);
                btn_heat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 43) + "");
                btn_fat.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0) + "");
                btn_sugar.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 10.8) + "");
                btn_protein.setText(APPUtils.keepTwoDecimalPlaces(2.5 * 0.1) + "");
                break;
        }

        img_user_center.setOnClickListener(this);
        tv_more.setOnClickListener(this);
        tv_changes_ok.setOnClickListener(this);
        tv_surplus.setOnClickListener(this);


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
            if (chineseIdeograph != null) {
                if (chineseIdeograph.equals("灌装啤酒-哈尔滨")) {
                    object.put("q", "啤酒");
                } else {
                    object.put("q", chineseIdeograph);
                }
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

    //修改保鲜期接口
    private void upDateSaveDay() {
        mHandler.sendEmptyMessage(0);
        OkHttpUtils.getInstance().postForJsonAsynchronization(ConfigUtils.UPDATESAVEDAY, requestInfoDay(), new OkHttpUtils.RequestCallBack<ImgRoot>() {
            @Override
            public void onError(Request request, Exception e) {
                mHandler.sendEmptyMessage(1);
                T.showshort(context, request.toString());

            }

            @Override
            public void onResponse(ImgRoot response) {
                if (response != null) {
                    mHandler.sendEmptyMessage(1);
                    if (response.getCode() == 1) {

                    }

                    T.showshort(context, response.getMsg());
                } else {
                    T.showshort(context, "网络繁忙，请稍后重试！");
                }

            }
        });
    }

    private String requestInfoDay() {

        JSONObject object = new JSONObject();
        try {
            object.put("recognitioninfo.name", names);
            object.put("recognitioninfo.saveDays", tv_surplus.getText().toString());

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
            case R.id.tv_changes_ok://修改保鲜期
                if (isOk) {
                    isOk = false;
                    tv_changes_ok.setText("确认");
             T.showshort(context,"请修改剩余保鲜期");

                } else {
                    isOk = true;
                    tv_changes_ok.setText("修改");
                    if (Integer.valueOf(tv_surplus.getText().toString()) <= (expDay + saveDays)) {
                        upDateSaveDay();
                    } else {
                        T.showshort(context, "剩余保鲜期不能大于保鲜期");
                    }
                }

                break;
            case R.id.tv_surplus://是否显示修改按钮
//                if (isOk) {
//                    tv_changes_ok.setVisibility(View.VISIBLE);
//                    isOk = false;
//                } else {
//                    tv_changes_ok.setVisibility(View.GONE);
//                    isOk = true;
//                }
                break;
            case R.id.tv_more://更多食谱推荐
                Intent intent1 = new Intent(context, CooBookSearch.class);
                if (chineseIdeograph.equals("灌装啤酒-哈尔滨")) {
                    intent1.putExtra("chineseIdeograph", "啤酒");
                } else {
                    intent1.putExtra("chineseIdeograph", chineseIdeograph);
                }
                startActivity(intent1);
                break;
        }
    }

    public Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            heat = APPUtils.keepTwoDecimalPlaces(heat);
            fat = APPUtils.keepTwoDecimalPlaces(fat);
            sugar = APPUtils.keepTwoDecimalPlaces(sugar);
            protein = APPUtils.keepTwoDecimalPlaces(protein);
            switch (msg.what) {
                case 3:
                    btn_heat.setText(heat + "");
                    btn_fat.setText(fat + "");
                    btn_sugar.setText(sugar + "");
                    btn_protein.setText(protein + "");
                    break;
                case 4:
                    btn_heat.setText(heat + "");
                    btn_fat.setText(fat + "");
                    btn_sugar.setText(sugar + "");
                    btn_protein.setText(protein + "");
                    break;
                case 5:
                    btn_heat.setText(heat + "");
                    btn_fat.setText(fat + "");
                    btn_sugar.setText(sugar + "");
                    btn_protein.setText(protein + "");
                    break;
                case 6:
                    btn_heat.setText(heat + "");
                    btn_fat.setText(fat + "");
                    btn_sugar.setText(sugar + "");
                    btn_protein.setText(protein + "");
                    break;
                case 7:
                    btn_heat.setText(heat + "");
                    btn_fat.setText(fat + "");
                    btn_sugar.setText(sugar + "");
                    btn_protein.setText(protein + "");
                    break;
                case 8:
                    btn_heat.setText(heat + "");
                    btn_fat.setText(fat + "");
                    btn_sugar.setText(sugar + "");
                    btn_protein.setText(protein + "");
                    break;
                case 9:
                    btn_heat.setText(heat + "");
                    btn_fat.setText(fat + "");
                    btn_sugar.setText(sugar + "");
                    btn_protein.setText(protein + "");
                    break;
                case 10:
                    btn_heat.setText(heat + "");
                    btn_fat.setText(fat + "");
                    btn_sugar.setText(sugar + "");
                    btn_protein.setText(protein + "");
                    break;
                case 11:
                    btn_heat.setText(heat + "");
                    btn_fat.setText(fat + "");
                    btn_sugar.setText(sugar + "");
                    btn_protein.setText(protein + "");
                    break;
                case 12:
                    btn_heat.setText(heat + "");
                    btn_fat.setText(fat + "");
                    btn_sugar.setText(sugar + "");
                    btn_protein.setText(protein + "");
                    break;
                case 13:
                    btn_heat.setText(heat + "");
                    btn_fat.setText(fat + "");
                    btn_sugar.setText(sugar + "");
                    btn_protein.setText(protein + "");
                    break;
                case 14:
                    btn_heat.setText(heat + "");
                    btn_fat.setText(fat + "");
                    btn_sugar.setText(sugar + "");
                    btn_protein.setText(protein + "");
                    break;
                case 15:
                    btn_heat.setText(heat + "");
                    btn_fat.setText(fat + "");
                    btn_sugar.setText(sugar + "");
                    btn_protein.setText(protein + "");
                    break;
                case 16:
                    btn_heat.setText(heat + "");
                    btn_fat.setText(fat + "");
                    btn_sugar.setText(sugar + "");
                    btn_protein.setText(protein + "");
                    break;
            }
            return false;
        }
    });

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() != 0) {
            if (!TextUtils.isEmpty(ed_weight.getText().toString())) {
                switch (names) {
                    case "li":
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
                        heat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 50;
                        fat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0.2;
                        sugar = (Double.valueOf(ed_weight.getText().toString())) / 100 * 10.2;
                        protein = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0.4;
                        myHandler.sendEmptyMessage(3);

//                            }
//                        }).start();

                        break;
                    case "pingguo":
                        heat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 54;
                        fat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0.2;
                        sugar = (Double.valueOf(ed_weight.getText().toString())) / 100 * 12.3;
                        protein = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0.2;
                        myHandler.sendEmptyMessage(4);
                        break;
                    case "qiezi":
                        heat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 23;
                        fat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0.2;
                        sugar = (Double.valueOf(ed_weight.getText().toString())) / 100 * 3.6;
                        protein = (Double.valueOf(ed_weight.getText().toString())) / 100 * 1.1;
                        myHandler.sendEmptyMessage(5);
                        break;
                    case "yumi":
                        heat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 112;
                        fat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 1.2;
                        sugar = (Double.valueOf(ed_weight.getText().toString())) / 100 * 19.9;
                        protein = (Double.valueOf(ed_weight.getText().toString())) / 100 * 4;
                        myHandler.sendEmptyMessage(6);
                        break;
                    case "xilanhua":
                        heat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 36;
                        fat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0.6;
                        sugar = (Double.valueOf(ed_weight.getText().toString())) / 100 * 2.7;
                        protein = (Double.valueOf(ed_weight.getText().toString())) / 100 * 4.1;
                        myHandler.sendEmptyMessage(7);
                        break;
                    case "doufu":
                        heat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 82;
                        fat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 3.7;
                        sugar = (Double.valueOf(ed_weight.getText().toString())) / 100 * 3.8;
                        protein = (Double.valueOf(ed_weight.getText().toString())) / 100 * 8.1;
                        myHandler.sendEmptyMessage(8);
                        break;
                    case "jidan":
                        heat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 144;
                        fat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 8.8;
                        sugar = (Double.valueOf(ed_weight.getText().toString())) / 100 * 2.8;
                        protein = (Double.valueOf(ed_weight.getText().toString())) / 100 * 13.3;
                        myHandler.sendEmptyMessage(9);
                        break;
                    case "xuebi":
                        heat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 191;
                        fat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0;
                        sugar = (Double.valueOf(ed_weight.getText().toString())) / 100 * 11;
                        protein = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0;
                        myHandler.sendEmptyMessage(10);
                        break;
                    case "haerbin":
                        heat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 32;
                        fat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0;
                        sugar = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0;
                        protein = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0.4;
                        myHandler.sendEmptyMessage(11);
                        break;
                    case "hongjiu":
                        heat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 74;
                        fat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0;
                        sugar = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0;
                        protein = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0.1;
                        myHandler.sendEmptyMessage(12);
                        break;
                    case "yiliniunai":
                        heat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 54;
                        fat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 3.2;
                        sugar = (Double.valueOf(ed_weight.getText().toString())) / 100 * 3.4;
                        protein = (Double.valueOf(ed_weight.getText().toString())) / 100 * 3;
                        myHandler.sendEmptyMessage(13);
                        break;
                    case "wawacai":
                        heat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 18;
                        fat = (Double.valueOf(ed_weight.getText().toString())) / 100 * 0.1;
                        sugar = (Double.valueOf(ed_weight.getText().toString())) / 100 * 2.4;
                        protein = (Double.valueOf(ed_weight.getText().toString())) / 100 * 1.5;
                        myHandler.sendEmptyMessage(14);
                        break;
                }
            }
        } else {
            T.showshort(context, "重量不能为空");
        }

    }
}
