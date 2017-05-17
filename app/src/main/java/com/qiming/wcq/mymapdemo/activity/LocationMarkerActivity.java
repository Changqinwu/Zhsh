package com.qiming.wcq.mymapdemo.activity;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;

import com.amap.api.maps.model.GroundOverlayOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;
import com.qiming.wcq.mymapdemo.R;

import com.qiming.wcq.mymapdemo.constants.Constant;
import com.qiming.wcq.mymapdemo.constants.FoodLatLng;
import com.qiming.wcq.mymapdemo.constants.HotelLatLng;
import com.qiming.wcq.mymapdemo.constants.JingQuLatLng;
import com.qiming.wcq.mymapdemo.constants.ParkLatLng;
import com.qiming.wcq.mymapdemo.constants.TolietLatLng;
import com.qiming.wcq.mymapdemo.route.DrivingRouteOverLay;
import com.qiming.wcq.mymapdemo.route.WalkRouteOverlay;
import com.qiming.wcq.mymapdemo.util.AMapUtil;
import com.qiming.wcq.mymapdemo.util.ProgressDialogUtil;
import com.qiming.wcq.mymapdemo.util.SensorEventHelper;
import com.qiming.wcq.mymapdemo.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * AMapV2地图中介绍自定义可旋转的定位图标
 */
public class LocationMarkerActivity extends BaseVoiceActivity implements LocationSource, View.OnClickListener,
        AMapLocationListener, AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, RouteSearch.OnRouteSearchListener {
    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private TextView mLocationErrText;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private boolean mFirstFix = false;
    private Marker mLocMarker;
    private SensorEventHelper mSensorHelper;
    private Circle mCircle;
    public static final String LOCATION_MARKER_FLAG = "mylocation";
    private MarkerOptions markerOption;
    //景区覆盖物需要三个坐标点定位
    private LatLng latlng = new LatLng(26.085332, 119.296539);
    private LatLng latlng1 = new LatLng(26.085823, 119.301135);
    private LatLng latlng2 = new LatLng(26.077601, 119.293872);

    //导航用到标记点坐标
    private double endLatitude;
    private double endLongitude;
    //我的位置坐标
    private double startLatitude;
    private double startlongitude;
    //    private ArrayList<LatLng> dataInfos = new ArrayList<>();
    private ArrayList<MarkerOptions> markerInfos = new ArrayList<>();
    //    private ArrayList<String> titles = new ArrayList<>();
    private boolean isLocation;
    private ImageView mPlayState;
    //驾车路线
    private final int ROUTE_TYPE_DRIVE = 2;
    //步行
    private final int ROUTE_TYPE_WALK = 3;
    //路线起点
    public LatLonPoint startPoint;
    //路线终点
    public LatLonPoint endPoint;
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    //返回
    private ImageView mBack;
    //标题
    private TextView mTitle;
    //驾车线路物覆盖
    private DrivingRouteOverLay drivingRouteOverlay;
    //开始导航布局
    private LinearLayout mLinGps;
    //是否是路线规划
    private boolean isRoute;
    private ArrayList<String> titles;
    private ArrayList<LatLng> dataInfos;
    //服务内容
    private LinearLayout mLinFw;
    //其他内容
    private LinearLayout mLinQt;
    //酒店
    private TextView mTvHotel;
    //餐饮
    private TextView mTvFood;
    //商店
    private TextView mTvStore;

    //停车场
    private TextView mTvPark;
    //服务中心
    private TextView mTvFwCenter;
    //判断底部服务布局显隐
    private boolean isClick;
    //判断底部其他布局显隐
    private boolean isQtClick;
    //判断线路规划时对应景区maker显示
    private String MarkerFlag;
    private Polygon polygon;
    //判断是否在景区内
    private boolean contains;
    private WalkRouteResult mWalkRouteResult;
    protected List<Polyline> allPolyLines = new ArrayList<Polyline>();
    //跳转导航页判断是否步行
    private boolean iswalk;
    //路线弹窗
    private ProgressDialogUtil dialogUtil;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.locationmodesource_activity);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        //初始化地图
        init();
        //初始化界面
        initView();



    }


    private void initData() {
        //景区坐标
        JingQuLatLng mJqLatlng = new JingQuLatLng();
        dataInfos = mJqLatlng.setJqLatLng();
        titles = mJqLatlng.setTitles();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.ima_back);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mTitle.setText("三坊七巷");
        RadioButton mRaJq = (RadioButton) findViewById(R.id.ra_jq);
        RadioButton mRaFw = (RadioButton) findViewById(R.id.ra_fw);
        RadioButton mRaCs = (RadioButton) findViewById(R.id.ra_cs);
        RadioButton mRaQt = (RadioButton) findViewById(R.id.ra_qt);
        mLinGps = (LinearLayout) findViewById(R.id.re_btn_gps);
        Button mStartGas = (Button) findViewById(R.id.btnStartGps);
        Button mCancleGps = (Button) findViewById(R.id.btnCancleGps);
        mLinFw = (LinearLayout) findViewById(R.id.lin_fw);
        mLinQt = (LinearLayout) findViewById(R.id.lin_qt);
        mTvHotel = (TextView) findViewById(R.id.tv_hotel);
        mTvFood = (TextView) findViewById(R.id.tv_food);
        mTvStore = (TextView) findViewById(R.id.tv_store);
        mTvPark = (TextView) findViewById(R.id.tv_park);
        mTvFwCenter = (TextView) findViewById(R.id.tv_fw_center);


        mTvHotel.setOnClickListener(this);
        mTvFood.setOnClickListener(this);
        mTvStore.setOnClickListener(this);
        mTvPark.setOnClickListener(this);
        mTvFwCenter.setOnClickListener(this);
        mRaJq.setOnClickListener(this);
        mRaFw.setOnClickListener(this);
        mRaCs.setOnClickListener(this);
        mRaQt.setOnClickListener(this);
        mStartGas.setOnClickListener(this);
        mCancleGps.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mLinGps.setOnClickListener(this);

    }

    /**
     * 初始化
     */
    private void init() {
        try {
            if (aMap == null) {
                aMap = mapView.getMap();
                setUpMap();
            }
            mSensorHelper = new SensorEventHelper(this);
            if (mSensorHelper != null) {
                mSensorHelper.registerSensorListener();
            }

            mLocationErrText = (TextView) findViewById(R.id.location_errInfo_text);
            mLocationErrText.setVisibility(View.GONE);

            mPlayState = (ImageView) findViewById(R.id.playstate);
            //播放介绍
            playVoice();
            mPlayState.setOnClickListener(this);
            addMarkersToMap();
            addOverlayToMap();

            //线路规划初始化
            mRouteSearch = new RouteSearch(this);
            mRouteSearch.setRouteSearchListener(this);

        }catch (Exception e){
            ToastUtil.show(this,">>>>"+e.getMessage());
        }


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
        aMap.setOnMarkerClickListener(this);
        aMap.setOnInfoWindowClickListener(this);
        aMap.setInfoWindowAdapter(this);
        aMap.getUiSettings().setZoomControlsEnabled(false);//隐藏缩放大小图标
        aMap.getUiSettings().setLogoBottomMargin(-50);//隐藏logo

    }

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
        registeReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void registeReceiver() {
        IntentFilter filter = new IntentFilter(Constant.PAUSE_ACTION);
        this.registerReceiver(receiver, filter);
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
        //停止定位
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

        //关闭语音
        mTts.stopSpeaking();
        // 退出时释放连接
        mTts.destroy();
        unregisterReceiver(receiver);
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mLocationErrText.setVisibility(View.GONE);
                startLatitude = amapLocation.getLatitude();
                startlongitude = amapLocation.getLongitude();
                LatLng location = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                if (!mFirstFix) {
                    mFirstFix = true;
//					addCircle(location, amapLocation.getAccuracy());//添加定位精度圆
                    addMarker(location);//添加定位图标
                    mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转

                } else {
                    mCircle.setCenter(location);
                    mCircle.setRadius(amapLocation.getAccuracy());
                    mLocMarker.setPosition(location);
                }

                if (isLocation) {
                    isLocation = false;
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
                }
                contains = polygon.contains(location);
                if (contains) {
                    ToastUtil.show(this, "当前景区为三坊七巷");
                } else {
                    ToastUtil.show(this, "当前不在景区内");
                }


            } else {
                String errText = "定位失败," + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
                mLocationErrText.setVisibility(View.VISIBLE);
                mLocationErrText.setText(errText);
            }
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
            mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
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

    private void addCircle(LatLng latlng, double radius) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        mCircle = aMap.addCircle(options);
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

    //回到我的位置按钮
    public void btnLocation(View view) {
        mLinGps.setVisibility(View.GONE);

        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        mSensorHelper = new SensorEventHelper(this);
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }

        setUpMap();
        mFirstFix = false;
        isLocation = true;
        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置为高精度定位模式
        mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        mlocationClient.startLocation();
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(){

        //初始化坐标
        initData();

        for (int i = 0; i < dataInfos.size(); i++) {
            View mJdMarkerView = getLayoutInflater().inflate(R.layout.map_custom_marker_view, null);
            TextView mTvMarker = (TextView) mJdMarkerView.findViewById(R.id.tv_marker);
            mTvMarker.setText(titles.get(i));
            markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                    .fromView(mJdMarkerView))
                    .position(dataInfos.get(i))
                    .title("三坊七巷")
                    .snippet(titles.get(i))
                    .draggable(true);
            markerInfos.add(markerOption);
            ArrayList<Marker> markers = aMap.addMarkers(markerInfos, true);
        }
    }

    //标记点点击回调
    @Override
    public boolean onMarkerClick(Marker marker) {

        if ("起点".equals(marker.getTitle()) || "终点".equals(marker.getTitle())) {
            return true;
        } else {
            //获取点击标记点坐标
            LatLng position = marker.getPosition();
            endLatitude = position.latitude;
            endLongitude = position.longitude;
        }
        return false;

    }

    //信息窗口点击回调
    @Override
    public void onInfoWindowClick(Marker marker) {
        marker.hideInfoWindow();

    }

    /**
     * 监听自定义infowindow窗口的infowindow事件回调
     */
    @Override
    public View getInfoWindow(Marker marker) {
        View markerView = getLayoutInflater().inflate(R.layout.map_marker_infowindow_layout, null);
        render(marker, markerView);
        return markerView;
    }

    /**
     * 监听自定义infowindow窗口的infocontents事件回调
     */
    @Override
    public View getInfoContents(Marker marker) {

        View markerContentView = getLayoutInflater().inflate(R.layout.map_marker_infowindow_content_layout, null);
        render(marker, markerContentView);
        return markerContentView;
    }


    /**
     * 自定义infowinfow窗口
     */
    public void render(final Marker marker, View view) {


        Button mBtnGps = (Button) view.findViewById(R.id.btnGps);
        final Button mBtnVoice = (Button) view.findViewById(R.id.btnVoice);
        final TextView title_ = (TextView) view.findViewById(R.id.title);
        final String sni_text = marker.getSnippet();

        if ("卫生间".equals(sni_text) || "停车场".equals(sni_text) || "餐饮".equals(marker.getTitle()) || "酒店".equals(marker.getTitle())) {
            mBtnVoice.setText("取消");
            title_.setVisibility(View.GONE);
        } else {
            title_.setVisibility(View.VISIBLE);
        }


        //显示路线规划
        mBtnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPoint = new LatLonPoint(startLatitude, startlongitude);
                endPoint = new LatLonPoint(endLatitude, endLongitude);
                if (!"0.0,0.0".equals(startPoint.toString())) {
                    aMap.clear();// 清理地图上的所有覆盖物
                    addOverlayToMap();
                    if ("景点".equals(MarkerFlag)) {
                        addMarkersToMap();
                    } else if ("餐饮".equals(MarkerFlag)) {
                        addFoodMarkerToMap();
                    } else if ("酒店".equals(MarkerFlag)) {
                        addHotelMarkerToMap();
                    } else if ("停车场".equals(MarkerFlag)) {
                        addParkMarkerToMap();
                    } else if ("厕所".equals(MarkerFlag)) {
                        addWcMarkerToMap();
                    } else {
                        addMarkersToMap();
                    }


                    marker.hideInfoWindow();
                    //底部的按钮隐藏
                    mLinQt.setVisibility(View.GONE);
                    mLinFw.setVisibility(View.GONE);
                    //显示路线
                    showRoute(marker);
                } else {
                    ToastUtil.show(LocationMarkerActivity.this, "我的位置不存在...");
                }

            }
        });

        //跳转语音介绍
        mBtnVoice.setOnClickListener(new View.OnClickListener() {
            public String text;

            @Override
            public void onClick(View view) {
                String btnText = mBtnVoice.getText().toString();

                if ("取消".equals(btnText)) {
                    marker.hideInfoWindow();
                } else {

                    if ("林则徐纪念馆".equals(sni_text)) {
                        text = getResources().getString(R.string.linzexujinianguantext);
                    } else if ("福州清真寺".equals(sni_text)) {
                        text = getResources().getString(R.string.fujianqingzhenshitext);
                    } else if ("衣锦坊".equals(sni_text)) {
                        text = getResources().getString(R.string.yijinfantext);
                    } else if ("文儒坊".equals(sni_text)) {
                        text = getResources().getString(R.string.wenrufantext);
                    } else if ("光禄坊".equals(sni_text)) {
                        text = getResources().getString(R.string.guangrutext);
                    } else if ("杨桥巷".equals(sni_text)) {
                        text = getResources().getString(R.string.yangqiaoxiangtext);
                    } else if ("郎官巷".equals(sni_text)) {
                        text = getResources().getString(R.string.rangqiaoxiangtext);
                    } else if ("塔巷".equals(sni_text)) {
                        text = getResources().getString(R.string.taxiangtext);
                    } else if ("黄巷".equals(sni_text)) {
                        text = getResources().getString(R.string.huangxiangtext);
                    } else if ("安民巷".equals(sni_text)) {
                        text = getResources().getString(R.string.anminxiangtext);
                    } else if ("宫巷".equals(sni_text)) {
                        text = getResources().getString(R.string.gongxiangtext);
                    } else if ("吉庇巷".equals(sni_text)) {
                        text = getResources().getString(R.string.jipixiangtext);
                    } else {
                        text = getResources().getString(R.string.voicetext);
                    }

                    Intent intent = new Intent(LocationMarkerActivity.this, JqDetailActivity.class);
                    intent.putExtra("jq_detail", text);
                    intent.putExtra("sni_text", sni_text);
                    startActivityForResult(intent, 0);
                }

            }


        });


        String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.BLACK), 0,
                    titleText.length(), 0);
            titleUi.setTextSize(15);
            titleUi.setText(titleText);

        } else {
            titleUi.setText("");
        }
        String snippet = marker.getSnippet();
        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
        if (snippet != null) {
            SpannableString snippetText = new SpannableString(snippet);
            snippetText.setSpan(new ForegroundColorSpan(Color.BLACK), 0,
                    snippetText.length(), 0);
            snippetUi.setTextSize(20);
            snippetUi.setText(snippetText);
        } else {
            snippetUi.setText("");
        }
    }

    private void showRoute(Marker marker) {
        //计算两个坐标点距离
        LatLng mStartLatlng = new LatLng(startLatitude, startlongitude);
        LatLng mEndLatlng = new LatLng(endLatitude, endLongitude);
        float distance = AMapUtils.calculateLineDistance(mStartLatlng, mEndLatlng);
        ToastUtil.show(LocationMarkerActivity.this, "你距离" + marker.getSnippet() + ":" + distance + "m");
        if (distance < 500) {
            searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WalkDefault);
            iswalk = true;
        } else {
            searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);
            iswalk = false;
        }
    }


//    往地图上添加一个groundoverlay覆盖物

    private void addOverlayToMap() {
        if (!isRoute) {
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12));// 设置当前地图显示为三坊七巷
        }
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(latlng2)
                .include(latlng1).build();

        aMap.addGroundOverlay(new GroundOverlayOptions()
                .anchor(0.5f, 0.5f)
                .transparency(0f)
                .image(BitmapDescriptorFactory
                        .fromResource(R.mipmap.overly)
                        )
                .visible(true)
                .zIndex(0.5f)
                .positionFromBounds(bounds));

        // 绘制一个多边形区域
        PolygonOptions pOption = new PolygonOptions();
        pOption.add(new LatLng(26.085758, 119.299211));
        pOption.add(new LatLng(26.085401, 119.295156));
        pOption.add(new LatLng(26.083831, 119.295477));
        pOption.add(new LatLng(26.083223, 119.292913));
        pOption.add(new LatLng(26.077577, 119.29508));
        pOption.add(new LatLng(26.078135, 119.297891));
        pOption.add(new LatLng(26.079658, 119.297516));
        pOption.add(new LatLng(26.080708, 119.300531));
        polygon = aMap.addPolygon(pOption.strokeWidth(4)
                .strokeColor(Color.argb(00, 1, 1, 1))
                .fillColor(Color.argb(00, 1, 1, 1)));
    }

    private void addWcMarkerToMap() {

        //初始化厕所坐标
        TolietLatLng tolietLatLng = new TolietLatLng();
        ArrayList<LatLng> mTolietDataInfos = tolietLatLng.setTolietLatLng();

        for (int i = 0; i < mTolietDataInfos.size(); i++) {
            View mJdMarkerView = getLayoutInflater().inflate(R.layout.map_custom_marker_view, null);
            TextView mTvMarker = (TextView) mJdMarkerView.findViewById(R.id.tv_marker);
            ImageView mImaMarker = (ImageView) mJdMarkerView.findViewById(R.id.ima_marker);
            mTvMarker.setText("卫生间");
            mImaMarker.setImageResource(R.mipmap.icon_toilet_map);
            markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromView(mJdMarkerView))
                    .position(mTolietDataInfos.get(i))
                    .snippet("卫生间")
                    .draggable(true);
            markerInfos.add(markerOption);
            aMap.addMarkers(markerInfos, true);
        }
    }

    private void addParkMarkerToMap() {

        //初始化停车场坐标
        ParkLatLng parkLatLng = new ParkLatLng();
        ArrayList<LatLng> mParkDataInfos = parkLatLng.setParkLatLng();


        for (int i = 0; i < mParkDataInfos.size(); i++) {
            View mJdMarkerView = getLayoutInflater().inflate(R.layout.map_custom_marker_view, null);
            TextView mTvMarker = (TextView) mJdMarkerView.findViewById(R.id.tv_marker);
            ImageView mImaMarker = (ImageView) mJdMarkerView.findViewById(R.id.ima_marker);
            mTvMarker.setText("停车场");
            mImaMarker.setImageResource(R.mipmap.icon_lot);
            markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromView(mJdMarkerView))
                    .position(mParkDataInfos.get(i))
                    .snippet("停车场")
                    .draggable(true);
            markerInfos.add(markerOption);
            aMap.addMarkers(markerInfos, true);

        }
    }

    private void addFoodMarkerToMap() {

        //初始化餐饮坐标
        FoodLatLng foodLatLng = new FoodLatLng();
        ArrayList<LatLng> mFoodDataInfos = foodLatLng.setFoodLatLng();
        ArrayList<String> mTitles = foodLatLng.setTitles();

        for (int i = 0; i < mFoodDataInfos.size(); i++) {
            View mJdMarkerView = getLayoutInflater().inflate(R.layout.map_custom_marker_view, null);
            TextView mTvMarker = (TextView) mJdMarkerView.findViewById(R.id.tv_marker);
            ImageView mImaMarker = (ImageView) mJdMarkerView.findViewById(R.id.ima_marker);
            mTvMarker.setText(mTitles.get(i));
            mImaMarker.setImageResource(R.mipmap.icon_map_cy);
            markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromView(mJdMarkerView))
                    .position(mFoodDataInfos.get(i))
                    .title("餐饮")
                    .snippet(mTitles.get(i))
                    .draggable(true);
            markerInfos.add(markerOption);
            aMap.addMarkers(markerInfos, true);

        }
    }

    private void addHotelMarkerToMap() {

        //初始化酒店坐标
        HotelLatLng hotelLatLng = new HotelLatLng();
        ArrayList<LatLng> mHotelDataInfos = hotelLatLng.setHotelLatLng();
        ArrayList<String> mTitles = hotelLatLng.setTitles();

        for (int i = 0; i < mHotelDataInfos.size(); i++) {
            View mJdMarkerView = getLayoutInflater().inflate(R.layout.map_custom_marker_view, null);
            TextView mTvMarker = (TextView) mJdMarkerView.findViewById(R.id.tv_marker);
            ImageView mImaMarker = (ImageView) mJdMarkerView.findViewById(R.id.ima_marker);
            mTvMarker.setText(mTitles.get(i));
            mImaMarker.setImageResource(R.mipmap.icon_map_jd);
            markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromView(mJdMarkerView))
                    .position(mHotelDataInfos.get(i))
                    .title("酒店")
                    .snippet(mTitles.get(i))
                    .draggable(true);
            markerInfos.add(markerOption);
            aMap.addMarkers(markerInfos, true);

        }
    }


    //右上角回到景点图片
    public void btnJing(View view) {
        MarkerFlag = "景点";
        if (aMap != null) {
            aMap.clear();
            dataInfos.clear();
            markerInfos.clear();
            mLinGps.setVisibility(View.GONE);
            addOverlayToMap();
            addMarkersToMap();
        }
    }


    //播放景区介绍

    public void playVoice() {

        String text = getResources().getString(R.string.voicetext);
        //设置参数
        setParam();
        mTts.startSpeaking(text, mTtsListener);
        mPlayState.setImageResource(R.mipmap.play);
        isPlaying = true;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playstate:
                if (isPlaying) {
                    mPlayState.setImageResource(R.mipmap.pause);
                    mTts.pauseSpeaking();
                    isPlaying = false;

                } else {
                    mPlayState.setImageResource(R.mipmap.play);
                    mTts.resumeSpeaking();
                    isPlaying = true;
                }
                break;
            //底部景点
            case R.id.ra_jq:
                MarkerFlag = "景点";
                isClick = false;
                isQtClick = false;
                mLinQt.setVisibility(View.GONE);
                mLinFw.setVisibility(View.GONE);
                if (aMap != null) {
                    aMap.clear();
                    dataInfos.clear();
                    markerInfos.clear();
                    addOverlayToMap();
                    addMarkersToMap();
                }
                break;
            //底部服务
            case R.id.ra_fw:
                isQtClick = false;
                if (isClick) {
                    mLinFw.setVisibility(View.GONE);
                    mLinQt.setVisibility(View.GONE);
                    isClick = false;
                } else {
                    mLinFw.setVisibility(View.VISIBLE);
                    mLinQt.setVisibility(View.GONE);
                    isClick = true;
                }

                break;
            //底部厕所
            case R.id.ra_cs:
                MarkerFlag = "厕所";
                isQtClick = false;
                isClick = false;
                mLinQt.setVisibility(View.GONE);
                mLinFw.setVisibility(View.GONE);
                if (aMap != null) {
                    aMap.clear();
                    dataInfos.clear();
                    markerInfos.clear();
                    addOverlayToMap();
                    addWcMarkerToMap();
                }

                break;
            //底部其他
            case R.id.ra_qt:
                isQtClick = false;
                isClick = false;
                mLinQt.setVisibility(View.GONE);
                mLinFw.setVisibility(View.GONE);
                intent = new Intent(this, VrActivity.class);
                intent.putExtra(Constant.QJZS_URL, Constant.FZSFQX_VR);
                startActivity(intent);
//                isClick = false;
//                if (isQtClick) {
//                    mLinFw.setVisibility(View.GONE);
//                    mLinQt.setVisibility(View.GONE);
//                    isQtClick = false;
//                } else {
//                    mLinFw.setVisibility(View.GONE);
//                    mLinQt.setVisibility(View.VISIBLE);
//                    isQtClick = true;
//                }
                break;

            //停车场
            case R.id.tv_park:
                MarkerFlag = "停车场";
                isQtClick = true;
                if (aMap != null) {
                    aMap.clear();
                    dataInfos.clear();
                    markerInfos.clear();
                    addOverlayToMap();
                    addParkMarkerToMap();
                }

                break;
            //酒店
            case R.id.tv_hotel:
                MarkerFlag = "酒店";
                isClick = true;
                if (aMap != null) {
                    aMap.clear();
                    dataInfos.clear();
                    markerInfos.clear();
                    addOverlayToMap();
                    addHotelMarkerToMap();
                }
                break;

            //餐饮
            case R.id.tv_food:
                MarkerFlag = "餐饮";
                isClick = true;
                if (aMap != null) {
                    aMap.clear();
                    dataInfos.clear();
                    markerInfos.clear();
                    addOverlayToMap();
                    addFoodMarkerToMap();
                }
                break;
            //返回
            case R.id.ima_back:
                try{
                    finish();
                }catch (Exception e){
                    ToastUtil.show(this,">>>>"+e.getMessage().toString());
                }

                break;
            //开始导航
            case R.id.btnStartGps:
                if (startLatitude >= 0 && startlongitude >= 0 && endLatitude >= 0 && endLongitude >= 0) {
                    Intent intent = new Intent(LocationMarkerActivity.this, GPSGpsActivity.class);
                    intent.putExtra("latitude", startLatitude + "");
                    intent.putExtra("longitude", startlongitude + "");
                    intent.putExtra("endLatitude", endLatitude + "");
                    intent.putExtra("endLongitude", endLongitude + "");
                    intent.putExtra("walk", iswalk);
                    startActivity(intent);
                } else {
                    ToastUtil.show(LocationMarkerActivity.this, "请求位置出现错误...");
                }
//              //调到高德地图
//                Intent intent = new Intent("android.intent.action.VIEW",android.net.Uri.parse("androidamap://navi?sourceApplication=智慧导览&lat="+endPoint.getLatitude()+ "&lon="+ endPoint.getLongitude()+ "&dev=0"));
//                intent.setPackage("com.autonavi.minimap");
//                startActivity(intent);

                break;

            // /取消导航
            case R.id.btnCancleGps:

                mLinGps.setVisibility(View.GONE);
                aMap.clear();
                dataInfos.clear();
                markerInfos.clear();
                addOverlayToMap();
                addMarkersToMap();
                break;

        }
    }


    /**
     * 合成回调监听。
     */
    public SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            Toast.makeText(LocationMarkerActivity.this, "开始播放", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSpeakPaused() {
            Toast.makeText(LocationMarkerActivity.this, "暂停播放", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSpeakResumed() {
            Toast.makeText(LocationMarkerActivity.this, "继续播放", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                Toast.makeText(LocationMarkerActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
                isFirst = false;
                mPlayState.setImageResource(R.mipmap.pause);
            } else if (error != null) {
                Toast.makeText(LocationMarkerActivity.this, "error.getPlainDescription(true)", Toast.LENGTH_SHORT).show();
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
        dialogUtil.showProgressDialog(this, "请稍后...");
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

    /*
    *
    * 线路规划回调
    *
    * */
    @Override
    public void onBusRouteSearched(BusRouteResult result, int errorCode) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        isRoute = true;

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
                    mLinGps.setVisibility(View.VISIBLE);
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

//        aMap.clear();// 清理地图上的所有覆盖物
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
                    mLinGps.setVisibility(View.VISIBLE);
                    dialogUtil.dismissProgressDialog();
                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.show(LocationMarkerActivity.this, R.string.no_result);
                }
            } else {
                ToastUtil.show(LocationMarkerActivity.this, R.string.no_result);
            }
        } else {
            ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            isPlaying = data.getBooleanExtra("isPlay", false);
            if (isPlaying) {
                mPlayState.setImageResource(R.mipmap.play);
            } else {
                mPlayState.setImageResource(R.mipmap.pause);
            }
        } else {
            ToastUtil.show(LocationMarkerActivity.this, "未收到结果码" + requestCode);
        }

    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (Constant.PAUSE_ACTION == action) {
                boolean isFinish = intent.getBooleanExtra("isFinish", false);
                if (isFinish) {
                    mPlayState.setImageResource(R.mipmap.pause);
                }
            }
        }
    };

    public void walkRoutePolyLine(PolylineOptions options) {
        if (options == null) {
            return;
        }
        Polyline polyline = aMap.addPolyline(options);
        polyline.setZIndex(1.0f);
        if (polyline != null) {
            allPolyLines.add(polyline);

        }
    }


}
