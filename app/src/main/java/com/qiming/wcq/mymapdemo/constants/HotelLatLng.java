package com.qiming.wcq.mymapdemo.constants;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/16.
 */

public class HotelLatLng {


    private ArrayList<LatLng> dataInfos = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();

    public ArrayList<LatLng> setHotelLatLng() {


        LatLng latlng_marker_1 = new LatLng(26.081643, 119.297525);
        LatLng latlng_marker_2 = new LatLng(26.080838, 119.294526);
        LatLng latlng_marker_3 = new LatLng(26.08049, 119.295728);
        LatLng latlng_marker_4 = new LatLng(26.084782, 119.296185);
        LatLng latlng_marker_5 = new LatLng(26.083746, 119.297183);
        LatLng latlng_marker_6 = new LatLng(26.083782, 119.297413);
        LatLng latlng_marker_7 = new LatLng( 26.08551, 119.298706);
        LatLng latlng_marker_8 = new LatLng(26.08551, 119.299967);
        LatLng latlng_marker_9 = new LatLng(26.077972, 119.300111);
        LatLng latlng_marker_10 = new LatLng(26.083465, 119.299999);
        LatLng latlng_marker_11 = new LatLng(26.085898, 119.295444);
        LatLng latlng_marker_12 = new LatLng(26.083423, 119.295675);


        dataInfos.add(latlng_marker_1);
        dataInfos.add(latlng_marker_2);
        dataInfos.add(latlng_marker_3);
        dataInfos.add(latlng_marker_4);
        dataInfos.add(latlng_marker_5);
        dataInfos.add(latlng_marker_6);
        dataInfos.add(latlng_marker_7);
        dataInfos.add(latlng_marker_8);
        dataInfos.add(latlng_marker_9);
        dataInfos.add(latlng_marker_10);
        dataInfos.add(latlng_marker_11);
        dataInfos.add(latlng_marker_12);


        return dataInfos;
    }


    public ArrayList<String> setTitles() {

        String title1 = "福州聚春园驿馆";
        String title2 = "如家快捷酒店";
        String title3 = "薇阁酒店（桂枝里）";
        String title4 = "东百洲际大酒店";
        String title5 = "易佰旅";
        String title6 = "速8酒店";
        String title7 = "中闽大厦宾馆";
        String title8 = "她家宾馆";
        String title9 = "格林豪泰酒店";
        String title10 = "花界情侣主题酒店";
        String title11 = "旺和宾馆";
        String title12 = "泉州市政府驻榕办宾馆";


        titles.add(title1);
        titles.add(title2);
        titles.add(title3);
        titles.add(title4);
        titles.add(title5);
        titles.add(title6);
        titles.add(title7);
        titles.add(title8);
        titles.add(title9);
        titles.add(title10);
        titles.add(title11);
        titles.add(title12);


        return titles;

    }


}
