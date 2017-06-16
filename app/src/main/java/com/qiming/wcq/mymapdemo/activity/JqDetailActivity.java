package com.qiming.wcq.mymapdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.google.gson.Gson;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;
import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.adapter.JdDetailImageHolderView;
import com.qiming.wcq.mymapdemo.adapter.NetworkImageHolderView;
import com.qiming.wcq.mymapdemo.constants.Constant;
import com.qiming.wcq.mymapdemo.constants.HomeData;
import com.qiming.wcq.mymapdemo.entity.JdDetailEntity;
import com.qiming.wcq.mymapdemo.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.qiming.wcq.mymapdemo.API.WebViewAPI.jd_detail_list_url;


public class JqDetailActivity extends BaseVoiceActivity implements View.OnClickListener {
    //返回
    private ImageView mBack;
    //显示详情
    private TextView mTvDetail;
    //显示进度条
    private ProgressBar mPb;
    //显示标题
    private TextView mTitle;
    //播放状态图片
    private ImageView mImaVoice;
    private String jq_detail;
    //轮播图
    private ConvenientBanner mBanner;
    private ArrayList<JdDetailEntity.JdDetailBean> imaData = new ArrayList<>();
    private int widthPixels;
    private int heightPixels;
    private int lp_ad;
    public boolean isFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jq_detail);
        getScreenProperty();

        //初始化view
        initView();


        //获取景区详情
        Intent intent = getIntent();
        jq_detail = intent.getStringExtra("jq_detail");
        String sni_text = intent.getStringExtra("sni_text");
        mTvDetail.setText(jq_detail);
        mTitle.setText(sni_text);

        //设置语音参数
        setImaDetail();
        //轮播图
        initData();

    }

    private void initData() {
//        imaData = HomeData.localSoundPic();
        OkHttpUtils.get()
                .url(jd_detail_list_url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.show(JqDetailActivity.this, "错误：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        JdDetailEntity jdDetailEntity = gson.fromJson(response, JdDetailEntity.class);
                        List<JdDetailEntity.JdDetailBean> jdDetail = jdDetailEntity.getJdDetail();
                        imaData.addAll(jdDetail);
                        //设置轮播图
                        setBanner();
                    }
                });
    }

    private void setImaDetail() {
        // 设置参数
        setParam();
        mTts.startSpeaking(jq_detail, mTtsListener);
        isPlaying = true;
        isFinish = false;
        mImaVoice.setImageResource(R.mipmap.icon_autopasu);

    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.ima_back);
        mTvDetail = (TextView) findViewById(R.id.tv_jq_detail);
        mPb = (ProgressBar) findViewById(R.id.pb);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mImaVoice = (ImageView) findViewById(R.id.ima_voice);
        mBanner = (ConvenientBanner) findViewById(R.id.banner);
        //将字体文件保存在assets/fonts/目录下，创建Typeface对象

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/hyxls.ttf");

        //使用字体
        mTvDetail.setTypeface(typeFace);
        mBack.setOnClickListener(this);
        mImaVoice.setOnClickListener(this);
        //轮播图高度
        lp_ad = heightPixels / 3;

        //轮播高度设置
        ViewGroup.LayoutParams lp = mBanner.getLayoutParams();
        lp.height = lp_ad;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ima_back:
                Intent intent = new Intent();
                intent.putExtra("isPlay", isPlaying);
                setResult(0, intent);
                finish();
                break;

            case R.id.ima_voice:
                if (isPlaying) {
                    mImaVoice.setImageResource(R.mipmap.icon_autoplay);
                    mTts.pauseSpeaking();
                    isPlaying = false;

                } else {
                    mImaVoice.setImageResource(R.mipmap.icon_autopasu);
                    mTts.resumeSpeaking();
                    isPlaying = true;
                }
                //播放完成，点击重新播放
                if (isFinish) {
                    setImaDetail();
                }

                break;


        }
    }


    private void setBanner() {
        mBanner.setPages(new CBViewHolderCreator<JdDetailImageHolderView>() {
            @Override
            public JdDetailImageHolderView createHolder() {
                return new JdDetailImageHolderView(imaData, getApplicationContext());
            }
        }, imaData);

        //设置需要切换的View
        mBanner.setPointViewVisible(true)    //设置指示器是否可见
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused}) //设置指示器圆点
                .startTurning(5000);//设置自动切换（同时设置了切换时间间隔）
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBanner.stopTurning();
    }

    /**
     * 语音合成回调监听。
     */
    public SynthesizerListener mTtsListener = new SynthesizerListener() {



        @Override
        public void onSpeakBegin() {
            Toast.makeText(JqDetailActivity.this, "开始播放", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSpeakPaused() {
            Toast.makeText(JqDetailActivity.this, "暂停播放", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSpeakResumed() {
            Toast.makeText(JqDetailActivity.this, "继续播放", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
//            // 合成进度
//            mPercentForBuffering = percent;
//            showTip(String.format(getString(R.string.tts_toast_format),
//                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            mPb.setProgress(percent);
            Log.e("开始", ">>>>>" + beginPos);
            Log.e("结束", ">>>>>" + endPos);

        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
//                showTip("播放完成");
                Toast.makeText(JqDetailActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
                isFirst = false;
                mImaVoice.setImageResource(R.mipmap.icon_autoplay);
                isFinish = true;
                Intent intent = new Intent(Constant.PAUSE_ACTION);
                intent.putExtra("isFinish", isFinish);
                sendBroadcast(intent);
            } else if (error != null) {
//                showTip(error.getPlainDescription(true));
                Toast.makeText(JqDetailActivity.this, "error.getPlainDescription(true)", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("isPlay", isPlaying);
            setResult(0, intent);
            finish();
            return true;
        }
        return false;
    }

    private void getScreenProperty() {
        String release = Build.VERSION.RELEASE;

        WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        widthPixels = dm.widthPixels;
        heightPixels = dm.heightPixels;
        float density = dm.density;
        int densityDpi = dm.densityDpi;

        int screenWidth = (int) (widthPixels / density);
        int screenHeight = (int) (heightPixels / density);

//        ToastUtil.show(this,"widthPixels:"+widthPixels+">>>>"+"heightPixels:"+heightPixels);
//        ToastUtil.show(this,"screenWidth"+screenWidth+">>>>"+"screenHeight"+screenHeight);

    }
}
