package com.jy.jz.zbx.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * Created by lijin on 2016/7/28.
 */
public class ZBXApplication extends Application {
    public static Context applicationContext;
    private static ZBXApplication instance;
    public static DbManager db;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);//初始化fresco
        x.Ext.init(this);
        x.Ext.setDebug(true);
        applicationContext = this;

        MobclickAgent.setScenarioType(applicationContext, MobclickAgent.EScenarioType.E_UM_NORMAL);
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");//微信 appid appsecret
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");//新浪微博 appkey appsecret
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");// QQ和Qzone appid appkey
//        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
//                .setDbName("iDEvent.db")//设置数据库名称
//                // 不设置dbDir时, 默认存储在app的私有目录.
//                .setDbDir(new File(Environment.getExternalStorageDirectory().getPath())) // 数据库存储路径
//                .setDbVersion(1)//设置数据库版本
//                .setAllowTransaction(true)//设置是否开启事务,默认为false关闭事务
//                .setDbOpenListener(new DbManager.DbOpenListener() {
//                    @Override
//                    public void onDbOpened(DbManager db) {
//                        // 开启WAL, 对写入加速提升巨大
//                        db.getDatabase().enableWriteAheadLogging();
//                    }
//                });
//                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
//                    @Override
//                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
//                        try {
//                            db.addColumn(Sign.class,"test");
//                        } catch (DbException e) {
//                            MLog.e("test","数据库更新失败");
//                            e.printStackTrace();
//                        }
                        // db.dropTable(...);
                        // ...
                        // or
                        // db.dropDb();
//                    }
//                });
        //db还有其他的一些构造方法，比如含有更新表版本的监听器的
//        db = x.getDb(daoConfig);//获取数据库单例

        DbManager.DaoConfig daoConfig=new DbManager.DaoConfig()
                .setDbName("addNames.db")
//                .setDbDir(new File(Environment.getExternalStorageDirectory().getPath())) // 数据库存储路径
                .setAllowTransaction(true)
                .setDbVersion(1)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                    }
                }) ;

        /** DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
         .setDbName("test")
         .setDbVersion(1)
         .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
        @Override
        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
        // TODO: ...
        // db.addColumn(...);
        // db.dropTable(...);
        // ...
        }
        });
         db= x.getDb(daoConfig);*/
        db= x.getDb(daoConfig);

    }
}
