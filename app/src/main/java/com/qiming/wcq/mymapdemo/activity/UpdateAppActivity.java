package com.qiming.wcq.mymapdemo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.constants.Constant;
import com.qiming.wcq.mymapdemo.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;


public class UpdateAppActivity extends BaseActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private LinearLayout mLinInstall;
    private Button mBtnInstall;
    private File absoluteFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_app);
        //初始化view
        initView();
        Intent intent = getIntent();
        if (intent != null) {
            String versonAddress = intent.getStringExtra(Constant.UPDATE_ADDRESS);
            //更新app弹窗
            showUpdateAppDialog(versonAddress);
        } else {
            ToastUtil.show(this, "app更新地址有误...");
        }

    }

    private void initView() {
        mLinInstall = (LinearLayout) findViewById(R.id.lin_install);
        mBtnInstall = (Button) findViewById(R.id.btn_install);
        mBtnInstall.setOnClickListener(this);
    }

    private void showUpdateAppDialog(String versonAddress) {
        ToastUtil.show(this, "有新版本...");
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("有新版本");
        progressDialog.setCancelable(false);
        progressDialog.show();
        OkHttpUtils.get()
                .url(versonAddress)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "zmp.apk") {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(File response, int id) {
                        //安装app
                        goInstallApp(response);
                        absoluteFile = response.getAbsoluteFile();
                        mLinInstall.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        progressDialog.setTitle("正在下载...");
                        progressDialog.setProgress((int) ((int) total * progress));
                        progressDialog.setMax((int) total);
                    }
                });
    }

    private void goInstallApp(File file) {
        // 核心是下面几句代码
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //解决安装闪退问题，加上这句话
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
        progressDialog.dismiss();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_install:
                //去安装app
                goInstallApp(absoluteFile);
                break;
        }
    }

}
