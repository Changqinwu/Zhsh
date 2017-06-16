package com.qiming.wcq.mymapdemo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.qiming.wcq.mymapdemo.Fragment.EnterFragment;
import com.qiming.wcq.mymapdemo.Fragment.HomeFragment;
import com.qiming.wcq.mymapdemo.Fragment.MemberCenterFragment;
import com.qiming.wcq.mymapdemo.Fragment.StoreFragment;
import com.qiming.wcq.mymapdemo.R;

import java.util.ArrayList;

public class MainTabActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout mRlsy;
    private RelativeLayout mRlrz;
    private RelativeLayout mRlsd;
    private RelativeLayout mRlwd;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private FragmentTransaction fragmentTransaction;
    private HomeFragment homeFragment;
    private EnterFragment enterFragment;
    private StoreFragment storeFragment;
    private MemberCenterFragment centerFragment;
    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        initView();
    }

    private void initView() {
        mRlsy = (RelativeLayout) findViewById(R.id.rl_sy);
        mRlrz = (RelativeLayout) findViewById(R.id.rl_rz);
        mRlsd = (RelativeLayout) findViewById(R.id.rl_sd);
        mRlwd = (RelativeLayout) findViewById(R.id.rl_wd);

        mRlsy.setOnClickListener(this);
        mRlrz.setOnClickListener(this);
        mRlsd.setOnClickListener(this);
        mRlwd.setOnClickListener(this);

        //底部选中状态初始化，都设为false,第一个tab设置为true
        tabSelected();
        mRlsy.setSelected(true);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        hideFragment(homeFragment);

    }

    private void tabSelected() {
        mRlsy.setSelected(false);
        mRlrz.setSelected(false);
        mRlsd.setSelected(false);
        mRlwd.setSelected(false);
    }

    private void hideFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //隐藏当前的显示的Fragment
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        //fragment没有被添加过就add，否则就是直接show
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.home_container, fragment);
        }else {
            fragmentTransaction.show(fragment);
        }
        //别忘记提交
        fragmentTransaction.commit();
        currentFragment = fragment;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_sy:
                tabSelected();
                mRlsy.setSelected(true);

                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                hideFragment(homeFragment);
                break;
            case R.id.rl_rz:
                tabSelected();
                mRlrz.setSelected(true);
//
//                if (enterFragment == null) {
//                    enterFragment = new EnterFragment();
//                }
//                hideFragment(enterFragment);
                break;
            case R.id.rl_sd:
                tabSelected();
                mRlsd.setSelected(true);
//
//                if (centerFragment == null) {
//                    centerFragment = new MemberCenterFragment();
//                }
//                hideFragment(centerFragment);
                break;
            case R.id.rl_wd:
                tabSelected();
                mRlwd.setSelected(true);
//
//                if (storeFragment == null) {
//                    storeFragment = new StoreFragment();
//                }
//                hideFragment(storeFragment);
                break;

            default:
                break;
        }
    }


}
