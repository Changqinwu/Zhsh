package com.qiming.wcq.mymapdemo.Fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.util.ExitAppUtil;
import com.qiming.wcq.mymapdemo.util.LoadWebDataUtil;
import com.qiming.wcq.mymapdemo.util.ProgressDialogUtil;
import com.qiming.wcq.mymapdemo.util.ToastUtil;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class EnterFragment extends Fragment implements View.OnClickListener {


    private ProgressDialogUtil progressDialogUtil;
    //无网络时显示图片
    private ImageView mImaError;
    //无网络时显示倒计时
    private TextView mTvCount;
    private Handler handler = new Handler();
    private ImageView mImaExitApp;
    private long exitTime = 0;

    public EnterFragment() {
        // Required empty public constructor
    }

    private View view;
    private TextView mtitle;
    private ImageView mBack;
    private com.tencent.smtt.sdk.WebView mWebView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_store, container, false);
            initView();
            initData();
        }
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView() {
        mWebView = (com.tencent.smtt.sdk.WebView) view.findViewById(R.id.webview);
        mImaError = (ImageView) view.findViewById(R.id.ima_web_page_error);
        mTvCount = (TextView) view.findViewById(R.id.tv_request_count_time);
        mImaExitApp = (ImageView) view.findViewById(R.id.ima_exit_app);
        mImaExitApp.setOnClickListener(this);

    }

    private void initData() {
        String Url = "http://998.ppjd.com/lzhong/qxfj/cn/join-us.html";
        LoadWebDataUtil mLoad = new LoadWebDataUtil(getActivity(), mWebView, mImaError, Url, mTvCount);
        mLoad.initData();

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
