package com.qiming.wcq.mymapdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.util.LoadWebDataUtil;

public class HomeBottomVrActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private ImageView mImaBack;
    private com.tencent.smtt.sdk.WebView mWebView;
    private String Url;
    //无网络时显示图片
    private ImageView mImaError;
    //无网络时显示倒计时
    private TextView mTvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_bottom_vr);
        //初始化view
        initView();
    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mImaBack = (ImageView) findViewById(R.id.ima_back);
        mWebView = (com.tencent.smtt.sdk.WebView) findViewById(R.id.webview);
        mImaError = (ImageView) findViewById(R.id.ima_web_page_error);
        mTvCount = (TextView) findViewById(R.id.tv_request_count_time);
        mImaBack.setOnClickListener(this);
        //载入webview内容
        loadWebData();

    }

    private void loadWebData() {

        Intent intent = getIntent();
        String param = intent.getStringExtra("param");
        String type = intent.getStringExtra("type");
        mTvTitle.setText(param);

        Url = "http://998.ppjd.com/lzhong/qxfj/cn/detail-list1.html?types=" + type;
        LoadWebDataUtil mLoad = new LoadWebDataUtil(this, mWebView, mImaError, Url, mTvCount);
        mLoad.initData();

//        WebSettings settings = mWebView.getSettings();
//        settings.setJavaScriptEnabled(true);
//
//        mWebView.loadUrl(Url);
//        mWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                view.setVisibility(View.GONE);
//                mImaError.setVisibility(View.VISIBLE);
//                super.onReceivedError(view, errorCode, description, failingUrl);
//            }
//        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ima_back:

                boolean b = mWebView.canGoBack();
                if (b) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
