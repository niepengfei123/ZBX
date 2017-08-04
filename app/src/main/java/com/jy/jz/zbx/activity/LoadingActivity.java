package com.jy.jz.zbx.activity;

//引入个推管理器

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.widget.Button;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.utils.L;
import com.jy.jz.zbx.utils.SPUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends BaseActivity implements View.OnClickListener, CallBackFunction, BridgeHandler {
    private static final String TAG = "LoadingActivity";
    private Context context;
    private TelephonyManager telManager;// 用于获取手机信息
    private BridgeWebView webView;
    private Button skip_btn;
    private Timer mTimer;
    private TimerTask timerTask;
    private int count = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全屏启动，也可以在布局文件中设置主题 android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//      this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loading);
        context = LoadingActivity.this;
        initWebView();
        initData();
    }

    /**
     * 初始化webview相关的东西
     */
    public void initWebView() {
        skip_btn = (Button) findViewById(R.id.skip_btn);
        skip_btn.setOnClickListener(this);
        webView = (BridgeWebView) findViewById(R.id.webView);
        webView.setDefaultHandler(new DefaultHandler());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("http://jindra.wicp.net/HeiNiuEducation/demo.html");
        registerHandler(new String[]{"submitFromWeb", "testFromWeb"});
    }

    /**
     * 初始化其他数据
     */
    public void initData() {
        mTimer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (count >= 1) {
                    count--;
                    handler.sendEmptyMessage(1);
                } else {
                    mTimer.cancel();
                    timerTask.cancel();
                    timerTask = null;
                    mTimer = null;
                    handler.sendEmptyMessage(0);
                }
            }
        };
        //开始一个定时任务
        if(mTimer!=null && timerTask!=null){
            mTimer.schedule(timerTask, 0, 1000);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                skip_btn.setText("跳 过 "+count + "s");
                L.e("跳过 "+count);
            } else if (msg.what == 0) {
                if(timerTask!=null){
                    timerTask.cancel();
                }
                if(mTimer!=null){
                    mTimer.cancel();
                }
                timerTask = null;
                mTimer = null;
                doGotoHomeView();
            } else {

            }

        }

        ;
    };

    /**
     * 跳转首页
     */
    private void doGotoHomeView() {
        boolean isfrist = (boolean) SPUtils.get(context,"firstFlag",true);
        if (isfrist) {//跳转引导页
            // TODO 第一次安装 绑定CID。将个推的clientid和 手机的唯一标识，调用服务端的接口，服务端要做关系绑定
            SPUtils.put(context,"firstFlag",false);
            // TODO 跳转至引导页
            Intent guideIntent = new Intent(context,GuideActivity.class);
            startActivity(guideIntent);
            finish();
        } else {//跳转登录页面
            // TODO 检查是否登录，已登录跳转到首页，未登录跳转到登录页
            String user_token = (String) SPUtils.get(context,"user_token","");
            if(user_token!=null && !"".equals(user_token)){
//                Intent intent = new Intent(context, MainActivity.class);
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent loginIntent = new Intent(context, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 400) {
            finish();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip_btn:
//                callHandler("functionTest", "data from Java");
                if(timerTask!=null){
                    timerTask.cancel();
                }
                if(mTimer!=null){
                    mTimer.cancel();
                }
                timerTask = null;
                mTimer = null;
                doGotoHomeView();
                break;
            default:
                break;
        }

    }

    //注册js回掉，用于web页面调用
    public void registerHandler(String[] registerNames) {
        for (int i = 0; i < registerNames.length; i++) {
            webView.registerHandler(registerNames[i], this);
        }
    }

    //调用web端的方法，把数据传给web端
    public void callHandler(String registerName, String data) {
        webView.callHandler(registerName, data, this);
    }

    //web端给的响应
    @Override
    public void onCallBack(String data) {
        String methodName = "";
        try {
            JSONObject jsonObject = new JSONObject(data);
            methodName = jsonObject.optString("methodName", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (methodName) {
            case "functionInJs":
//                skip_btn.setText("data from web = " + data);
                break;
            case "functionTest":
//                skip_btn.setText("data from web = " + data);
                break;
        }
    }

    //web传过来的数据接收
    @Override
    public void handler(String data, CallBackFunction function) {
        String methodName = "";
        try {
            JSONObject jsonObject = new JSONObject(data);
            methodName = jsonObject.optString("methodName", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (methodName) {
            case "submitFromWeb":
                skip_btn.setText("data from web = " + data);
                function.onCallBack("submitFromWeb exe, response data 中文 from Java");//给web端的响应
                break;
            case "testFromWeb":
                skip_btn.setText("data from web = " + data);
                function.onCallBack("testFromWeb exe, response data 中文 from Java");//给web端的响应
                break;
        }
    }
}
