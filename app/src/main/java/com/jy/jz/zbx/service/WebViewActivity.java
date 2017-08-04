package com.jy.jz.zbx.service;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.utils.FilePathUtil;
import com.jy.jz.zbx.utils.NetStates;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 装载H5页面
 */
public class WebViewActivity extends FragmentActivity implements View.OnClickListener, CallBackFunction, BridgeHandler {
    private static final String TAG = "LoadingActivity";
    private Context context;
    private BridgeWebView webView;
    private TextView tv_title, tv_left_title, tv_right_title;
    private String url = "";
    private int webName;
    private SystemBarTintManager mTintManager;

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全屏启动，也可以在布局文件中设置主题 android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //状态栏透明 需要在创建SystemBarTintManager 之前调用。
//            setTranslucentStatus(true);
//        }
//        mTintManager = new SystemBarTintManager(this);
//        mTintManager.setStatusBarTintEnabled(true);

        setContentView(R.layout.activity_webview);
        context = WebViewActivity.this;
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            webName = intent.getExtras().getInt("webName");
        }
        initWebView();
        initData();
    }

    /**
     * 初始化webview相关的东西
     */
    public void initWebView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_left_title = (TextView) findViewById(R.id.tv_left_title);
        tv_right_title = (TextView) findViewById(R.id.tv_right_title);

        webView = (BridgeWebView) findViewById(R.id.webView);
        webView.setDefaultHandler(new DefaultHandler());
        webView.setWebChromeClient(new WebChromeClient());
        DefaultConfig(webView, false, false);
        webView.loadUrl(url);
        registerHandler(new String[]{"share", "back"});
        switch (webName) {
            case 0:
                tv_title.setText("说明书");
                break;
            case 1:
                tv_title.setText("");
                break;
            case 2:
                tv_title.setText("");
                break;
            case 3:
                tv_title.setText("");
                break;
            case 4:
                tv_title.setText("");
                break;
            case 5:
                tv_title.setText("");
                break;
        }

    }

    /**
     * 初始化其他数据
     */
    public void initData() {
        tv_left_title.setOnClickListener(this);
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

            } else if (msg.what == 0) {

            } else {

            }
        }
    };


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
                break;
            case R.id.tv_left_title:
                finish();
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
            case "clientBack":
//                skip_btn.setText("data from web = " + data);
                finish();
                break;
            case "functionTest":
//                skip_btn.setText("data from web = " + data);
                break;
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            callHandler("clientBack", "");
//            return true;
//        } else {
//            return super.onKeyDown(keyCode, event);
//        }
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    //web端send过来的数据接收
    @Override
    public void handler(String data, CallBackFunction function) {
        String methodName = "";
        String shareUrl = "";
        try {
            JSONObject jsonObject = new JSONObject(data);
            methodName = jsonObject.optString("methodName", "");
            shareUrl = jsonObject.optString("url", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (methodName) {
            case "share":
//                APPUtils.share(context, "三人行", "绍汽出行", R.mipmap.sq_logo, shareUrl, new UMShareListener() {
//                    @Override
//                    public void onResult(SHARE_MEDIA share_media) {
//                        T.showshort(context, "分享成功");
//                    }
//
//                    @Override
//                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//                        L.e("失败" + share_media.name());
//                    }

//                    @Override
//                    public void onCancel(SHARE_MEDIA share_media) {
//                        L.e("中断");
//                    }
//                });
//                function.onCallBack("{'code':1,'msg':'成功'}");//给web端的响应
                break;
            case "back":
                finish();
                break;
        }
    }

    /**
     * webview 默认设置
     *
     * @param wv
     * @param webchromeclient
     * @param webviewclient
     */
    public void DefaultConfig(WebView wv, boolean webchromeclient, boolean webviewclient) {
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = context.getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);

        if (!NetStates.trueNet(context)) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }
        /**
         * 设置缓存路径
         */
        webSettings.setAppCachePath(FilePathUtil.getSDCardRoot() + FilePathUtil.APPROOT + "/data/cache");
        if (webchromeclient) {
            wv.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                    new AlertDialog.Builder(context).setMessage(message)
                            .setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            }).setCancelable(false).create().show();
                    return true;
                }

                @Override
                public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
                                          JsPromptResult result) {
                    return super.onJsPrompt(view, url, message, defaultValue, result);
                }

                @Override
                public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                    return super.onJsConfirm(view, url, message, result);
                }
            });
        }

        if (webviewclient) {
            wv.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    // 设置错误页面
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();
                }

                @Override
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                }
            });
        }
    }
}
