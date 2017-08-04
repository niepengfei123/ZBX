package com.jy.jz.zbx.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hiflying.smartlink.ISmartLinker;
import com.hiflying.smartlink.OnSmartLinkListener;
import com.hiflying.smartlink.SmartLinkedModule;
import com.hiflying.smartlink.v7.MulticastSmartLinker;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.bean.Product;
import com.jy.jz.zbx.dms.ConfigUdpBroadcast;
import com.jy.jz.zbx.dms.FindEquiAdapter;
import com.jy.jz.zbx.dms.ModuleInfo;
import com.jy.jz.zbx.dms.MyListView;
import com.jy.jz.zbx.service.WebViewActivity;
import com.jy.jz.zbx.utils.SPUtils;
import com.jy.jz.zbx.utils.T;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备配网，发现
 */
public class BindingEquipmentActivity extends BaseActivity implements OnSmartLinkListener,
        OnClickListener, OnKeyListener {

    private static final String TAG = "Bind";
    public Context ctx;
    // view
//    public TitleBar green_title;
    public TextView green_title;
    public ScrollView equi_add_scrollView;
    public ImageView step_line, first_step_img, third_step_img1, third_step_img2, wifi_pwd_status;
    public LinearLayout ll_first_step, ll_second_step, ll_third_step, ll_third_step_config, ll_third_step_find,
            ll_fourth_step, ll_five_step, ll_bottom_btn;
    protected EditText wifi_name_edit, wifi_pwd_edit, fourth_text_devname;
    protected TextView back, next, first_step_text1, first_step_text2, second_step_text1, third_step_bottom_text2,
            finish, try_aging, customer_service;
    protected TextView first_step_text3, third_step_find_title, third_step_bottom_text1, fourth_step_text1;
    protected MyListView find_equi_lv;
    protected ProgressBar config_progress;
    protected View v_third_step_find_line;
    private TextView tv_ai_result;
    private TextView img_user_back;
    // protected Button mStartButton;

    // 设备配网
    protected ISmartLinker mSnifferSmartLinker;
    private boolean mIsConncting = false;
    protected Handler mViewHandler = new Handler();
    private BroadcastReceiver mWifiChangedReceiver;

    // 设备发现
    private ConfigUdpBroadcast mConfigBroadUdp;
    boolean isconncting = false;
    // 设备listview
    public FindEquiAdapter findEquiAdapter;
    public List<ModuleInfo> usablelist = new ArrayList<ModuleInfo>();
    private List<ModuleInfo> addList = new ArrayList<ModuleInfo>();

    private int stepNum = 0;// 处于什么阶段
    private boolean isSkipSecondStep = false;// 是否跳过第二步
    private String type, from, equipmentName;
    private String selectType;//当前选中的类型
    private int pwdFlag = 1;// 密码可见性 0不可见，1可见
    private Handler startHandler;
    private ModuleInfo selectEquipmen;
    public static boolean ISADD = false;
    public boolean isCanAdd = true;
    private AnimationDrawable animationDrawable;
    private Product product;
    private String productName;
    private String productCode;
    private String wifiCore;
    private String url;//说明书地址
    private String imgUrl;//

    public Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                equi_add_scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                findEquiAdapter.notifyDataSetChanged();
                equi_add_scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                third_step_find_title.setVisibility(View.VISIBLE);
                third_step_find_title.setText("设备连接成功，已发现以下设备");
                v_third_step_find_line.setVisibility(View.VISIBLE);
            } else if (msg.what == 1) {
                int p = Math.round((float) (progress / 30.0) * 100);
                third_step_bottom_text2.setText("" + p + "%");
                config_progress.setProgress(progress);
            } else if (msg.what == 2) {
//                green_title.hideLeftBtn();
                ll_first_step.setVisibility(View.GONE);
                ll_second_step.setVisibility(View.GONE);
                ll_third_step.setVisibility(View.GONE);
                ll_fourth_step.setVisibility(View.VISIBLE);
                ll_five_step.setVisibility(View.GONE);
                ll_bottom_btn.setVisibility(View.GONE);
                step_line.setImageResource(R.drawable.jieduan4);
                // fourth_text_devname.setText(selectEquipmen.getName());
                fourth_step_text1.setText("恭喜您，" + selectEquipmen.getName() + "设备添加成功");
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.v2_bind_equipment);
        ctx = this;
        Intent intent = getIntent();
        if (null != intent) {
            product = (Product) intent.getExtras().getSerializable("product");
            imgUrl = intent.getExtras().getString("imgUrl");
            Bundle bundle = intent.getBundleExtra("data");
            if (null != bundle) {
                type = bundle.getString("type");
                from = bundle.getString("from");
            }
        }
        isCanAdd = true;
        initView();
        initData();
        initWifiChangedReceiver();
    }

    public void initView() {
        mSnifferSmartLinker = MulticastSmartLinker.getInstance();
        green_title = (TextView) findViewById(R.id.green_title);
        img_user_back = (TextView) findViewById(R.id.img_back_take);
        img_user_back.setOnClickListener(this);
        tv_ai_result = (TextView) findViewById(R.id.tv_ai_result);
//        green_title.setTitleIsedit(false);
        green_title.setText("设备绑定");
//        green_title.setLeftBtn(R.drawable.redorange_icon_back, new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                goBack();
//            }
//        });
//        green_title.hidieRightBtn();
        equi_add_scrollView = (ScrollView) findViewById(R.id.equi_add_scrollView);
        step_line = (ImageView) findViewById(R.id.step_line);
        first_step_img = (ImageView) findViewById(R.id.first_step_img);
        third_step_img1 = (ImageView) findViewById(R.id.third_step_img1);
        third_step_img2 = (ImageView) findViewById(R.id.third_step_img2);
        wifi_pwd_status = (ImageView) findViewById(R.id.wifi_pwd_status);
        wifi_pwd_status.setOnClickListener(this);
        ll_first_step = (LinearLayout) findViewById(R.id.ll_first_step);
        ll_second_step = (LinearLayout) findViewById(R.id.ll_second_step);
        ll_third_step = (LinearLayout) findViewById(R.id.ll_third_step);
        ll_third_step_config = (LinearLayout) findViewById(R.id.ll_third_step_config);
        ll_third_step_find = (LinearLayout) findViewById(R.id.ll_third_step_find);
        ll_fourth_step = (LinearLayout) findViewById(R.id.ll_fourth_step);
        ll_five_step = (LinearLayout) findViewById(R.id.ll_five_step);
        ll_bottom_btn = (LinearLayout) findViewById(R.id.ll_bottom_btn);
        wifi_name_edit = (EditText) findViewById(R.id.wifi_name_edit);
        wifi_pwd_edit = (EditText) findViewById(R.id.wifi_pwd_edit);
        fourth_text_devname = (EditText) findViewById(R.id.fourth_text_devname);
        second_step_text1 = (TextView) findViewById(R.id.second_step_text1);
        first_step_text1 = (TextView) findViewById(R.id.first_step_text1);
        first_step_text2 = (TextView) findViewById(R.id.first_step_text2);
        first_step_text2.setOnClickListener(this);
        first_step_text3 = (TextView) findViewById(R.id.first_step_text3);
        third_step_bottom_text1 = (TextView) findViewById(R.id.third_step_bottom_text1);
        third_step_find_title = (TextView) findViewById(R.id.third_step_find_title);
        fourth_step_text1 = (TextView) findViewById(R.id.fourth_step_text1);
        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(this);
        finish = (TextView) findViewById(R.id.finish);
        finish.setOnClickListener(this);
        try_aging = (TextView) findViewById(R.id.try_aging);
        try_aging.setOnClickListener(this);
        customer_service = (TextView) findViewById(R.id.customer_service);
        customer_service.setOnClickListener(this);

        wifi_name_edit.setText(getWifiName(ctx));
        wifi_pwd_edit.setText((String) (SPUtils
                .get(getApplicationContext(), wifi_name_edit.getText().toString(), new String())));
        v_third_step_find_line = findViewById(R.id.v_third_step_find_line);
        find_equi_lv = (MyListView) findViewById(R.id.find_equi_lv);
        stepNum = 1;
        findEquiAdapter = new FindEquiAdapter(ctx, usablelist);
        find_equi_lv.setAdapter(findEquiAdapter);
        find_equi_lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                stopConnection();
                stopConfiger();
                selectEquipmen = usablelist.get(arg2);
                if (isCanAdd) {
                    isCanAdd = false;
                    goNextStep();
                }
            }
        });
        third_step_bottom_text2 = (TextView) findViewById(R.id.third_step_bottom_text2);
        config_progress = (ProgressBar) findViewById(R.id.config_progress);
    }

    private void goBack() {
        if (stepNum == 1) {
            stepOne();
        } else if (stepNum == 2) {
            goPreviousStep();
        } else if (stepNum == 3) {
            if (isSkipSecondStep) {
                stepNum = 2;
            }
            stopConnection();
            stopConfiger();
            goPreviousStep();
            isSkipSecondStep = false;
        } else if (stepNum == 5) {
            stepNum = 3;
            wifi_pwd_edit.setText("");
            goPreviousStep();
        } else {
            goPreviousStep();
        }
    }

    /**
     * 第一步，返回
     */
    private void stepOne() {
        finish();
    }

    public void initData() {
        if (product.getProductName() != null) {
            productName = product.getProductName();
        }
        if (product.getProductCode() != null) {
            productCode = product.getProductCode();
        }
        if (product.getWifiCore() != null) {
            wifiCore = product.getWifiCore();
        }
        if (product.getUrl() != null) {
            url = product.getUrl();
        }
        if (productName != null && productCode != null) {
            tv_ai_result.setText(productName + "");
        }
        img_user_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        SpannableString styledText = new SpannableString("如果Wi-Fi指示灯不亮，请长按Wi-Fi按键2秒以上，使指示灯快闪");
        styledText
                .setSpan(new ForegroundColorSpan(0xffff5828), 16, 25, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        first_step_text1.setText(styledText, TextView.BufferType.SPANNABLE);

        String first_step_text2_value = first_step_text2.getText().toString();
        SpannableString styledText1 = new SpannableString(first_step_text2_value);
        styledText1.setSpan(new ForegroundColorSpan(0xffff5828), first_step_text2_value.length() - 4,
                first_step_text2_value.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        styledText1.setSpan(new UnderlineSpan(), first_step_text2_value.length() - 4,
                first_step_text2_value.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        first_step_text2.setText(styledText1, TextView.BufferType.SPANNABLE);

        String second_step_text1_value = second_step_text1.getText().toString();
        SpannableString styledText2 = new SpannableString(second_step_text1_value);
        styledText2
                .setSpan(new ForegroundColorSpan(0xffff5828), 17, 21, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        second_step_text1.setText(styledText2, TextView.BufferType.SPANNABLE);
        initFirstStepImgAndThirdStepImg();
    }

    public void initFirstStepImgAndThirdStepImg() {
        if (null != type) {// 二维码以及型号配置
            first_step_img.setImageResource(R.drawable.pic_link);
            third_step_img1.setImageResource(R.drawable.v2_contentview_icon_heat);
            first_step_text3.setText("配网过程中请把设备尽可能靠近路由器\n（示意图仅供参考）");
        }
    }


    /**
     * 初始化监听网络变化
     */
    public void initWifiChangedReceiver() {
        mWifiChangedReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
                        Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (networkInfo != null && networkInfo.isConnected()) {
                    // 获得wifi ssid
                    wifi_name_edit.setText(getWifiName(ctx));
                    //根据ssid获得密码
                    wifi_pwd_edit.setText((String) (SPUtils
                            .get(getApplicationContext(), wifi_name_edit.getText().toString(), new String())));
                    wifi_pwd_edit.requestFocus();
                } else {
                    wifi_name_edit.setText("没有连接WIFI");
                    wifi_name_edit.requestFocus();
                    stopConfiger();
                }
            }
        };
        registerReceiver(mWifiChangedReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * 初始化UDP广播，发现设备
     */
    public void initConfigBroadUdp() {
        if (null == mConfigBroadUdp) {
            mConfigBroadUdp = new ConfigUdpBroadcast(getBroadcastAddress(ctx), callback, true);
        }

        if (isconncting) {
            isconncting = false;
            mConfigBroadUdp.stopReceive();
        }
        isconncting = true;
        mConfigBroadUdp.open();
        mConfigBroadUdp.receive();

        new Thread(findMoudleThread).start();
    }

    /**
     * 获取广播的地址
     */
    public String getBroadcastAddress(Context ctx) {
        WifiManager cm = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo myDhcpInfo = cm.getDhcpInfo();
        if (myDhcpInfo == null) {
            return "255.255.255.255";
        }
        // | ~myDhcpInfo.netmask;
        int broadcast = (myDhcpInfo.ipAddress & myDhcpInfo.netmask) | ~myDhcpInfo.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++) {
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        }
        try {
            return InetAddress.getByAddress(quads).getHostAddress();
        } catch (Exception e) {
            return "255.255.255.255";
        }
    }

    @Override
    protected void onStart() {
        HandlerThread startThread = new HandlerThread("findDevthread");
        startThread.start();// 创建一个HandlerThread并启动它
        startHandler = new Handler(startThread.getLooper());
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopConnection();
        stopConfiger();
        try {
            unregisterReceiver(mWifiChangedReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLinked(final SmartLinkedModule module) {
        mViewHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "配置成功", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCompleted() {

        mViewHandler.post(new Runnable() {

            @Override
            public void run() {
                third_step_bottom_text1.setVisibility(View.GONE);
                third_step_bottom_text2.setVisibility(View.GONE);
                config_progress.setVisibility(View.GONE);
                mIsConncting = false;
                stopConfiger();
                startHandler.removeCallbacks(configEquipmentProgress);
            }
        });
    }

    @Override
    public void onTimeOut() {
        mViewHandler.post(new Runnable() {
            @Override
            public void run() {
                mIsConncting = false;
                stopConfiger();
                stopConnection();
                if (!isSkipSecondStep && usablelist.size() <= 0) {
                    Toast.makeText(getApplicationContext(), "配置超时", Toast.LENGTH_LONG).show();

                    stepNum = 4;
                    goNextStep();
                } else {
                    third_step_bottom_text1.setVisibility(View.GONE);
                    third_step_bottom_text2.setVisibility(View.GONE);
                    config_progress.setVisibility(View.GONE);
                }
                startHandler.removeCallbacks(configEquipmentProgress);
            }
        });
    }

    Runnable findMoudleThread = new Runnable() {
        @Override
        public void run() {
            while (isconncting) {
                if (mConfigBroadUdp != null) {
                    mConfigBroadUdp.sendFindCmd();
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void stopConnection() {
        isconncting = false;
        if (mConfigBroadUdp != null) {
            mConfigBroadUdp.stopReceive();
            mConfigBroadUdp.close();
            mConfigBroadUdp = null;
        }
    }

    public void stopConfiger() {
        progress = 30;
        mIsConncting = false;
        if (mSnifferSmartLinker != null) {
            mSnifferSmartLinker.setOnSmartLinkListener(null);
            mSnifferSmartLinker.stop();
        }
        startHandler.removeCallbacks(configEquipmentProgress);
    }

    Map<String, ModuleInfo> devMap = new HashMap<String, ModuleInfo>(10);
    ConfigUdpBroadcast.ConnectCallBack callback = new ConfigUdpBroadcast.ConnectCallBack() {

        @Override
        public void onConnectTimeOut() {
        }

        @Override
        public void onConnectOk() {
        }

        @Override
        public void onConnect(final ModuleInfo mi) {
            Log.e("发现设备  ",
                    "发现设备  " + mi.getProductType() + "mac" + mi.getMac() + "IP=" + mi.getIp() + "SN=" + mi
                            .getSn());
            if (devMap.containsKey(mi.getSn())) {
                if (devMap.get(mi.getSn()).getIp().equals(mi.getIp())) {
                    return;
                }
            }

            devMap.put(mi.getSn(), mi);
            myHandler.post(new Runnable() {

                @Override
                public void run() {
                    /*** 设备数据返回 解析 保存 显示 */
                    String uid = "0";

                    String sn = mi.getSn().toUpperCase();
                    for (ModuleInfo m : usablelist) {
                        if (mi.getSn().equalsIgnoreCase(m.getSn())) {
                            return;
                        }
                    }

                    String type = mi.getProductType() + "";
                    mi.setProductType(type);
                    //TODO 由前面传入机型名称
                    mi.setName("测试机型");
                    mi.setSn(sn);
                    mi.setUid(uid);

                    if (selectType != null && selectType.equals(type)) {
                        usablelist.add(mi);
                        Message msg = new Message();
                        msg.what = 0;
                        myHandler.sendMessage(msg);
                    }


                }
            });
        }

        @Override
        public void findFilish() {
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.img_back_take:
//
//                break;
//            case R.id.back:

//                break;
            case R.id.back://查看说明书
                if (url != null) {
                    Intent webH5 = new Intent(ctx, WebViewActivity.class);
                    webH5.putExtra("url", url);
                    webH5.putExtra("webName", 0);
                    startActivity(webH5);
                } else {
                    T.showshort(ctx, "暂无说明书");
                }
                break;
            case R.id.next:
                goNextStep();
                break;
            case R.id.first_step_text2:
                isSkipSecondStep = true;
                stepNum = 2;
                goNextStep();
                break;
            case R.id.wifi_pwd_status:
                if (pwdFlag == 0) {
                    wifi_pwd_status.setImageResource(R.drawable.v2_contentview_icon_eyes_close);
                    wifi_pwd_edit
                            .setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pwdFlag = 1;
                } else {
                    wifi_pwd_status.setImageResource(R.drawable.v2_contentview_icon_eyes_open);
                    wifi_pwd_edit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pwdFlag = 0;
                }
                Editable edit = wifi_pwd_edit.getText();
                Selection.setSelection(edit, edit.length());// 光标显示在最后
                break;
            case R.id.finish:
                equipmentName = fourth_text_devname.getText().toString().trim();
                if (equipmentName.length() > 0) {
                    selectEquipmen.setName(equipmentName);
                } else {
                    if (from != null && "CheckOutUserActivity".equals(from)) {
                        Intent intent = new Intent();
                        intent.putExtra("result", "ok");
                        setResult(22, intent);
                        finish();
//                        Intent intent1 = new Intent(ctx, V1IndexActivity.class);
//                        startActivity(intent1);
                    } else if (from != null && "cookBookDetail".equals(from)) {
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("result", "ok");
                        setResult(22, intent);
                    }
                    finish();
                }

                break;
            case R.id.try_aging:
                stepNum = 3;
                wifi_pwd_edit.setText("");
                goPreviousStep();
                break;
            case R.id.customer_service:
                Toast.makeText(getApplication(), "联系客服 400 6186 999", Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
    }

    /**
     * 开始给设备配网
     */
    public void beginConfig() {
        if (!mIsConncting) {
            // 设置要配置的ssid 和pswd
            try {
                mSnifferSmartLinker.setOnSmartLinkListener(BindingEquipmentActivity.this);
                // 开始 smartLink
                mSnifferSmartLinker
                        .start(getApplicationContext(), wifi_pwd_edit.getText().toString().trim(),
                                wifi_name_edit.getText().toString().trim());
                mSnifferSmartLinker.setTimeoutPeriod(30000);// 设备超时时间，默认30秒
                progress = 0;
                startHandler.post(configEquipmentProgress);
                mIsConncting = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
    }

    /**
     * 上一步
     */
    public void goPreviousStep() {
        if (stepNum == 1) {
            stepOne();
        } else if (stepNum == 2) {
            stepNum = 1;
//            green_title.showLeftBtn();
            ll_first_step.setVisibility(View.VISIBLE);
            ll_second_step.setVisibility(View.GONE);
            ll_third_step.setVisibility(View.GONE);
            ll_fourth_step.setVisibility(View.GONE);
            ll_five_step.setVisibility(View.GONE);
            ll_bottom_btn.setVisibility(View.VISIBLE);
            step_line.setVisibility(View.VISIBLE);
            step_line.setImageResource(R.drawable.jieduan1);
            back.setText("说明书");
            next.setText("下一步");
        } else if (stepNum == 3) {
//            green_title.showLeftBtn();
            stepNum = 2;
            ll_first_step.setVisibility(View.GONE);
            ll_second_step.setVisibility(View.VISIBLE);
            ll_third_step.setVisibility(View.GONE);
            ll_fourth_step.setVisibility(View.GONE);
            ll_five_step.setVisibility(View.GONE);
            ll_bottom_btn.setVisibility(View.VISIBLE);
            step_line.setVisibility(View.VISIBLE);
            step_line.setImageResource(R.drawable.jieduan2);
            back.setText("说明书");
            next.setText("连接");
            stopConnection();
            stopConfiger();
            usablelist.clear();
            third_step_find_title.setText("还未发现设备");
            devMap.clear();
            third_step_bottom_text1.setVisibility(View.VISIBLE);
            third_step_bottom_text2.setVisibility(View.VISIBLE);
            config_progress.setVisibility(View.VISIBLE);
        } else if (stepNum == 4) {
            stepNum = 3;
//            green_title.showLeftBtn();
            ll_first_step.setVisibility(View.GONE);
            ll_second_step.setVisibility(View.GONE);
            ll_third_step.setVisibility(View.VISIBLE);
            ll_fourth_step.setVisibility(View.GONE);
            ll_five_step.setVisibility(View.GONE);
            ll_bottom_btn.setVisibility(View.GONE);
            step_line.setImageResource(R.drawable.jieduan3);
            initConfigBroadUdp();
        }
    }

    /**
     * 下一步
     */
    public void goNextStep() {
        if (stepNum == 1) {
            stepNum = 2;
//            green_title.showLeftBtn();
            ll_first_step.setVisibility(View.GONE);
            ll_second_step.setVisibility(View.VISIBLE);
            ll_third_step.setVisibility(View.GONE);
            ll_fourth_step.setVisibility(View.GONE);
            ll_five_step.setVisibility(View.GONE);
            ll_bottom_btn.setVisibility(View.VISIBLE);
            step_line.setVisibility(View.VISIBLE);
            step_line.setImageResource(R.drawable.jieduan2);
            back.setText("说明书");
            next.setText("连接");
            isSkipSecondStep = false;
        } else if (stepNum == 2) {
            if (!isSkipSecondStep) {// 不跳过第二步就执行配网
                if (wifi_pwd_edit.getText().toString().trim().equals("")
                        || wifi_name_edit.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "为了确保您的设备安全，请使用有密码的WIFI，谢谢！", Toast.LENGTH_LONG).show();

                    return;
                }
                //保存密码(不做任何判断,直接保存)
                SPUtils.put(getApplicationContext(), wifi_name_edit.getText().toString(),
                        wifi_pwd_edit.getText().toString());
                ll_third_step_config.setVisibility(View.VISIBLE);
                ll_third_step_find.setVisibility(View.VISIBLE);
                third_step_img2.setImageResource(R.drawable.config_equipment_anim);
                animationDrawable = (AnimationDrawable) third_step_img2.getDrawable();
                animationDrawable.start();
                progress = 0;
                beginConfig();
                initConfigBroadUdp();
            } else {
                initConfigBroadUdp();
                ll_third_step_config.setVisibility(View.GONE);
                ll_third_step_find.setVisibility(View.VISIBLE);
                third_step_img2.setImageResource(R.drawable.v2_contentview_icon_connect4);
                third_step_img2.setImageResource(R.drawable.config_equipment_anim);
                animationDrawable = (AnimationDrawable) third_step_img2.getDrawable();
                animationDrawable.start();
            }
            stepNum = 3;
//            green_title.showLeftBtn();
            ll_first_step.setVisibility(View.GONE);
            ll_second_step.setVisibility(View.GONE);
            ll_third_step.setVisibility(View.VISIBLE);
            ll_fourth_step.setVisibility(View.GONE);
            ll_five_step.setVisibility(View.GONE);
            ll_bottom_btn.setVisibility(View.GONE);
            step_line.setImageResource(R.drawable.jieduan3);
        } else if (stepNum == 3) {
            if (selectEquipmen != null) {
                selectEquipmen.setUid("0");
            }
            selectEquipmen.setSn(selectEquipmen.getSn().toUpperCase());
            addList.add(selectEquipmen);

        } else if (stepNum == 4) {
//            green_title.showLeftBtn();
            stepNum = 5;
            ll_first_step.setVisibility(View.GONE);
            ll_second_step.setVisibility(View.GONE);
            ll_third_step.setVisibility(View.GONE);
            ll_fourth_step.setVisibility(View.GONE);
            ll_five_step.setVisibility(View.VISIBLE);
            ll_bottom_btn.setVisibility(View.GONE);
            step_line.setVisibility(View.GONE);
        }
    }

    int progress = 0;
    Runnable configEquipmentProgress = new Runnable() {
        @Override
        public void run() {
            while (progress < 30) {
                progress++;
                myHandler.sendEmptyMessage(1);
                Log.e(TAG, "当前进度：" + progress);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }


    public String getWifiName(Context context) {
        WifiManager mWifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifi = mWifi.getConnectionInfo();
        String name = wifi.getSSID();
        if (name != null) {
            name = name.replace("\"", "");
        }
        return "<unknown ssid>".equals(name) ? "" : name;
    }

}
