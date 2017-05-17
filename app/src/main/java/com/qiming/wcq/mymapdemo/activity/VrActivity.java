package com.qiming.wcq.mymapdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiming.wcq.mymapdemo.API.WebViewAPI;
import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.constants.Constant;
import com.qiming.wcq.mymapdemo.util.LoadWebDataUtil;
import com.tencent.smtt.sdk.WebView;

public class VrActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private ImageView mBack;
    private WebView mWebView;
    private ImageView mImaError;
    private TextView mTvCountTime;
    private LinearLayout mLinError;
    private RelativeLayout mRlTitle;
    private String param;
    private String Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr);

        //初始化view
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
        getData();
    }


    public void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            param = intent.getStringExtra(Constant.QJZS_URL);
            mTvTitle.setText("全景展示");
            if (Constant.FZSFQX_VR.equals(param)) {
                Url = WebViewAPI.FZSFQX_VR_URL;
            }else {
                Url = param;
            }
        }

        LoadWebDataUtil mLoad = new LoadWebDataUtil(this, mWebView, mImaError, Url, mTvCountTime);
        mLoad.initData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //加载空URl，关掉VR声音
        Url = "";
        mWebView.loadUrl(Url);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.ima_back:
                finish();
                break;
        }
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
////            mWebView.goBack();// 返回前一个页面
//            mWebView.loadUrl("javascript:androidBack()");
//            mWebView.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

}
