package com.qiming.wcq.mymapdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.qiming.wcq.mymapdemo.API.WebViewAPI;
import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.constants.Constant;
import com.qiming.wcq.mymapdemo.util.LoadWebDataUtil;
import com.tencent.smtt.sdk.WebView;

public class HomeNavDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private ImageView mBack;
    private com.tencent.smtt.sdk.WebView mWebView;
    private String Url;
    private JavaScriptInterface JSInterface;
    private boolean isClick;
    //没网络是显示
    private ImageView mImaError;
    //没网络显示倒计时
    private TextView mTvCountTime;
    //得到intent传过来的参数
    private String param;
    //没网络布局
    private LinearLayout mLinError;
    private String title;
    private RelativeLayout mRlTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_nav_detail);
        initView();
    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mBack = (ImageView) findViewById(R.id.ima_back);
        mWebView = (com.tencent.smtt.sdk.WebView) findViewById(R.id.webview);
        mImaError = (ImageView) findViewById(R.id.ima_web_page_error);
        mTvCountTime = (TextView) findViewById(R.id.tv_request_count_time);
        mLinError = (LinearLayout) findViewById(R.id.lin_error);
        mRlTitle = (RelativeLayout) findViewById(R.id.re_title);
        mBack.setOnClickListener(this);
        //获取Url
        getData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ima_back:
                boolean back = mWebView.canGoBack();
                if (back) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                break;
        }

    }

    public void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            param = intent.getStringExtra("param");
            title = intent.getStringExtra("title");
            mTvTitle.setText(title);
            if (Constant.MSTD.equals(title) || Constant.SHCS.equals(title) || Constant.SXHC.equals(title) || Constant.JDDL.equals(title)) {
                //标题布局消失
                mRlTitle.setVisibility(View.GONE);
                Url = WebViewAPI.HOME_ICON_MSTD + param;
            } else if (Constant.ZWPT.equals(title)) {
                Url = WebViewAPI.ZWPT_URL;
            } else if (Constant.ZPFB.equals(title)) {
                Url = WebViewAPI.ZPFB_URL;
            } else if (Constant.JDKZ.equals(title)) {
                Url = WebViewAPI.JDKZ_URL;
            } else {
                //标题布局消失
                mRlTitle.setVisibility(View.GONE);
                Url = WebViewAPI.HOME_lIST_URL + param;
            }
        }


        LoadWebDataUtil mLoad = new LoadWebDataUtil(this, mWebView, mImaError, Url, mTvCountTime);
        mLoad.initData();

        // 设置js接口  第一个参数事件接口实例，第二个是实例在js中的别名，这个在js中会用到
        JSInterface = new JavaScriptInterface(this); ////------
        mWebView.addJavascriptInterface(JSInterface, "JSInterface");
    }




    public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        //去这里
        @JavascriptInterface
        public void changeActivity(String param) {
            Intent intent = new Intent(HomeNavDetailActivity.this, HtmlToAndroidRouteActivity.class);
            intent.putExtra("param", param);
            startActivity(intent);
        }

        //三坊七巷地图
        @JavascriptInterface
        public void mapSfqx(String param) {

            if (Constant.SFQX_PARAM.equals(param)) {
                Intent intent = new Intent(HomeNavDetailActivity.this, LocationMarkerActivity.class);
                startActivity(intent);
            }

        }

        @JavascriptInterface
        public void changeClick(boolean param) {
            isClick = param;
        }

        //退出webview
        @JavascriptInterface
        public void exitWebview(String param) {
            Log.e("h5返回过来的参数", ">>>>>" + param);
            HomeNavDetailActivity.this.finish();
        }

        //全景展示
        @JavascriptInterface
        public void toVr(String url) {
            Log.e("h5返回过来的参数", ">>>>>" + param);
            Intent intent = new Intent(HomeNavDetailActivity.this, VrActivity.class);
            intent.putExtra(Constant.QJZS_URL, url);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        //防止内存溢出
        destroyWebView();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack() || isClick) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void destroyWebView() {
        if (mWebView != null) {
            mWebView.destroy();
        }
    }

}
