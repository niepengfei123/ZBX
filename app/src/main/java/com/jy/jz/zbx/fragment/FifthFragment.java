package com.jy.jz.zbx.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jy.jz.zbx.R;
import com.jy.jz.zbx.activity.FoodsDetailsActivity;
import com.jy.jz.zbx.activity.UserCenterActivity;
import com.jy.jz.zbx.adapter.DrinkFoodsAdapater;
import com.jy.jz.zbx.bean.MarketBean;
import com.jy.jz.zbx.service.MyGridView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijin on 2016/7/26.
 * 公开课
 */
public class FifthFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "FifthFragment";
    protected Context mContext;
    private Intent intent;
    protected View rootView;
    protected ImageView img_user_center;
    private RelativeLayout rl_drink_foods;
    private RelativeLayout rl_fruit_vegetables;
    private RelativeLayout rl_frozen_meat;
    private RelativeLayout rl_condiment_other;
    private MyGridView gd_drink_foods;
    private MyGridView gd_fruit_vegetables;
    private MyGridView gd_frozen_meat;
    private MyGridView gd_condiment_other;
    private DrinkFoodsAdapater drinkFoodsAdapater;
    private DrinkFoodsAdapater fruitVegetablesAdapter;
    private DrinkFoodsAdapater frozenMeatAdapter;
    private DrinkFoodsAdapater condimentOtherAdapter;
    private List<MarketBean> drinkFoodsList;
    private List<MarketBean> fruitVegetablesList;
    private List<MarketBean> frozenMeatList;
    private List<MarketBean> condimentOtherList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fifth_fragment, container, false);
        mContext = getActivity();
        initView();
        initData();
        initGrids();
        return rootView;
    }


    public void initView() {
        img_user_center = (ImageView) rootView.findViewById(R.id.img_user_center);
        rl_drink_foods = (RelativeLayout) rootView.findViewById(R.id.rl_drink_foods);
        rl_fruit_vegetables = (RelativeLayout) rootView.findViewById(R.id.rl_fruit_vegetables);
        rl_frozen_meat = (RelativeLayout) rootView.findViewById(R.id.rl_frozen_meat);
        rl_condiment_other = (RelativeLayout) rootView.findViewById(R.id.rl_condiment_other);

        gd_drink_foods = (MyGridView) rootView.findViewById(R.id.gd_drink_foods);
        gd_fruit_vegetables = (MyGridView) rootView.findViewById(R.id.gd_fruit_vegetables);
        gd_frozen_meat = (MyGridView) rootView.findViewById(R.id.gd_frozen_meat);
        gd_condiment_other = (MyGridView) rootView.findViewById(R.id.gd_condiment_other);
    }

    private void initData() {
        drinkFoodsList = new ArrayList<>();
        fruitVegetablesList = new ArrayList<>();
        frozenMeatList = new ArrayList<>();
        condimentOtherList = new ArrayList<>();
        drinkFoodsList.add(new MarketBean(R.mipmap.milk, "牛奶", "价格未知", "<100m"));
        drinkFoodsList.add(new MarketBean(R.mipmap.hot_strip, "辣条", "9.9元", "200m"));
        drinkFoodsList.add(new MarketBean(R.mipmap.cola, "可乐", "3.9元", "300m"));
        drinkFoodsList.add(new MarketBean(R.mipmap.soda, "汽水饮料", "价格未知", "400m"));
        drinkFoodsAdapater = new DrinkFoodsAdapater(mContext, drinkFoodsList);
        gd_drink_foods.setAdapter(drinkFoodsAdapater);

        fruitVegetablesList.add(new MarketBean(R.mipmap.lettuce, "生菜", "免费", "<100m"));
        fruitVegetablesList.add(new MarketBean(R.mipmap.ginger_2, "生姜", "1.0元", "200m"));
        fruitVegetablesList.add(new MarketBean(R.mipmap.pitaya_2, "火龙果", "9.9元", "200m"));
        fruitVegetablesList.add(new MarketBean(R.mipmap.pear, "香梨", "9.9元", "300m"));
        fruitVegetablesAdapter = new DrinkFoodsAdapater(mContext, fruitVegetablesList);
        gd_fruit_vegetables.setAdapter(fruitVegetablesAdapter);

        frozenMeatList.add(new MarketBean(R.mipmap.spinach_2, "菠菜", "免费", "<100m"));
        frozenMeatList.add(new MarketBean(R.mipmap.meat, "猪肉", "9.9元", "200m"));
        frozenMeatList.add(new MarketBean(R.mipmap.ball, "肉丸", "3.9元", "300m"));
        frozenMeatList.add(new MarketBean(R.mipmap.boiled, "速冻水饺", "3.9元", "300m"));
        frozenMeatAdapter = new DrinkFoodsAdapater(mContext, frozenMeatList);
        gd_frozen_meat.setAdapter(frozenMeatAdapter);

        condimentOtherList.add(new MarketBean(R.mipmap.chilli_2, "韩式辣酱", "价格未知", "<100m"));
        condimentOtherList.add(new MarketBean(R.mipmap.paste_2, "黄豆酱", "9.9元", "200m"));
        condimentOtherList.add(new MarketBean(R.mipmap.sauce_2, "酱油", "6.9元", "200m"));
        condimentOtherList.add(new MarketBean(R.mipmap.zacai, "榨菜", "2.9元", "300m"));
        condimentOtherAdapter = new DrinkFoodsAdapater(mContext, condimentOtherList);
        gd_condiment_other.setAdapter(condimentOtherAdapter);


        img_user_center.setOnClickListener(this);
        rl_drink_foods.setOnClickListener(this);
        rl_fruit_vegetables.setOnClickListener(this);
        rl_frozen_meat.setOnClickListener(this);
        rl_condiment_other.setOnClickListener(this);


    }

    private void initGrids() {
        gd_drink_foods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        intent = new Intent(mContext, FoodsDetailsActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_user_center:
                intent = new Intent(mContext, UserCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_condiment_other://调味。其他
//                Intent intent = new Intent(mContext, UserCenterActivity.class);
//                startActivity(intent);
                break;
            case R.id.rl_drink_foods://饮料食品
//                Intent intent = new Intent(mContext, UserCenterActivity.class);
//                startActivity(intent);
                break;
            case R.id.rl_frozen_meat://冷冻肉
//                Intent intent = new Intent(mContext, UserCenterActivity.class);
//                startActivity(intent);
                break;
            case R.id.rl_fruit_vegetables://水果蔬菜更多
//                Intent intent = new Intent(mContext, UserCenterActivity.class);
//                startActivity(intent);
                break;
        }
    }
}
