package com.qiming.wcq.mymapdemo.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.activity.HomeNavDetailActivity;
import com.qiming.wcq.mymapdemo.activity.HomeTabActivity;
import com.qiming.wcq.mymapdemo.activity.HtmlToAndroidRouteActivity;
import com.qiming.wcq.mymapdemo.constants.Constant;
import com.qiming.wcq.mymapdemo.util.ExitAppUtil;
import com.qiming.wcq.mymapdemo.util.LoadWebDataUtil;
import com.qiming.wcq.mymapdemo.util.ProgressDialogUtil;
import com.qiming.wcq.mymapdemo.util.ToastUtil;
import com.tencent.smtt.sdk.WebView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment implements View.OnClickListener {


    private ProgressDialogUtil mDialog;
    private JavaScriptInterface JSInterface;
    private RelativeLayout mReTitle;
    private TextView mTvTitle;
    private ImageView mImaBack;
    private boolean isClick;
    private long exitTime = 0;
    //无网络时显示图片
    private ImageView mImaError;
    //无网络时显示倒计时
    private TextView mTvCount;
    private ImageView mImaExitApp;

    public StoreFragment() {
        // Required empty public constructor
    }

    private View view;
    private TextView mtitle;
    private ImageView mBack;
    private WebView mWebView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_me, container, false);

//            initView();
//            initData();
            Intent intent = new Intent(getActivity(),HomeTabActivity.class);
            getActivity().startActivity(intent);
            //设置跳转动画
            getActivity().overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
            getActivity().finish();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView() {

        mWebView = (WebView) view.findViewById(R.id.webview);
        mReTitle = (RelativeLayout) view.findViewById(R.id.re_title);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mImaBack = (ImageView) view.findViewById(R.id.ima_back);
        mImaError = (ImageView) view.findViewById(R.id.ima_web_page_error);
        mTvCount = (TextView) view.findViewById(R.id.tv_request_count_time);
        mImaExitApp = (ImageView) view.findViewById(R.id.ima_exit_app);
        mImaBack.setOnClickListener(this);
        mImaExitApp.setOnClickListener(this);

        mTvTitle.setText("商家");

    }


    private void initData() {
        String Url = "http://998.ppjd.com/lzhong/qxfj/cn/details1.html?type=" + "all";
        LoadWebDataUtil mLoad = new LoadWebDataUtil(getActivity(), mWebView, mImaError, Url, mTvCount);
        mLoad.initData();
        // 设置js接口  第一个参数事件接口实例，第二个是实例在js中的别名，这个在js中会用到
        JSInterface = new JavaScriptInterface(getActivity());
        mWebView.addJavascriptInterface(JSInterface, "JSInterface");
//        mDialog = new ProgressDialogUtil();
//        mWebView.loadUrl("http://998.ppjd.com/lzhong/qxfj/cn/details1.html?type=" + "all");
//        WebSettings settings = mWebView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        mWebView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//
//                if (newProgress == 100) {
//                    mDialog.dismissProgressDialog();
//                } else {
//                    mDialog.showProgressDialog(getActivity(), "请稍后...");
//                }
//                super.onProgressChanged(view, newProgress);
//            }
//        });
//        mWebView.setWebViewClient(new WebViewClient() {
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
//
//        // 设置js接口  第一个参数事件接口实例，第二个是实例在js中的别名，这个在js中会用到
//        JSInterface = new JavaScriptInterface(getActivity());
//        mWebView.addJavascriptInterface(JSInterface, "JSInterface");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ima_back:
                //返回键，调用了h5的返回界面
                boolean back = mWebView.canGoBack();
                if (isClick || back) {
                    mWebView.loadUrl("javascript:androidBack()");
                    mWebView.goBack();
                    isClick = false;
                } else {
                    ToastUtil.show(getActivity(),"已经是商家首页了");
                }
                break;

            case R.id.ima_exit_app:
                //退出app
                if ((System.currentTimeMillis() - exitTime) > 500) {
                    exitTime = System.currentTimeMillis();
                } else {
                    new ExitAppUtil(getActivity()).dialogExit();
                }
                break;


        }
    }


    public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void changeActivity(String param) {
            Log.e("坐标", param);
            Intent intent = new Intent(getActivity(), HtmlToAndroidRouteActivity.class);
            intent.putExtra("param", param);
            startActivity(intent);
        }

        @JavascriptInterface
        public void changeClick(boolean param) {
            isClick = param;

            mImaBack.setVisibility(View.VISIBLE);
            view.requestLayout();
        }


    }


}
