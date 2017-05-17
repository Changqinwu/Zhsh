package com.qiming.wcq.mymapdemo.activity;

import android.content.Intent;
import android.os.Bundle;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.qiming.wcq.mymapdemo.R;

public class GPSGpsActivity extends BaseGpsActivity {

    private String startLatitude;
    private String startlongitude;
    private String endLatitude;
    private String endLongitude;
    private NaviLatLng mStartNaviLatlng;
    private NaviLatLng mEndNaviLatlng;
    private boolean iswalk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            startLatitude = intent.getStringExtra("latitude");
            startlongitude = intent.getStringExtra("longitude");
            endLatitude = intent.getStringExtra("endLatitude");
            endLongitude = intent.getStringExtra("endLongitude");
            iswalk = intent.getBooleanExtra("walk", false);

            mStartNaviLatlng = new NaviLatLng(Double.valueOf(startLatitude), Double.valueOf(startlongitude));
            mEndNaviLatlng = new NaviLatLng(Double.valueOf(endLatitude), Double.valueOf(endLongitude));

            sList.add(mStartNaviLatlng);
            eList.add(mEndNaviLatlng);
        }

    }


    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, true, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (iswalk) {
            //步行导航
            mAMapNavi.calculateWalkRoute(mStartNaviLatlng, mEndNaviLatlng);
        } else {
            //车行导航
            mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);
        }

    }

    @Override
    public void onCalculateRouteSuccess() {
        super.onCalculateRouteSuccess();
        mAMapNavi.startNavi(NaviType.GPS);
    }
}
