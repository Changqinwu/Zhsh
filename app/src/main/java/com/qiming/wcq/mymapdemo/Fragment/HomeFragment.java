package com.qiming.wcq.mymapdemo.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andview.refreshview.XRefreshView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qiming.wcq.mymapdemo.API.WebViewAPI;
import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.activity.CouponActivity;
import com.qiming.wcq.mymapdemo.activity.HomeNavDetailActivity;
import com.qiming.wcq.mymapdemo.activity.LocationMarkerActivity;
import com.qiming.wcq.mymapdemo.activity.NearParkingActivity;
import com.qiming.wcq.mymapdemo.adapter.GridAdapter;
import com.qiming.wcq.mymapdemo.adapter.HomeRvAdapter;
import com.qiming.wcq.mymapdemo.adapter.NetworkImageHolderView;
import com.qiming.wcq.mymapdemo.constants.Constant;
import com.qiming.wcq.mymapdemo.customview.RippleView;
import com.qiming.wcq.mymapdemo.entity.AdImageEntity;
import com.qiming.wcq.mymapdemo.entity.HomeCouponListEntity;
import com.qiming.wcq.mymapdemo.entity.HomeIconListEntity;
import com.qiming.wcq.mymapdemo.entity.HomeListEntity;
import com.qiming.wcq.mymapdemo.util.ExitAppUtil;
import com.qiming.wcq.mymapdemo.util.ProgressDialogUtil;
import com.qiming.wcq.mymapdemo.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

import static com.qiming.wcq.mymapdemo.constants.Constant.ADIMAGEID;
import static com.qiming.wcq.mymapdemo.constants.Constant.HOME_COUPON_ID;
import static com.qiming.wcq.mymapdemo.constants.Constant.HOME_ICON_ID;
import static com.qiming.wcq.mymapdemo.constants.Constant.HOME_LIST_ID;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, RippleView.OnRippleCompleteListener {

    private View view;
    //使用第三方库XRefreshView
    private XRefreshView mXRefreshView;
    private RecyclerView xRv;
    private LinearLayoutManager linManager;
    private HomeRvAdapter homeRvAdapter;
    //轮播广告
    private ConvenientBanner convenientBanner;
    private Intent intent;
    //8个图标用gridview布局
    private RecyclerView homegridView;
    //轮播图片
    private ArrayList<AdImageEntity.AdpicturesBean> localImages = new ArrayList<>();
    //图标
    private ArrayList<HomeIconListEntity.IconlistBean> homeIconDatas = new ArrayList<>();
    //列表
    private List<HomeListEntity.HomelistBean> homeListDatas = new ArrayList<>();
    //优惠券
    private ArrayList<HomeCouponListEntity.CouponlistBean> couponPicDatas = new ArrayList<>();
    //右下角退出app
    private ImageView mImaExit;
    private long exitTime;
    private int lp_ad;
    private int widthPixels;
    private int heightPixels;
    private int lp_coupon;
    private Handler handle = new Handler();
    private ImageView mImaMp;
    private ImageView mImaYhq;
    private ImageView mImaFsmq;
    private ProgressDialogUtil progressDialogUtil;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home1, container, false);
            //获取屏幕属性
            getScreenProperty();
            //初始化View
            initView();
            //初始化数据
            initDatas();
        }
        return view;
    }

    private void initDatas() {

        progressDialogUtil = new ProgressDialogUtil();
        progressDialogUtil.showProgressDialog(getActivity(),"正在加载数据...");
        //首页列表
        showListDatas(Constant.HOME_LIST_ID, WebViewAPI.home_list_url);

        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                //轮播图5分钟请求一次
                showListDatas(ADIMAGEID, WebViewAPI.ad_image_url);
            }
        }, 300000);
    }

    private void showListDatas(int ID, String Url) {
        //弹窗
        OkHttpUtils.get()
                .id(ID)
                .url(Url)
                .build()
                .execute(new HomeCallBack());
    }


    private void initView() {
        mXRefreshView = (XRefreshView) view.findViewById(R.id.xrefreshview);
        xRv = (RecyclerView) view.findViewById(R.id.rv);
        mImaExit = (ImageView) view.findViewById(R.id.ima_exit_app);
        linManager = new LinearLayoutManager(getActivity());

        //mXRefreshViewy一些设置
        mXRefreshView.setPullLoadEnable(false);
        mXRefreshView.setMoveForHorizontal(true);
        mXRefreshView.setPullRefreshEnable(false);
        xRv.setLayoutManager(linManager);
        mImaExit.setOnClickListener(this);
    }

    private void setHomeRvAdapter(List<HomeListEntity.HomelistBean> homelist) {
        homeRvAdapter = new HomeRvAdapter(getActivity(), homelist);
        //创建头部布局
        View view = creatHeadView();
        //设置头部界面
        homeRvAdapter.setHeaderView(view, xRv);
        xRv.setAdapter(homeRvAdapter);
        //列表点击跳转
        homeRvAdapter.setItemClickListener(new HomeRvAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != 0) {
                    if (position == 1) {
                        intent = new Intent(getActivity(), LocationMarkerActivity.class);
                        startActivity(intent);
                    } else {
                        intent = new Intent(getActivity(), HomeNavDetailActivity.class);
                        intent.putExtra("param", homeListDatas.get(position - 1).getItem_type());
                        intent.putExtra("title", homeListDatas.get(position - 1).getItem_title());
                        startActivity(intent);
                    }
                }
            }
        });


    }

    private View creatHeadView() {
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.home_rv_headview_layout, null);
        convenientBanner = (ConvenientBanner) headView.findViewById(R.id.convenientBanner);
        homegridView = (RecyclerView) headView.findViewById(R.id.home_gridview);
        RippleView mRlJqmp = (RippleView) headView.findViewById(R.id.re_jqmp);
        RippleView mRlYhq = (RippleView) headView.findViewById(R.id.re_yhq);
        RippleView mRlFsmq = (RippleView) headView.findViewById(R.id.re_fsmq);
        LinearLayout mLinCoupon = (LinearLayout) headView.findViewById(R.id.lin_coupon);
        mImaMp = (ImageView) headView.findViewById(R.id.ima_coupon_mp);
        mImaYhq = (ImageView) headView.findViewById(R.id.ima_coupon_yhq);
        mImaFsmq = (ImageView) headView.findViewById(R.id.ima_coupon_fsmq);

        mRlJqmp.setOnRippleCompleteListener(this);
        mRlYhq.setOnRippleCompleteListener(this);
        mRlFsmq.setOnRippleCompleteListener(this);

        //轮播图高度
        lp_ad = heightPixels / 3;
        ////轮播图下面三个布局高度
        lp_coupon = (int) (heightPixels / 7.5);

        //轮播高度设置
        ViewGroup.LayoutParams lp = convenientBanner.getLayoutParams();
        lp.height = lp_ad;
        //轮播图下面三个布局高度设置
        ViewGroup.LayoutParams lp_lin = mLinCoupon.getLayoutParams();
        lp_lin.height = lp_coupon;

        //轮播图
        showListDatas(HOME_ICON_ID, WebViewAPI.home_icon_url);
        //优惠券
        showListDatas(HOME_COUPON_ID, WebViewAPI.home_coupon_url);
        //图标
        showListDatas(ADIMAGEID, WebViewAPI.ad_image_url);

        return headView;
    }

    private void setBanner(ConvenientBanner convenientBanner) {
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView(localImages, getActivity());
            }
        }, localImages);

        //设置需要切换的View
        convenientBanner.setPointViewVisible(true)    //设置指示器是否可见
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused}); //设置指示器圆点
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        convenientBanner.startTurning(5000);//设置自动切换（同时设置了切换时间间隔）
//        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                intent = new Intent(getActivity(), HomeNavDetailActivity.class);
//                intent.putExtra("param", Constant.SC);
//                intent.putExtra("type", Constant.SC_TYPE);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (convenientBanner != null) {
            convenientBanner.startTurning(5000);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //退出app
            case R.id.ima_exit_app:
                if ((System.currentTimeMillis() - exitTime) > 500) {
                    exitTime = System.currentTimeMillis();
                } else {
                    new ExitAppUtil(getActivity()).dialogExit();
                }
                break;
        }
    }

    private void getScreenProperty() {
        String release = Build.VERSION.RELEASE;

        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        widthPixels = dm.widthPixels;
        heightPixels = dm.heightPixels;
        float density = dm.density;
        int densityDpi = dm.densityDpi;

        int screenWidth = (int) (widthPixels / density);
        int screenHeight = (int) (heightPixels / density);

//        ToastUtil.show(getActivity(),"widthPixels:"+widthPixels+">>>>"+"heightPixels:"+heightPixels);
//        ToastUtil.show(getActivity(),"screenWidth"+screenWidth+">>>>"+"screenHeight"+screenHeight);

    }

    @Override
    public void onComplete(RippleView rippleView) {
        switch (rippleView.getId()) {
            //景区门票
            case R.id.re_jqmp:
                intent = new Intent(getActivity(), CouponActivity.class);
                intent.putExtra("param", Constant.JQMP);
                startActivity(intent);
                break;
            //优惠券
            case R.id.re_yhq:
                intent = new Intent(getActivity(), CouponActivity.class);
                intent.putExtra("param", Constant.YHQ);
                startActivity(intent);
                break;
            //风俗民情
            case R.id.re_fsmq:
                intent = new Intent(getActivity(), CouponActivity.class);
                intent.putExtra("param", Constant.FSMQ);
                startActivity(intent);
                break;
        }
    }

    //回调
    class HomeCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            ToastUtil.show(getActivity(), "id:" + id + "错误：" + e.getMessage());
        }



        @Override
        public void onResponse(String response, int id) {
            if (response != null) {
                //关闭弹窗
                progressDialogUtil.dismissProgressDialog();
                Gson gson = new Gson();
                if (ADIMAGEID == id) {
                    //轮播图
                    AdImageEntity adImageEntity = gson.fromJson(response, AdImageEntity.class);
                    List<AdImageEntity.AdpicturesBean> adpictures = adImageEntity.getAdpictures();
                    localImages.addAll(adpictures);
                    //设置轮播图
                    setBanner(convenientBanner);
                } else if (HOME_COUPON_ID == id) {
                    //优惠券
                    HomeCouponListEntity homeCouponListEntity = gson.fromJson(response, HomeCouponListEntity.class);
                    List<HomeCouponListEntity.CouponlistBean> couponlist = homeCouponListEntity.getCouponlist();
                    couponPicDatas.addAll(couponlist);
                    Glide.with(getActivity()).load(couponPicDatas.get(0).getImage_url()).into(mImaMp);
                    Glide.with(getActivity()).load(couponPicDatas.get(1).getImage_url()).into(mImaYhq);
                    Glide.with(getActivity()).load(couponPicDatas.get(2).getImage_url()).into(mImaFsmq);

                } else if (HOME_ICON_ID == id) {
                    //图标
                    HomeIconListEntity homeIconListEntity = gson.fromJson(response, HomeIconListEntity.class);
                    List<HomeIconListEntity.IconlistBean> iconlist = homeIconListEntity.getIconlist();
                    homeIconDatas.addAll(iconlist);
                    //图标适配器
                    GridAdapter gridAdapter = new GridAdapter(getActivity(), homeIconDatas);
                    homegridView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                    homegridView.setAdapter(gridAdapter);

                    //图标点击
                    gridAdapter.setItemClickListener(new GridAdapter.MyItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {
                            if (position == 3) {
                                intent = new Intent(getActivity(), NearParkingActivity.class);
                                startActivity(intent);
                            } else {
                                intent = new Intent(getActivity(), HomeNavDetailActivity.class);
                                intent.putExtra("param", homeIconDatas.get(position).getIcon_type());
                                intent.putExtra("title", homeIconDatas.get(position).getIcon_name());
                                startActivity(intent);
                            }
                        }
                    });
                } else if (HOME_LIST_ID == id) {
                    //列表
                    HomeListEntity homeListEntity = gson.fromJson(response, HomeListEntity.class);
                    List<HomeListEntity.HomelistBean> homelist = homeListEntity.getHomelist();
                    homeListDatas.addAll(homelist);
                    //列表适配器
                    setHomeRvAdapter(homelist);
                }

            } else {
                ToastUtil.show(getActivity(), "错误：" + "数据异常...");
            }
        }

    }

}
