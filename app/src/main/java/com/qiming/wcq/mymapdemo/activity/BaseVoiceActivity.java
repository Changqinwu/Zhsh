package com.qiming.wcq.mymapdemo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.qiming.wcq.mymapdemo.R;

public class BaseVoiceActivity extends AppCompatActivity {

    // 引擎类型
    private String mEngineType;
    // 语音合成对象
    public SpeechSynthesizer mTts;
    private String TAG = "CouponActivity";

    private String[] mCloudVoicersEntries;
    private String[] mCloudVoicersValue;

    // 默认发音人
    private String voicer = "xiaoyan";
    private int selectedNum = 0;
    public boolean isFirst;
    public boolean isPlaying;

    private ProgressDialog progDialog = null;// 搜索时进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取屏幕宽度
        getScreenProperty();
        // 初始化合成对象
        initVoice();
    }

    private void initVoice() {
        //在线合成模式
        mEngineType = SpeechConstant.TYPE_CLOUD;
        mTts = SpeechSynthesizer.createSynthesizer(BaseVoiceActivity.this, mTtsInitListener);
        // 云端发音人名称列表
        mCloudVoicersEntries = getResources().getStringArray(R.array.voicer_cloud_entries);
        mCloudVoicersValue = getResources().getStringArray(R.array.voicer_cloud_values);


    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(BaseVoiceActivity.this, "初始化失败,错误码：" + code, Toast.LENGTH_SHORT).show();
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
                // 移动数据分析，收集开始合成事件

            }
        }
    };

    /**
     * 参数设置
     *
     * @param
     * @return
     */
    public void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "50");
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mTts.stopSpeaking();
//        // 退出时释放连接
//        mTts.destroy();
    }

    @Override
    protected void onResume() {
        //移动数据统计分析
//        FlowerCollector.onResume(BaseVoiceActivity.this);
//        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        //移动数据统计分析
//        FlowerCollector.onPageEnd(TAG);
//        FlowerCollector.onPause(BaseVoiceActivity.this);
        super.onPause();
    }


//    /**
//     * 显示进度框
//     */
//    public void showProgressDialog() {
//        if (progDialog == null)
//            progDialog = new ProgressDialog(this);
//        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progDialog.setIndeterminate(false);
//        progDialog.setCancelable(true);
//        progDialog.setMessage("正在搜索");
//        progDialog.show();
//    }

    /**
     * 隐藏进度框
     */
    public void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    private void getScreenProperty() {
        String release = Build.VERSION.RELEASE;

        WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;
        float density = dm.density;
        int densityDpi = dm.densityDpi;

        int screenWidth = (int) (widthPixels / density);
        int screenHeight = (int) (heightPixels / density);

        Log.e("屏幕的属性", "宽" + screenWidth + ">>>>高" + screenHeight);
        Log.e("android版本", "版本号" + release);

        if (screenHeight > 600) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

}
