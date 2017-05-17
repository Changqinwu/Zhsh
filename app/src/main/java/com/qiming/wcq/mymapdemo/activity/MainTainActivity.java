package com.qiming.wcq.mymapdemo.activity;

        import android.os.Bundle;
        import android.os.Handler;

        import com.qiming.wcq.mymapdemo.R;


public class MainTainActivity extends BaseActivity {

    private Handler handle = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tain);

        //重新连接
        reConnect();
    }

    private void reConnect() {
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                //更新qpp
                updateApp();
            }
        },300000);
    }
}
