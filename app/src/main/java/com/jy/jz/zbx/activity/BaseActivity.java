package com.jy.jz.zbx.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionSuccess;

public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    //When it succeeded in obtaining permission
    @PermissionSuccess(requestCode = 100)
    public void doSomething(){
        //Toast.makeText(this, "Contact permission is granted", Toast.LENGTH_SHORT).show();
    }

    //When it failed in obtaining permission
    @PermissionFail(requestCode = 100)
    public void doFailSomething(){
        //Toast.makeText(this, "Contact permission is not granted", t.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
