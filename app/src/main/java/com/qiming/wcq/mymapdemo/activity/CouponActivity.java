package com.qiming.wcq.mymapdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiming.wcq.mymapdemo.API.WebViewAPI;
import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.constants.Constant;
import com.qiming.wcq.mymapdemo.util.LoadWebDataUtil;
import com.tencent.smtt.sdk.WebView;

public class CouponActivity extends BaseActivity {

    private WebView mWebView;
    private ImageView mImaError;
    private TextView mTvCountTime;
    private LinearLayout mLinError;
    private String param;
    private String title;
    private String Url;
    private JavaScriptInterface JSInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        //初始化View
        initView();

    }

    private void initView() {
        mWebView = (com.tencent.smtt.sdk.WebView) findViewById(R.id.webview);
        mImaError = (ImageView) findViewById(R.id.ima_web_page_error);
        mTvCountTime = (TextView) findViewById(R.id.tv_request_count_time);
        mLinError = (LinearLayout) findViewById(R.id.lin_error);

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            param = intent.getStringExtra("param");
            if (Constant.JQMP.equals(param)) {
                Url = WebViewAPI.JQMQ_URL;
            } else if (Constant.YHQ.equals(param)) {
                Url = WebViewAPI.YHQ_URL;
            } else {
                Url = WebViewAPI.FSMQ_URL;
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

        @JavascriptInterface
        public void exitWebview(String param) {
            Log.e("h5返回过来的参数",">>>>>"+param);
            CouponActivity.this.finish();
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack() ) {
//            mWebView.goBack();// 返回前一个页面
            mWebView.loadUrl("javascript:androidBack()");
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
