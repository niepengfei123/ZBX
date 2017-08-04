package com.jy.jz.zbx.activity;

/**
 * author lw
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jy.jz.zbx.MainActivity;
import com.jy.jz.zbx.R;
import com.jy.jz.zbx.bean.User;
import com.jy.jz.zbx.utils.ConfigUtils;
import com.jy.jz.zbx.utils.L;
import com.jy.jz.zbx.utils.OkHttpUtils;
import com.jy.jz.zbx.utils.SPUtils;
import com.jy.jz.zbx.utils.T;
import com.jy.jz.zbx.utils.WaitingDialog;
import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements OnClickListener{
    private TelephonyManager telManager;// 用于获取手机信息
    private static final String TAG = "LoginActivity";
    private Context context;
    private WaitingDialog waitingDialog;

    //title
    private TextView tv_title;

    private SimpleDraweeView logo;
    private EditText login_username_edit, login_password_edit;
    private Button commit_login;
    private TextView go_regist, forget_password;
    private ImageView wechat_login,qq_login,sina_login;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        context = this;
        initView();
        waitingDialog = new WaitingDialog(context);
    }

    TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("登录");
        logo = (SimpleDraweeView) findViewById(R.id.logo);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(R.color.light_green, 1.0f);
        roundingParams.setRoundAsCircle(true);
        logo.getHierarchy().setRoundingParams(roundingParams);
        login_username_edit = (EditText) findViewById(R.id.login_username_edit);
        login_password_edit = (EditText) findViewById(R.id.login_password_edit);
        go_regist = (TextView) findViewById(R.id.go_regist);
        go_regist.setOnClickListener(this);
        forget_password = (TextView) findViewById(R.id.forget_password);
        forget_password.setOnClickListener(this);
        commit_login = (Button) findViewById(R.id.commit_login);
        commit_login.setOnClickListener(this);

        wechat_login = (ImageView) findViewById(R.id.wechat_login);
        qq_login = (ImageView) findViewById(R.id.qq_login);
        sina_login = (ImageView) findViewById(R.id.sina_login);
        wechat_login.setOnClickListener(this);
        qq_login.setOnClickListener(this);
        sina_login.setOnClickListener(this);
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

    public static int getLength(String s) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 2;
            } else {
                // 其他字符长度为0.5
                valueLength += 1;
            }
        }
        // 进位取整
        return valueLength;
    }

    /**
     * 验证登陆信息
     * @return
     */
    public boolean login_lg() {
        if (TextUtils.isEmpty(login_username_edit.getText().toString())
                || TextUtils.isEmpty(login_password_edit.getText().toString())) {
            T.showshort(LoginActivity.this, "手机号和密码不能为空");
            return false;
        }
        if (getLength(login_username_edit.getText().toString().trim()) < 3
                || getLength(login_username_edit.getText().toString().trim()) > 15) {
            T.showshort(LoginActivity.this, "手机号11位，昵称长度要求3至15个字符之间（1个汉字为2个字符）");
            return false;
        }
        return true;
    }

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit_login:
                if(login_lg()){
                    mHandler.sendEmptyMessage(0);
                    OkHttpUtils.getInstance().postForJsonAsynchronization(ConfigUtils.CLIENTLOGIN, requestMyInfo(), new OkHttpUtils.RequestCallBack<User>() {
                        @Override
                        public void onError(Request request, Exception e) {
                            mHandler.sendEmptyMessage(1);
                            T.showshort(context,request.toString());
                            L.e("错误信息："+request.toString()+e.toString());
                        }

                        @Override
                        public void onResponse(User response) {
                            if(response!=null){
                                mHandler.sendEmptyMessage(1);
                                if(response.getCode()!=null && response.getCode().equals("1")){
                                    //TODO 保存到本地
                                    SPUtils.put(context,"user_token",response.getUser_token());
                                    //TODO 跳转到主页
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                T.showshort(context,response.getMessage());
                            }else{
                                T.showshort(context,"网络繁忙，请稍后重试！");
                            }
                        }
                    });
                }
                break;
            case R.id.go_regist:
                Intent registIntent = new Intent(context,RegistActivity.class);
                startActivity(registIntent);
                finish();
                break;
            case R.id.forget_password:

                break;
            case R.id.wechat_login:

                break;
            case R.id.qq_login:

                break;
            case R.id.sina_login:

                break;
            default:
                break;
        }
    }

    public String requestMyInfo() {
        JSONObject object = new JSONObject();
        try {
            object.put("user.user_phone", login_username_edit.getText().toString());
            object.put("user.user_password",login_password_edit.getText().toString());
            object.put("user.client_imei", "a");
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
