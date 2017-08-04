package com.jy.jz.zbx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jy.jz.zbx.R;


/**
 * Created by Mic-roo on 2016/11/23 0023.
 * 个人中心
 */

public class UserCenterActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rl_user_information;
    private RelativeLayout rl_family_member;
    private RelativeLayout rl_home_menu;
    private RelativeLayout rl_recipe_collection;
    private RelativeLayout rl_system_management;
    private ImageView img_user_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        initView();
        initData();
    }

    private void initView() {
        rl_system_management = (RelativeLayout) findViewById(R.id.rl_system_management);
        rl_recipe_collection = (RelativeLayout) findViewById(R.id.rl_recipe_collection);
        rl_home_menu = (RelativeLayout) findViewById(R.id.rl_home_menu);
        rl_family_member = (RelativeLayout) findViewById(R.id.rl_family_member);
        rl_user_information = (RelativeLayout) findViewById(R.id.rl_user_information);
        img_user_back = (ImageView) findViewById(R.id.img_user_back);
    }

    private void initData() {
        rl_user_information.setOnClickListener(this);
        rl_family_member.setOnClickListener(this);
        rl_home_menu.setOnClickListener(this);
        rl_recipe_collection.setOnClickListener(this);
        rl_system_management.setOnClickListener(this);
        img_user_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_user_back:
                finish();
                break;
            case R.id.rl_user_information://个人信息
                break;
            case R.id.rl_family_member://家庭成员管理
                break;
            case R.id.rl_home_menu://家庭菜单
                break;
            case R.id.rl_recipe_collection://食谱收藏
                break;
            case R.id.rl_system_management://系统管理
                break;
        }
    }
}
