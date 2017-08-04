//
//package com.jy.jz.zbx.dms;
//
//import android.app.AlertDialog.Builder;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Typeface;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
////import com.joyoung.cookingmachine.activity.CreateMenuActivity;
////import com.joyoung.et05.activity.TimerSettingActivity;
////import com.joyoung.hurom.bean.BaseResult;
////import com.joyoung.joyoungsmart.R;
////import com.joyoung.joyoungsmart.util.T;
////import com.joyoung.ricecooker.timepicker.timepickutil.TextUtil;
////import com.joyoung.t3ricecooker.utill.UmEventCount;
////import com.joyoung.toaster_35i6.activity.DiyCreateMenuActivity;
////import com.joyoung.widget.NTextView;
////import com.joyoung.y18.activity.Y18DiyCreateMenuActivity;
////import com.joyoungsoft.activity.MainActivity;
////import com.joyoungsoft.activity.MyCookBookActivity;
////import com.joyoungsoft.activity.V1EquipmentIntroduceActivity;
////import com.joyoungsoft.activity.WebBarActivity;
////import com.joyoungsoft.data.ret.HttpResult;
////import com.joyoungsoft.datarespository.JYHttpBase;
////import com.joyoungsoft.datarespository.JYHttpDS;
////import com.joyoungsoft.json.JsonForDev;
////import com.joyoungsoft.jyjson.LocalJsonParser;
////import com.joyoungsoft.shareControl.ShareControlActivity;
////import com.joyoungsoft.smart.ModuleInfo;
////import com.lidroid.xutils.exception.HttpException;
////import com.lidroid.xutils.http.ResponseInfo;
////import com.lidroid.xutils.http.callback.RequestCallBack;
//
//import com.jy.jz.zbx.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TitleBar extends RelativeLayout implements OnClickListener {
//
//    private Context context;
//    private ImageView leftbtn;
//    private NTextView centerTitle;
//    private ImageView rightBtn;
//    private ImageView rightbtn2;
//    private TextView mBtnRightText;
//    private List<String> popMenus;
//    private ModuleInfo ModuleInfo;
//    private boolean titleIsedit = true;
//    private int bgcor;
//    private RelativeLayout layout;
//    private Dialog dialog;
//    private ImageView rightSearch;
//
//    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initview(context);
//    }
//
//    public TitleBar(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initview(context);
//    }
//
//    public TitleBar(Context context) {
//        super(context);
//        initview(context);
//    }
//
//    private void initview(Context context) {
//        this.context = context;
//        View view = View.inflate(context, R.layout.general_title, this);
//        layout = (RelativeLayout) view.findViewById(R.id.general_title_bc);
//        leftbtn = (ImageView) view.findViewById(R.id.equiadd_bt_user);
//        centerTitle = (NTextView) view.findViewById(R.id.equiadd_tv_title);
//        rightBtn = (ImageView) view.findViewById(R.id.equiadd_bt_more);
//        rightbtn2 = (ImageView) view.findViewById(R.id.rightbtn1);
//        mBtnRightText = (TextView) view.findViewById(R.id.right_text_btn);
//        rightSearch = (ImageView) view.findViewById(R.id.iv_search);
//        hideRightBtn2();
//        rightBtn.setOnClickListener(this);
//        rightbtn2.setOnClickListener(this);
//        ModuleInfo = Cons.getModuleInControl();
//        setRightImg(R.drawable.icon_more);
//        centerTitle.setText(ModuleInfo.getName());
//
//    }
//
//
//    public void setTypeface(Typeface textFace) {
//        centerTitle.setTypeface(textFace);
//    }
//
//    public void setTitle(String title) {
//        centerTitle.setText(title);
//    }
//
//    public void setTitleColor(int color) {
//        centerTitle.setTextColor(color);
//    }
//
//    public TextView getTitleView() {
//        return centerTitle;
//    }
//
//    public String getTitle() {
//        return centerTitle.getText().toString();
//    }
//
//    public void setLeftBtn(OnClickListener onClickListener) {
//        leftbtn.setOnClickListener(onClickListener);
//    }
//
//    public void setLeftBtn(int id, OnClickListener onClickListener) {
//        leftbtn.setImageResource(id);
//        leftbtn.setOnClickListener(onClickListener);
//    }
//
//    public void setRightBtn(int id, OnClickListener onClickListener) {
//        rightBtn.setImageResource(id);
//        rightBtn.setOnClickListener(onClickListener);
//    }
//
//    public void setRightBtn(int id) {
//        rightBtn.setImageResource(id);
//    }
//
//    public void hideLeftBtn() {
//        leftbtn.setVisibility(View.GONE);
//    }
//
//    public void hideRightBtn() {
//        rightBtn.setVisibility(View.GONE);
//    }
//
//    public void setLeftImg(int id) {
//        leftbtn.setImageResource(id);
//    }
//
//    public void setRightImg(int id) {
//        // rightBtn.setBackgroundResource(id);
//        rightBtn.setImageResource(id);
//    }
//
//    public void showLeftBtn() {
//        leftbtn.setVisibility(View.VISIBLE);
//    }
//
//    public void showRightBtn() {
//        rightBtn.setVisibility(View.VISIBLE);
//    }
//
//    public void setRightSearch(int id, OnClickListener onClickListener) {
//        rightSearch.setVisibility(VISIBLE);
//        rightSearch.setImageResource(id);
//        rightSearch.setOnClickListener(onClickListener);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.equiadd_bt_more:
//                openPop();
//                UmEventCount.onEvent(context, "40T3_menuClick", null);
//                break;
//
//            case R.id.rightbtn1:
//
//                break;
//
//            default:
//                break;
//        }
//
//    }
//
//    public void setRightBtn2(Bitmap bitmap) {
//        rightbtn2.setImageBitmap(bitmap);
//    }
//
//    public void setRightBtn2(int id, OnClickListener onClickListener) {
//
//        rightbtn2.setImageResource(id);
//
//        rightbtn2.setOnClickListener(onClickListener);
//    }
//
//    public void setRightBtn2(int id) {
//
//        rightbtn2.setImageResource(id);
//    }
//
//    public void showRightbtn2() {
//        rightbtn2.setVisibility(View.VISIBLE);
//    }
//
//    public void hideRightBtn2() {
//        rightbtn2.setVisibility(View.GONE);
//    }
//
//    public void showRightbtn() {
//        rightBtn.setVisibility(View.VISIBLE);
//    }
//
//    public void hidieRightBtn() {
//        rightBtn.setVisibility(View.GONE);
//    }
//
//    public void showRightTextBtn() {
//        mBtnRightText.setVisibility(View.VISIBLE);
//    }
//
//    public void hideRightTextBtn() {
//        mBtnRightText.setVisibility(View.GONE);
//    }
//
//    public void setRightTextButtonTextAndListener(String text, OnClickListener onClickListener) {
//        setRightTextButtonText(text);
//        setRightTextButtonListener(onClickListener);
//    }
//
//    public void setRightTextButtonText(String text) {
//        mBtnRightText.setText(text);
//    }
//
//    public void setRightTextButtonListener(OnClickListener onClickListener) {
//        mBtnRightText.setOnClickListener(onClickListener);
//    }
//
//    public void setRightTextButtonTextColor(int color) {
//        mBtnRightText.setTextColor(color);
//    }
//
//    public static final String RENAME = "修改设备名";
//    public static final String DIRECTION = "常见问题";
//    public static final String MY_COOKBOOK = "我的食谱";
//    public static final String DELETEDEV = "删除设备";
//    public static final String INTRODUCEDEV = "产品介绍";
//    public static final String COOKBOOKMANAGE = "食谱管理";
//    public static final String SETTIMING = "定时设置";
//    public static final String SUBMITCOOK = "上传食谱";
//    public static final String STATISTICALREPORT = "统计报表";
//    public static final String SHARE_CONTROL = "设备共享";
//    private PopupWindow popupWindow;
//
//    @SuppressWarnings("deprecation")
//    private void openPop() {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
//        View popview = View.inflate(context, R.layout.pop_index_menu, null);
//        popupWindow = new PopupWindow(popview, width / 3, LayoutParams.WRAP_CONTENT, true);
////        popupWindow = new PopupWindow(popview, 420, LayoutParams.WRAP_CONTENT, true);
//        ListView pop_lv = (ListView) popview.findViewById(R.id.pop_lv);
//        pop_lv.setAdapter(new ArrayAdapter<String>(context, -1, popMenus) {
//
//            @Override
//            public int getCount() {
//                return popMenus == null ? 0 : popMenus.size();
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                if (convertView == null) {
//                    convertView = LayoutInflater.from(context).inflate(R.layout.pop_menu_item, parent, false);
//
//                }
//                TextView tv_item = (TextView) convertView.findViewById(R.id.pop_item_tv);
//                tv_item.setText(popMenus.get(position));
//                return convertView;
//            }
//        });
//
//        pop_lv.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String itemName = popMenus.get(position);
//                switch (itemName) {
//                    case "更改设备名":
//                    case RENAME:// 重命名
//                        openDialog();
//                        ymStatisticalData("Rename");
//                        break;
//                    case DELETEDEV:// 删除设备
//                        ymStatisticalData("Delete");
//                        ExceptionDialogUtil.getInstance(context)
//                                .showIKnowDialog(context, 1, selectCallBack, "删除设备", true,
//                                        0, "是否删除当前" + Cons.getModuleInControl().getName() + "设备", null);
//                        // ((MainActivity)context).finish();
//                        break;
//                    case DIRECTION:// 常见问题
//                        ymStatisticalData("FAQ");
//                        String FQAURL =
//                                "/api/mine/faqlist.jsp?robottype=" + Cons.getModuleInControl().getProductType();
//                        Intent intent = new Intent(context, WebBarActivity.class);
//                        intent.putExtra(WebBarActivity.URL_STRING, FQAURL);
//                        context.startActivity(intent);
//                        break;
//                    case MY_COOKBOOK:// 我的食谱
//                        if (Cons.getModuleInControl().getProductType().equals("15361") ||
//                                Cons.getModuleInControl().getProductType().equals("15363")) {
//                            if (onMyCookListener != null) {
//                                onMyCookListener.openMyCook();
//                            }
//                        } else {
//                            Intent mintent = new Intent(context, MyCookBookActivity.class);
//                            context.startActivity(mintent);
//                        }
//
//                        break;
//                    case INTRODUCEDEV:// 设备介绍页
//                        ymStatisticalData("Introduce");
//                        Intent introducedev = new Intent(context, V1EquipmentIntroduceActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("equipmentType", Cons.getModuleInControl());
//                        bundle.putBoolean("isIntroduce", true);
//                        introducedev.putExtra("data", bundle);
//                        context.startActivity(introducedev);
//                        break;
//                    case COOKBOOKMANAGE:// 跳转到食谱管理页面
//                        ymStatisticalData("CookbookManage");
//                        if (null != onMenuMangerListener) {
//                            onMenuMangerListener.openMenuManger();
//                        }
////		    Intent cookbookManageIntent = new Intent(context, Y18OrOvenRecipesManagerActivity.class);
////		    cookbookManageIntent.putExtra("devicesCode", Cons.getModuleInControl().getProductType());
////		    context.startActivity(cookbookManageIntent);
//                        break;
//                    case SETTIMING:// 跳转到"定时设置"页面
//                        ymStatisticalData("TimerSetting");
//                        Intent settimingIntent = new Intent(context, TimerSettingActivity.class);
//                        settimingIntent.putExtra("devicesCode", Cons.getModuleInControl().getProductType());
//                        context.startActivity(settimingIntent);
//                        break;
//                    case SUBMITCOOK://上传食谱
//                        ymStatisticalData("CreateCookbook");
//                        switch (Cons.getModuleInControl().getProductType()) {
//                            case "12289":
//                                Intent y18submitcook = new Intent(context, Y18DiyCreateMenuActivity.class);
//                                y18submitcook.putExtra("submit", true);
//                                context.startActivity(y18submitcook);
//                                break;
//                            case "15361":
//                            case "15363":
//                                Intent submitcook = new Intent(context, DiyCreateMenuActivity.class);
//                                submitcook.putExtra("submit", true);
//                                context.startActivity(submitcook);
//                                ((MainActivity) context).finish();
//                                break;
//                            case "15362":
//                                Intent kxsubmitcook = new Intent(context,
//                                        com.joyoung.toaster_38i95.activity.DiyCreateMenuActivity.class);
//                                kxsubmitcook.putExtra("submit", true);
//                                context.startActivity(kxsubmitcook);
//                                break;
//                            case "16385":
//                                Intent intent1 = new Intent(context, CreateMenuActivity.class);
//                                intent1.putExtra("submit", true);
//                                context.startActivity(intent1);
//                                break;
//                        }
//
//                        break;
//                    case STATISTICALREPORT://"统计报表"页面
//                        ymStatisticalData("Report");
////			Intent statisticalreportIntent = new Intent(context, V1EquipmentIntroduceActivity.class);
////			Bundle statisticalreportIntentbundle = new Bundle();
////			statisticalreportIntentbundle.putSerializable("equipmentType", Cons.getModuleInControl());
////			statisticalreportIntentbundle.putBoolean("isIntroduce", false);
////			statisticalreportIntent.putExtra("data", statisticalreportIntentbundle);
////			context.startActivity(statisticalreportIntent);
//                        String urlStr = "/api/data/heater.html";
//                        Intent statisticalreportIntent = new Intent(context, WebBarActivity.class);
//                        statisticalreportIntent.putExtra("from", "ToasterFragment");
//                        statisticalreportIntent.putExtra(WebBarActivity.URL_STRING, urlStr);
//                        context.startActivity(statisticalreportIntent);
//                        break;
//                    case SHARE_CONTROL:
//                        Intent shareControl = new Intent(context, ShareControlActivity.class);
//                        shareControl.putExtra(ShareControlActivity.PRODUCT_TYPE, Cons.getModuleInControl().getSn());
//                        shareControl.putExtra(ShareControlActivity.PRODUCT_DNAME, Cons.getModuleInControl().getDname());
//                        shareControl.putExtra(ShareControlActivity.MDCODE, Cons.getModuleInControl().getMdcode());
//                        context.startActivity(shareControl);
//                        break;
//                }
//                popupWindow.dismiss();
//            }
//
//        });
//
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.showAsDropDown(rightBtn, 0, 20);
//    }
//
//    private void openDialog() {
//        LayoutInflater inflaterDl = LayoutInflater.from(context);
//        final Builder builder = new Builder(context);
//        popupWindow.dismiss();
//        View view = inflaterDl.inflate(R.layout.dilog_rename_dev, null);
//        builder.setView(view);
//        final EditText edt_devName = (EditText) view.findViewById(R.id.edt_dev_name);
//        if (ModuleInfo != null && !TextUtil.isEmpty(ModuleInfo.getName())) {
//            // edt_devName.setText(ModuleInfo.getName());
//            // edt_devName.setSelection(edt_devName.getText().length());
//        }
//        TextView btnCancel = (TextView) view.findViewById(R.id.dig_cancel);
//        TextView btn_save = (TextView) view.findViewById(R.id.dig_confirm);
//        dialog = builder.show();
//        btnCancel.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        btn_save.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String dev_name = edt_devName.getText().toString();
//                if (!TextUtil.isEmpty(dev_name)) {
//                    updateDevName(dev_name);
//                } else {
//                    T.show(context, "设备名不能为空");
//                }
//                dialog.dismiss();
//
//            }
//        });
//
//    }
//
//    //    private EquimentDAO dao;
//    private String devName;
//    private ModuleInfo tempMod;
//
//    public void updateDevName(final String devName) {
////        dao = new EquimentDAOImpl(context);
////	final ModuleInfo tempMod = dao.getModuleInfoByUidAndId(Cons.user.getId(),
////		Cons.getModuleInControl().getSn().toUpperCase());
//        this.devName = devName;
//        tempMod = Cons.getModuleInControl();
//        tempMod.setName(devName);
//        tempMod.setIsActivate("1");
//        List<ModuleInfo> moduleInfos = new ArrayList<ModuleInfo>();
//        moduleInfos.add(tempMod);
//        new JYHttpDS.MBuilder().setBeginSign(CountEvent.E_RENAME_TITLE)
//                .build().updateDevName(tempMod);
//    }
//
//    /**
//     * 更改TextView上面设备名称
//     */
//    public void modifyDevicesNameInTextView(BaseResult<Object> result) {
//        if (result != null && result.getCode() == 0) {
//            if (tempMod != null && !TextUtil.isEmpty(tempMod.getSn())) {
//                if (titleIsedit) {
//                    centerTitle.setText(devName);
//                }
//                Cons.getModuleInControl().setName(devName);
//
//            }
//        } else {
//            T.show(context, "修改设备名称失败!");
//        }
//    }
//
//    // 删除设备(服务器)
//    public void deleteDev() {
////        dao = new EquimentDAOImpl(context);
//        try {
//            JYHttpBase.getInstance()
//                    .newPost(Cons.NEW_DELETE_DEV, JsonForDev.getDelDevUrl(Cons.getModuleInControl().getSn()),
//                            new RequestCallBack<String>() {
//
//                                @Override
//                                public void onSuccess(ResponseInfo<String> arg0) {
//                                    String res = arg0.result;
//                                    HttpResult<Object> httpResult = LocalJsonParser
//                                            .json2HttpResult(res, Object.class);
//                                    if (httpResult.getCode() == 0) {
//                                        DeviceUpdataUtils.getInstace().getDeviceNetWork(context);
//                                        ((MainActivity) context).finish();
//                                    } else {
//                                        T.show(context, "删除设备失败，请稍后重试");
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(HttpException arg0, String arg1) {
//                                    T.show(context, "网络不给力，请稍后尝试删除设备");
//                                }
//                            });
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//    }
//
//    public void setConModuleName(String mName) {
//        Cons.getModuleInControl().setName(mName);
//    }
//
//
//    public boolean isTitleIsedit() {
//        return titleIsedit;
//    }
//
//    public void setTitleIsedit(boolean titleIsedit) {
//        this.titleIsedit = titleIsedit;
//    }
//
//    public List<String> getPopMenus() {
//        return popMenus;
//    }
//
//    public void setPopMenus(List<String> popMenus) {
//        this.popMenus = popMenus;
//    }
//
//    public PopupWindow getPopupWindow() {
//        return popupWindow;
//    }
//
//    public void ymStatisticalData(String statisticalType) {
//        String prodctType = Cons.getModuleInControl().getProductType();
//        switch (prodctType) {
//            case "0002":
//            case "2":// 667豆浆机
//                JYUmOnlineStatistics.onEvent(context, "D667SG", "RightCornerMenu", statisticalType);
//                break;
//            case "8193":// 净水机
//                JYUmOnlineStatistics.onEvent(context, "13T450B", "RightCornerMenu", statisticalType);
//                break;
//            case "0004":
//            case "4":// K5原浆机
//                JYUmOnlineStatistics.onEvent(context, "K5", "RightCornerMenu", statisticalType);
//                break;
//            case "0005":// P3
//            case "5":
//                JYUmOnlineStatistics.onEvent(context, "P3", "RightCornerMenu", statisticalType);
//                break;
//            case "0006":// G3
//            case "6":
//                JYUmOnlineStatistics.onEvent(context, "G3", "RightCornerMenu", statisticalType);
//                break;
//            case "0007":
//            case "7":
//                JYUmOnlineStatistics.onEvent(context, "Q1", "RightCornerMenu", statisticalType);
//                break;
//            case "0009":
//            case "9":
//                JYUmOnlineStatistics.onEvent(context, "Q1-01", "RightCornerMenu", statisticalType);
//                break;
//            case "10240":// 养生壶
//                JYUmOnlineStatistics.onEvent(context, "K06_I1", "RightCornerMenu", statisticalType);
//                break;
//            case "11264":// 饭煲603
//                JYUmOnlineStatistics.onEvent(context, "40FS603", "RightCornerMenu", statisticalType);
//                break;
//            case "11268":// 饭煲607
//                JYUmOnlineStatistics.onEvent(context, "40FS607", "RightCornerMenu", statisticalType);
//                break;
//            case "11267":// 饭煲40FS30
//                JYUmOnlineStatistics.onEvent(context, "40FS30", "RightCornerMenu", statisticalType);
//                break;
//            case "12288":// 原汁机V915
//                JYUmOnlineStatistics.onEvent(context, "JYZ_V915", "RightCornerMenu", statisticalType);
//                break;
//            case "13313":// 压力煲50C8
//                JYUmOnlineStatistics.onEvent(context, "Y_50C8", "RightCornerMenu", statisticalType);
//                break;
//            case "11266":// 饭煲40T3
//                JYUmOnlineStatistics.onEvent(context, "40T3", "RightCornerMenu", statisticalType);
//            case "15361":// 烤箱35i6
//            case "15363":
//                JYUmOnlineStatistics.onEvent(context, "35i6-NewUI", "RightCornerMenu", statisticalType);
//            case "12289":// Y18
//                JYUmOnlineStatistics.onEvent(context, "Y18-NewUI", "RightCornerMenu", statisticalType);
//            case "5120":// 热水器
//            case "5121":
//            case "5122":
//            case "5123":
//            case "5124":
//            case "5125":
//                JYUmOnlineStatistics.onEvent(context, "DT03", "RightCornerMenu", statisticalType);
//                break;
//            case "15362":
//                JYUmOnlineStatistics.onEvent(context, "38i95", "RightCornerMenu", statisticalType);
//            case "16385":
//                JYUmOnlineStatistics.onEvent(context, "J7", "RightCornerMenu", statisticalType);
//                break;
//        }
//    }
//
//    /**
//     * 弹框回调
//     */
//    public ExceptionDialogUtil.SelectCallBack selectCallBack = new ExceptionDialogUtil.SelectCallBack() {
//
//        @Override
//        public void selectYes() {
//            deleteDev();
//            ymStatisticalData("Delete");
//        }
//
//        @Override
//        public void selectNormal() {
//        }
//
//        @Override
//        public void selectNo() {
//        }
//    };
//
//    public int getBgcor() {
//        return bgcor;
//    }
//
//    public void setBgcor(int bgcor) {
//        layout.setBackgroundColor(bgcor);
//    }
//
//    public Dialog getDialog() {
//        return dialog;
//    }
//
//    private OnMenuMangerListener onMenuMangerListener;
//
//    private OnMyCookListener onMyCookListener;
//
//    public interface OnMenuMangerListener {
//
//        public void openMenuManger();
//    }
//
//    public interface OnMyCookListener {
//
//        public void openMyCook();
//    }
//
//    public void setOnMyCookListener(OnMyCookListener onMyCookListener) {
//        this.onMyCookListener = onMyCookListener;
//    }
//
//    public void setOnMenuMangerListener(OnMenuMangerListener onMenuMangerListener) {
//        this.onMenuMangerListener = onMenuMangerListener;
//    }
//
//}
