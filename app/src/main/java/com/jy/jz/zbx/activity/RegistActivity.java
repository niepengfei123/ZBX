package com.jy.jz.zbx.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import java.util.Timer;
import java.util.TimerTask;

public class RegistActivity extends BaseActivity implements View.OnClickListener {
	private static final String TAG = "RegistActivity";
	private Context context;
	private WaitingDialog waitingDialog;
	//title
	private TextView tv_title;

	private EditText regist_phone_edit, captcha_edit;
	private EditText regist_password_edit, confirm_password_edit;
	private Button commit_regist;
	private TextView regist_captcha_tv;

	private Timer mTimer;
	private TimerTask timerTask;
	private int count = 60;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		context = this;
		initView();
		waitingDialog = new WaitingDialog(context);
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
	public void initView(){
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("注册");

		regist_phone_edit = (EditText) findViewById(R.id.regist_phone_edit);
		captcha_edit = (EditText) findViewById(R.id.captcha_edit);
		regist_password_edit = (EditText) findViewById(R.id.regist_password_edit);
		confirm_password_edit = (EditText) findViewById(R.id.confirm_password_edit);
		regist_captcha_tv = (TextView) findViewById(R.id.regist_captcha_tv);
		regist_captcha_tv.setOnClickListener(this);
		commit_regist = (Button) findViewById(R.id.commit_regist);
		commit_regist.setOnClickListener(this);

	}
	/**
	 * 初始化定时器
	 */
	public void initTimerData() {
		count = 60;
		mTimer = new Timer();
		timerTask = new TimerTask() {
			@Override
			public void run() {
				if (count >= 1) {
					count--;
					mHandler.sendEmptyMessage(2);
				} else {
					mTimer.cancel();
					timerTask.cancel();
					timerTask = null;
					mTimer = null;
					mHandler.sendEmptyMessage(3);
				}
			}
		};
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.regist_captcha_tv:
				if(verificationGetRegistCode()){
					mHandler.sendEmptyMessage(0);
					OkHttpUtils.getInstance().postForJsonAsynchronization(ConfigUtils.GETREGISTCODE, requestCaptcha(), new OkHttpUtils.RequestCallBack<User>() {
						@Override
						public void onError(Request request, Exception e) {
							mHandler.sendEmptyMessage(1);
							T.showshort(context,request.toString());
							L.e("错误信息："+request.toString()+e.toString());
						}

						@Override
						public void onResponse(User response) {
							mHandler.sendEmptyMessage(1);
							if(response!=null){
								if(response.getCode()!=null && response.getCode().equals("1")){
									//开始一个定时任务
									initTimerData();
									if(mTimer!=null && timerTask!=null){
										mTimer.schedule(timerTask, 0, 1000);
									}
								}
								T.showshort(context,response.getMessage());
							}else{
								T.showshort(context,"网络繁忙，请稍后重试！");
							}
						}
					});
				}
				break;
			case R.id.commit_regist:
				if(verificationRegistInfo()){
					mHandler.sendEmptyMessage(0);
					OkHttpUtils.getInstance().postForJsonAsynchronization(ConfigUtils.REGISTER, requestRegist(), new OkHttpUtils.RequestCallBack<User>() {
						@Override
						public void onError(Request request, Exception e) {
							mHandler.sendEmptyMessage(1);
							T.showshort(context,request.toString());
							L.e("错误信息："+request.toString()+e.toString());
						}

						@Override
						public void onResponse(User response) {
							mHandler.sendEmptyMessage(1);
							if(response!=null){
								if(response.getCode()!=null && response.getCode().equals("1")){
									//TODO 保存到本地
									SPUtils.put(context,"user_token",response.getUser_token());
									//TODO 跳转到主页
									Intent intent = new Intent(context, MainActivity.class);
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
			default:
				break;
		}
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				waitingDialog.showWaitingDialog();
			} else if (msg.what == 1) {
				waitingDialog.dismissWaitingDialog();
			}else if (msg.what == 2) {
				regist_captcha_tv.setText(count + "s后重试");
			}else if (msg.what == 3) {
				if(timerTask!=null){
					timerTask.cancel();
				}
				if(mTimer!=null){
					mTimer.cancel();
				}
				timerTask = null;
				mTimer = null;
				regist_captcha_tv.setText("验 证 码");
			}

		}
	};

	/**
	 * 验证注册相关信息的正确性
	 * @return
     */
	public boolean verificationRegistInfo() {
		if (TextUtils.isEmpty(regist_phone_edit.getText().toString())
				|| TextUtils.isEmpty(captcha_edit.getText().toString())
				|| TextUtils.isEmpty(regist_password_edit.getText().toString())) {
			T.showshort(context, "输入有误，请重试");
			return false;
		}
		if(!regist_password_edit.getText().toString().equals(confirm_password_edit.getText().toString())){
			T.showshort(context, "两次输入的密码不一致");
			return false;
		}
		return true;
	}

	/**
	 * 组装获注册的信息
	 * @return
	 */
	public String requestRegist() {
		TelephonyManager mTm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		String imei = mTm.getDeviceId();
		JSONObject object = new JSONObject();
		try {
			object.put("user.user_phone", regist_phone_edit.getText().toString());
			object.put("user.user_password", regist_password_edit.getText().toString());
			object.put("user.captcha_code", captcha_edit.getText().toString());
			object.put("user.client_type", 1);
			object.put("user.client_imei", imei);
			return object.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 验证获取验证码信息正确性
	 * @return
	 */
	public boolean verificationGetRegistCode() {
		if (TextUtils.isEmpty(regist_phone_edit.getText().toString())) {
			T.showshort(context, "手机号不能为空");
			return false;
		}
		return true;
	}
	/**
	 * 组装获取验证码的信息
	 * @return
     */
	public String requestCaptcha() {
		JSONObject object = new JSONObject();
		try {
			object.put("user.user_phone", regist_phone_edit.getText().toString());
			return object.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
