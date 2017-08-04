package com.jy.jz.zbx.utils;

import com.jy.jz.zbx.bean.AddNames;
import com.jy.jz.zbx.service.Address;

/**
 * Created by lijin on 2016/8/19.
 */
public class ConfigUtils {
    /**
     * 基本地址
     **/
    public static String HostIP = "http://jindra.wicp.net"; // 接口外侧测地址
    public volatile static String SERVICETYPE = "/HeiNiuEducation/app";
    public volatile static String DEFAULTHOST = HostIP + SERVICETYPE;

    /**
     * 具体接口
     **/
    public static String CLIENTLOGIN = DEFAULTHOST + "/user!clientLogin.action";//登陆
    public static String CHECKUSEREXISTS = DEFAULTHOST + "/user!checkUserExists.action";//检查用户是否存在
    public static String GETREGISTCODE = DEFAULTHOST + "/user!getRegistCode.action";//获取注册码
    public static String REGISTER = DEFAULTHOST + "/user!register.action";//注册
    public static String GETNEWTOKEN = DEFAULTHOST + "/user!getNewToken.action";//置换token
    public static String CHECKTOKENVALID = DEFAULTHOST + "/user!checkTokenValid.action";//检查token是否过期
    /**
     * 美食杰
     **/
    public static String GETRECIPELIST = "http://api.meishi.cc/jiuyang/get_recipe_list.php"; //菜谱列表
    public static String RECIPEDETAIL = "http://api.meishi.cc/jiuyang/recipe_detail.php"; //菜谱详情

    /**
     * 照片获取
     */
//    public static String GETTWOIMG = "http://www.heiniu.net.cn/testImg/in/img!getTwoImg"; //获取照片
//    public static String BASEIP = "http://jingzhong3.s1.natapp.cc/imgTest/in";
//    public static String BASEIP = "http://112.124.102.114:8089/imgTest/in";
            //http://kg.joyoung.com/
            public static String BASEIP = "http://kg.joyoung.com/imgTest/in";
    //public static String BASEIP = "http://jingzhong.s1.natapp.cc/imgTest/in";
    public static String GETTWOIMG = BASEIP + "/img!getTestImg"; //获取照片
    public static String UPDATESAVEDAY = BASEIP + "/general!updateSaveDay"; //获取照片
    public static String ADDUPLOADIST = BASEIP + "/general!addUploadIst"; //是否上传照片 1.是。 0.否。
    public static String IMGUPLOADPRODUCT = BASEIP + "/img!imgUploadProduct"; //上传照片
    public static String IMGUPLOAD = "http://www.heiniu.net.cn/testImg/in/img!imgUpload"; //上传照片 上传图片 参数 imgs 类型 file 可以传多张  我只接收两张



    public static Address address;
    public static AddNames addNames;

    // 内网UDP广播端口
    public static int udpSendPort = 33999;	//Cons.udpSendPort
}
