package com.qiming.wcq.mymapdemo.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.API.WebViewAPI;
import com.qiming.wcq.mymapdemo.util.ExitAppUtil;
import com.qiming.wcq.mymapdemo.util.LoadWebDataUtil;
import com.qiming.wcq.mymapdemo.util.ProgressDialogUtil;
import com.qiming.wcq.mymapdemo.util.ToastUtil;
import com.tencent.smtt.sdk.WebView;

import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberCenterFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView mtitle;
    private ImageView mBack;
    private WebView mWebView;
    private ProgressBar pb;
    private ProgressDialogUtil progressDialog;
    //无网络时显示图片
    private ImageView mImaError;
    //无网络时显示倒计时
    private TextView mTvCount;
    private Handler handler = new Handler();
    //退出app
    private ImageView mImaExitApp;
    private long exitTime = 0;

    public MemberCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_jing_qu, container, false);
            initView();
            //加载网页
            initData();

        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView() {

        mWebView = (WebView) view.findViewById(R.id.webview);
        pb = (ProgressBar) view.findViewById(R.id.progressBar);
        mImaError = (ImageView) view.findViewById(R.id.ima_web_page_error);
        mTvCount = (TextView) view.findViewById(R.id.tv_request_count_time);
        mImaExitApp = (ImageView) view.findViewById(R.id.ima_exit_app);
        mImaExitApp.setOnClickListener(this);
    }


    private void initData() {
        String Url = WebViewAPI.MEMBER_CENTER_URL;
        LoadWebDataUtil mLoad = new LoadWebDataUtil(getActivity(), mWebView, mImaError, Url, mTvCount);
        mLoad.initData();


//
//        progressDialog = new ProgressDialogUtil();
//        //会员中心链接
//        mWebView.loadUrle(WbViewAPI.MEMBER_CENTER_URL);
//        WebSettings settings = mWebView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        mWebView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress == 100) {
//                    progressDialog.dismissProgressDialog();
//                } else {
//
//                    progressDialog.showProgressDialog(getActivity(), "请稍后...");
//                }
//
//                super.onProgressChanged(view, newProgress);
//            }
//        });
//
//        mWebView.setWebViewClient(new WebViewClient() {
//            public boolean isLoadFaild;
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if (isLoadFaild) {
//                    view.setVisibility(View.GONE);
//                    mImaError.setVisibility(View.VISIBLE);
//                }else {
//                    view.setVisibility(View.VISIBLE);
//                    mImaError.setVisibility(View.GONE);
//                }
//                super.onPageFinished(view, url);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                //显示网络错误图片
//                view.setVisibility(View.GONE);
//                mImaError.setVisibility(View.VISIBLE);
//                isLoadFaild = true;
//                ToastUtil.show(getActivity(),"网络出错了，5秒后尝试重新连接。。。");
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        initData();
//                    }
//                },5000);
//                super.onReceivedError(view, errorCode, description, failingUrl);
//            }
//        });
//

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
}
