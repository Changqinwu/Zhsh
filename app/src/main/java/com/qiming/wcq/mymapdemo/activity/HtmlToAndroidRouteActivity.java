package com.qiming.wcq.mymapdemo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.route.DrivingRouteOverLay;
import com.qiming.wcq.mymapdemo.route.WalkRouteOverlay;
import com.qiming.wcq.mymapdemo.util.AMapUtil;
import com.qiming.wcq.mymapdemo.util.ProgressDialogUtil;
import com.qiming.wcq.mymapdemo.util.SensorEventHelper;
import com.qiming.wcq.mymapdemo.util.ToastUtil;

public class HtmlToAndroidRouteActivity extends BaseActivity implements LocationSource, RouteSearch.OnRouteSearchListener, AMapLocationListener, View.OnClickListener {

    public static final String LOCATION_MARKER_FLAG = "mylocation";
    private MapView mapView;
    private AMap aMap;
    private SensorEventHelper mSensorHelper;
    private RouteSearch mRouteSearch;
    private boolean mFirstFix;
    private AMapLocationClient mlocationClient;
    private OnLocationChangedListener mListener;
    private AMapLocationClientOption mLocationOption;
    private Marker mLocMarker;
    private LatLonPoint startPoint;
    private LatLonPoint endPoint;
    private ProgressDialog progDialog;
    //驾车路线
    private final int ROUTE_TYPE_DRIVE = 2;
    //步行
    private final int ROUTE_TYPE_WALK = 3;
    private DriveRouteResult mDriveRouteResult;
    private DrivingRouteOverLay drivingRouteOverlay;
    private WalkRouteResult mWalkRouteResult;
    private double startLatitude;
    private double startlongitude;
    private Double endLatitude;
    private Double endLongitude;
    private boolean iswalk;
    private ImageView mIaBack;
    //开始导航按钮
    private Button mBtnStartGps;
    private Button mBtnCancleGps;
    private LinearLayout mLinBtnGps;
    private ProgressDialogUtil dialogUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_to_android_route);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        //得到h5传过来的终点坐标
        getEndLatLng();
        //初始化地图
        init();

    }

    private void getEndLatLng() {
        Intent intent = getIntent();
        String param = intent.getStringExtra("param");
        String[] split = param.split(",");
        endLatitude = Double.valueOf(split[0]);
        endLongitude = Double.valueOf(split[1]);
    }


    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        mSensorHelper = new SensorEventHelper(this);
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }

        //线路规划初始化
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);

        TextView mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("线路规划");
        mIaBack = (ImageView) findViewById(R.id.ima_back);
        mBtnStartGps = (Button) findViewById(R.id.btnStartGps);
        mBtnCancleGps = (Button) findViewById(R.id.btnCancleGps);
        mLinBtnGps = (LinearLayout) findViewById(R.id.re_btn_gps);


        mIaBack.setOnClickListener(this);
        mBtnStartGps.setOnClickListener(this);
    }


    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
//        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        aMap.getUiSettings().setZoomControlsEnabled(false);//隐藏缩放大小图标
        aMap.getUiSettings().setLogoBottomMargin(-50);//隐藏logo


    }


    //我的位置Marker
    private void addMarker(LatLng latlng) {
        if (mLocMarker != null) {
            aMap.clear();
            Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
                    R.mipmap.navi_map_gps_locked);
            BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);

//		BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
            MarkerOptions options = new MarkerOptions();
            options.icon(des);
            options.anchor(0.5f, 0.5f);
            options.position(latlng);
            mLocMarker = aMap.addMarker(options);
            mLocMarker.setTitle(LOCATION_MARKER_FLAG);
            return;
        }
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.navi_map_gps_locked);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);

//		BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        mLocMarker = aMap.addMarker(options);
        mLocMarker.setTitle(LOCATION_MARKER_FLAG);
    }


    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (startPoint == null) {
            ToastUtil.show(this, "定位中，稍后再试...");
            return;
        }
        if (endPoint == null) {
            ToastUtil.show(this, "终点未设置");
        }
        dialogUtil = new ProgressDialogUtil();
        dialogUtil.showProgressDialog(this,"请稍后...");
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                startPoint, endPoint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }

        if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, mode);
            mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }
    }

    private void showRoute(Double startLatitude, Double startlongitude, Double endLatitude, Double endLongitude) {
        //计算两个坐标点距离
        LatLng mStartLatlng = new LatLng(startLatitude, startlongitude);
        LatLng mEndLatlng = new LatLng(endLatitude, endLongitude);
        startPoint = new LatLonPoint(startLatitude, startlongitude);
        endPoint = new LatLonPoint(endLatitude, endLongitude);
        setfromandtoMarker(startPoint, endPoint);
        float distance = AMapUtils.calculateLineDistance(mStartLatlng, mEndLatlng);
        if (distance < 500) {
            searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WalkDefault);
            iswalk = true;
        } else {
            searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);
            iswalk = false;
        }
    }


    /*
    *
    * 设置路线起终点的标记
    * */
    private void setfromandtoMarker(LatLonPoint startpoint, LatLonPoint endpoint) {

        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(startpoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.start)));
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(endpoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.end)));

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
//
//    /**
//     * 隐藏进度框
//     */
//    public void dissmissProgressDialog() {
//        if (progDialog != null) {
//            progDialog.dismiss();
//        }
//    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }

    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
            mSensorHelper.setCurrentMarker(null);
            mSensorHelper = null;
        }
        mapView.onPause();
        deactivate();
        mFirstFix = false;
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }

    }


    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mFirstFix = false;
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }


    /**
     * 停止定位
     */

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    drivingRouteOverlay = new DrivingRouteOverLay(
                            this, aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    mLinBtnGps.setVisibility(View.VISIBLE);
                    mBtnCancleGps.setVisibility(View.GONE);
                    dialogUtil.dismissProgressDialog();
                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.show(this, R.string.no_result);
                }

            } else {
                ToastUtil.show(this, R.string.no_result);
            }
        } else {
            ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mWalkRouteResult = result;
                    final WalkPath walkPath = mWalkRouteResult.getPaths()
                            .get(0);
                    WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(
                            this, aMap, walkPath,
                            mWalkRouteResult.getStartPos(),
                            mWalkRouteResult.getTargetPos());
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                    mLinBtnGps.setVisibility(View.VISIBLE);
                    mBtnCancleGps.setVisibility(View.GONE);
                    dialogUtil.dismissProgressDialog();
                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.show(HtmlToAndroidRouteActivity.this, R.string.no_result);
                }
            } else {
                ToastUtil.show(HtmlToAndroidRouteActivity.this, R.string.no_result);
            }
        } else {
            ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                startLatitude = amapLocation.getLatitude();
                startlongitude = amapLocation.getLongitude();
                LatLng location = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                if (!mFirstFix) {
                    mFirstFix = true;
//					addCircle(location, amapLocation.getAccuracy());//添加定位精度圆
                    addMarker(location);//添加定位图标
                    mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
                    showRoute(startLatitude, startlongitude, endLatitude, endLongitude);
                }

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);

            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ima_back:
                finish();
                break;
            case R.id.btnStartGps:
                if (startLatitude >= 0 && startlongitude >= 0 && endLatitude >= 0 && endLongitude >= 0) {
                    Intent intent = new Intent(HtmlToAndroidRouteActivity.this, GPSGpsActivity.class);
                    intent.putExtra("latitude", startLatitude + "");
                    intent.putExtra("longitude", startlongitude + "");
                    intent.putExtra("endLatitude", endLatitude + "");
                    intent.putExtra("endLongitude", endLongitude + "");
                    intent.putExtra("walk", iswalk);
                    startActivity(intent);
                } else {
                    ToastUtil.show(HtmlToAndroidRouteActivity.this, "请求位置出现错误...");
                }

                break;
        }
    }
}
