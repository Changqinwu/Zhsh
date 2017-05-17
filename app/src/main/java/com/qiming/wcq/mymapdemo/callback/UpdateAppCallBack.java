package com.qiming.wcq.mymapdemo.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.gson.Gson;
import com.qiming.wcq.mymapdemo.activity.MainTainActivity;
import com.qiming.wcq.mymapdemo.activity.UpdateAppActivity;
import com.qiming.wcq.mymapdemo.constants.Constant;
import com.qiming.wcq.mymapdemo.entity.MaintainEntity;
import com.qiming.wcq.mymapdemo.entity.UpdateEntity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.math.BigDecimal;
import okhttp3.Call;

import static com.qiming.wcq.mymapdemo.API.WebViewAPI.maintain_url;
import static com.qiming.wcq.mymapdemo.constants.Constant.MIANTAINID;
import static com.qiming.wcq.mymapdemo.constants.Constant.UPDATE_ADDRESS;

/**
 * Created by Administrator on 2017/1/24.
 */

public class UpdateAppCallBack extends StringCallback {

    private final String localversionname;
    private final int localversioncode;
    private final Context context;
    private ProgressDialog progressDialog;
    private ProgressBar pb;
    private TextView mTvPercent;
    private TextView mTvTotal;

    public UpdateAppCallBack(Context context, String localversionname, int localversioncode) {
        this.localversionname = localversionname;
        this.localversioncode = localversioncode;
        this.context = context;
    }


    @Override
    public void onError(Call call, Exception e, int id) {


    }

    @Override
    public void onResponse(String response, int id) {
        //解析数据
        if (response != null) {
            Gson gson = new Gson();
            UpdateEntity updateEntity = gson.fromJson(response, UpdateEntity.class);
            int httpversionCode = Integer.valueOf(updateEntity.getVersionCode());
            String versionName = updateEntity.getVersionName();
            final String versonAddress = updateEntity.getVersonAddress();
            if (httpversionCode > localversioncode) {
                //跳转到更新app的界面
                Intent intent = new Intent(context, UpdateAppActivity.class);
                intent.putExtra(UPDATE_ADDRESS,versonAddress);
                context.startActivity(intent);
            }else {
                //是否有维护
                serverMainTain();
            }
        }

    }


    private void serverMainTain() {
        //查看服务器是否有维护
        OkHttpUtils.get()
                .id(MIANTAINID)
                .url(maintain_url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        MaintainEntity maintainEntity = gson.fromJson(response, MaintainEntity.class);
                        String mainTainType = maintainEntity.getMainTainType();
                        if (Constant.MIANTAIN_TYPE.equals(mainTainType)) {
                            //跳转到维护界面
                            Intent intent = new Intent(context, MainTainActivity.class);
                            context.startActivity(intent);
                        }
                    }
                });
    }


    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     *
     * @param bytes
     * @return
     */
    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1)
            return (returnValue + "MB");
//            return  returnValue;
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB");
//        return returnValue;
    }


}
