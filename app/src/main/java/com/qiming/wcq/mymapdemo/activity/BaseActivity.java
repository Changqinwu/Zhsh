package com.qiming.wcq.mymapdemo.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.callback.UpdateAppCallBack;
import com.qiming.wcq.mymapdemo.receiver.ConnectionChangeReceiver;
import com.qiming.wcq.mymapdemo.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import static com.qiming.wcq.mymapdemo.API.WebViewAPI.update_url;
import static com.qiming.wcq.mymapdemo.constants.Constant.VERSIONID;

@RuntimePermissions
public class BaseActivity extends AppCompatActivity {


    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private boolean isPermission;
    private ConnectionChangeReceiver mNetworkStateReceiver;
    public int screenWidth;
    private PackageInfo mPackageInfo;
    private String localVersionName;
    private int localversionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //获取屏幕宽度
        getScreenProperty();

//        //透明状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
        //初始化权限
        BaseActivityPermissionsDispatcher.showPermissionWithCheck(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //注册判断是否有网络的广播
        IntentFilter filter = new IntentFilter();
        mNetworkStateReceiver = new ConnectionChangeReceiver();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkStateReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        unregisterReceiver(mNetworkStateReceiver);
    }

    /*
           *
            * 引用权限相关和第三方库PermissionsDispatcher的一些回调方法
            *
            * */
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showPermission() {
//        ToastUtil.show(this,"已获取你的定位权限");
        isPermission = false;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BaseActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);

    }

    //提示用户为什么需要此权限
    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationale(final PermissionRequest request) {
        request.proceed();
    }

    //一旦用户拒绝了
    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeny() {
//        ToastUtil.show(this,"拒绝了该权限");
        builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setTitle("请允许获取设备信息")
                .setMessage("需要获取设备信息的权限才能正常使用")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //初始化权限
                        BaseActivityPermissionsDispatcher.showPermissionWithCheck(BaseActivity.this);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                });
        builder.show();
    }

    //用户选择的不再询问
    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showAskAagain() {
        isPermission = false;
        if (!isPermission) {
//            ToastUtil.show(this,"不在询问该权限");
            builder = new AlertDialog.Builder(BaseActivity.this);
            builder.setTitle("请允许获取设备信息")
                    .setMessage("需要获取设备信息的权限才能正常使用")
                    .setCancelable(false)
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //跳转到设置应用信息权限
                            Intent localIntent = new Intent();
                            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            if (Build.VERSION.SDK_INT >= 9) {
                                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                            } else if (Build.VERSION.SDK_INT <= 8) {
                                localIntent.setAction(Intent.ACTION_VIEW);
                                localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
                                localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                            }
                            startActivity(localIntent);
                            isPermission = true;
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        }
                    });
            dialog = builder.show();
        }

    }



    @Override
    protected void onResume() {
        super.onResume();

        if (isPermission) {
            //再次询问
            showAskAagain();
        }
    }

    private void getScreenProperty() {
        String release = Build.VERSION.RELEASE;

        WindowManager manager  = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;
        float density = dm.density;
        int densityDpi = dm.densityDpi;

        screenWidth = (int) (widthPixels/density);
        int screenHeight = (int) (heightPixels/density);

        Log.e("屏幕的属性","宽"+screenWidth+">>>>高"+screenHeight);
        Log.e("android版本","版本号"+release);

        if (screenHeight > 600) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }



    /**
     * 判断是否平板设备
     *
     * @param context
     * @return true:平板,false:手机
     */
    public boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    public void updateApp() {
        //获取本地版本
        PackageManager manager = this.getPackageManager();
        try {
            mPackageInfo = manager.getPackageInfo(this.getPackageName(), 0);
            localVersionName = mPackageInfo.versionName;
            localversionCode = mPackageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ToastUtil.show(BaseActivity.this,"版本名称："+localVersionName);
        //获取服务器版本
        OkHttpUtils.get()
                .id(VERSIONID)
                .url(update_url)
                .build()
                .execute(new UpdateAppCallBack(this, localVersionName, localversionCode));
    }
}
