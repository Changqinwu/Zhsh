package com.qiming.wcq.mymapdemo.activity;

import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import com.qiming.wcq.mymapdemo.Fragment.HomeFragment;
import com.qiming.wcq.mymapdemo.Fragment.StoreFragment;
import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.util.ToastUtil;

public class HomeTabActivity extends BaseActivity {

    private FragmentTabHost mTabHost;
    private Handler handler = new Handler();
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tab);
//        更新版本
        updateApp();
        //初始化view
        initView();
        //获取device_id
//        getDevieceId();



    }


    private void getDevieceId() {
        TelephonyManager tm = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        Log.e("手机DeviceId",">>>>>"+deviceId);

        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("AndroidId",">>>>>"+android_id);

    }


    private void initView() {

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        LayoutInflater view = getLayoutInflater();
        View bottom_home = view.inflate(R.layout.bottom_home_item, null);
        View bottom_jq = view.inflate(R.layout.bottom_jq_item, null);
        View bottom_store = view.inflate(R.layout.bottom_store_item, null);
        View bottom_me = view.inflate(R.layout.bottom_me_item, null);

        mTabHost.addTab(mTabHost.newTabSpec("全景购物").setIndicator(bottom_home), HomeFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("景点").setIndicator(bottom_jq), HomeFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("酒店").setIndicator(bottom_store), HomeFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("停车场").setIndicator(bottom_me), HomeFragment.class, null);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                Log.e("首页", ">>>>" + s);
            }
        });

    }

//
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //是否平板
            if (!isTabletDevice(this)) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    ToastUtil.show(this, "再点一次退出程序");
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
                return true;
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
