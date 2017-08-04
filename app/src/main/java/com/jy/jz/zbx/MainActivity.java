package com.jy.jz.zbx;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.jz.zbx.activity.BaseActivity;
import com.jy.jz.zbx.fragment.FifthFragment;
import com.jy.jz.zbx.fragment.FirstFragment;
import com.jy.jz.zbx.fragment.FourthFragment;
import com.jy.jz.zbx.fragment.SecondFragment;
import com.jy.jz.zbx.fragment.ThirdFragment;
import com.jy.jz.zbx.utils.APPUtils;
import com.jy.jz.zbx.utils.DialogUtil;
import com.umeng.analytics.MobclickAgent;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = "MainActivity";
    private static final int youmengRequestPermissionsCode = 100;
    private Context context;
    private Fragment[] tabbarFragments;
    private FirstFragment firstFragment;//
    private SecondFragment secondFragment;//
    private FourthFragment fourthFragment;//
    private ThirdFragment thirdFragment;//
    private FifthFragment fifthFragment;//
    private ImageView[] tabbarImageView;
    private TextView[] tabbarTextView;

    private FrameLayout frame_content;//主内容容器
    private RelativeLayout layout_public_pay, layout_discussion, layout_teacher_live_radio, layout_open_courses, layout_market;
    private FrameLayout frameMenu_center;//中间按钮容器
    private ImageView plus_btn;
    private int index = 0;//点击的tab，将要选中
    private int currentTabIndex = 0;//当前选中的tab

    //顶部title
    protected TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initView();
    }

    /**
     * 初始化view控件
     */
    public void initView() {
        //初始化fragment
        firstFragment = new FirstFragment();
        thirdFragment = new ThirdFragment();
        secondFragment = new SecondFragment();
        fifthFragment = new FifthFragment();
        fourthFragment = new FourthFragment();
        tabbarFragments = new Fragment[]{firstFragment, secondFragment, fourthFragment, thirdFragment, fifthFragment, secondFragment};
        //初始化底部tab
        tabbarImageView = new ImageView[5];
        tabbarImageView[0] = (ImageView) findViewById(R.id.image_public_pay);
        tabbarImageView[1] = (ImageView) findViewById(R.id.image_discussion);
        tabbarImageView[2] = (ImageView) findViewById(R.id.image_teacher_live_radio);
        tabbarImageView[3] = (ImageView) findViewById(R.id.image_open_courses);
        tabbarImageView[4] = (ImageView) findViewById(R.id.image_market);
        tabbarTextView = new TextView[5];
        tabbarTextView[0] = (TextView) findViewById(R.id.tv_public_pay);
        tabbarTextView[1] = (TextView) findViewById(R.id.tv_discussion);
        tabbarTextView[2] = (TextView) findViewById(R.id.tv_teacher_live_radio);
        tabbarTextView[3] = (TextView) findViewById(R.id.tv_open_courses);
        tabbarTextView[4] = (TextView) findViewById(R.id.tv_market);
        //初始化其他view
        layout_public_pay = (RelativeLayout) findViewById(R.id.layout_public_pay);
        layout_public_pay.setOnClickListener(this);
        layout_discussion = (RelativeLayout) findViewById(R.id.layout_discussion);
        layout_discussion.setOnClickListener(this);
        layout_teacher_live_radio = (RelativeLayout) findViewById(R.id.layout_teacher_live_radio);
        layout_teacher_live_radio.setOnClickListener(this);
        layout_open_courses = (RelativeLayout) findViewById(R.id.layout_open_courses);
        layout_open_courses.setOnClickListener(this);
        layout_market = (RelativeLayout) findViewById(R.id.layout_market);
        layout_market.setOnClickListener(this);
        chooseTab();
        frameMenu_center = (FrameLayout) findViewById(R.id.frameMenu_center);
        frameMenu_center.setOnClickListener(this);
        plus_btn = (ImageView) findViewById(R.id.plus_btn);

        //顶部title
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("首页");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_public_pay:
                index = 0;
                chooseTab();
//                tv_title.setText("首页");
                break;
            case R.id.layout_discussion:
                index = 1;
                chooseTab();
//                tv_title.setText("老师");
                break;
            case R.id.layout_teacher_live_radio:
                index = 2;
                chooseTab();
//                tv_title.setText("我的");
                break;
            case R.id.layout_open_courses:
                index = 3;
                chooseTab();
                break;
            case R.id.layout_market:
                index = 4;
                chooseTab();
                break;
            case R.id.frameMenu_center:
                //TODO 中间按钮，暂时隐藏
                break;
        }
    }

    public void chooseTab() {
        if (index == 0 && currentTabIndex == 0) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(tabbarFragments[0]);
            if (!tabbarFragments[0].isAdded()) {
                trx.add(R.id.frame_content, tabbarFragments[0]);
            }
            trx.show(tabbarFragments[0]).commit();
            tabbarImageView[0].setSelected(true);
            tabbarTextView[0].setSelected(true);
        } else {
            if (index != currentTabIndex) {
                FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                trx.hide(tabbarFragments[currentTabIndex]);
                if (!tabbarFragments[index].isAdded()) {
                    trx.add(R.id.frame_content, tabbarFragments[index]);
                }
                trx.show(tabbarFragments[index]).commit();
            }
            tabbarImageView[currentTabIndex].setSelected(false);
            tabbarTextView[currentTabIndex].setSelected(false);
            // set current tab selected
            tabbarImageView[index].setSelected(true);
            tabbarTextView[index].setSelected(true);
            currentTabIndex = index;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e(TAG, "onKeyDown");
            DialogUtil.getInstance(context).showIKnowDialog(context, 1, selectCallBack, "提示", true, 18, "您确定要退出ZBX吗？", null);
        }
        return super.onKeyDown(keyCode, event);
    }

    DialogUtil.SelectCallBack selectCallBack = new DialogUtil.SelectCallBack() {
        @Override
        public void selectYes() {
            finish();
        }

        @Override
        public void selectNo() {

        }

        @Override
        public void selectNormal() {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        APPUtils.youmengRequestPermissions(context, youmengRequestPermissionsCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @PermissionSuccess(requestCode = youmengRequestPermissionsCode)
    public void getLocationPermissionSuccess() {
        Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionFail(requestCode = youmengRequestPermissionsCode)
    private void getLocationPermissionFail() {
        Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
