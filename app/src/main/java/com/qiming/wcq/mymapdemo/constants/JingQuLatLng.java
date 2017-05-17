package com.qiming.wcq.mymapdemo.constants;

import com.amap.api.maps.model.LatLng;
import com.qiming.wcq.mymapdemo.javabean.DataInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/16.
 */

public class JingQuLatLng {


    private ArrayList<LatLng> dataInfos = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();

    public ArrayList<LatLng> setJqLatLng() {


        LatLng latlng_marker_1 = new LatLng(26.078606, 119.297525);
        LatLng latlng_marker_2 = new LatLng(26.079083, 119.294526);
        LatLng latlng_marker_3 = new LatLng(26.0798, 119.295728);
        LatLng latlng_marker_4 = new LatLng(26.080389, 119.296185);
        LatLng latlng_marker_5 = new LatLng(26.080186, 119.297183);
        LatLng latlng_marker_6 = new LatLng(26.080548, 119.297413);
        LatLng latlng_marker_7 = new LatLng(26.080726, 119.298706);
        LatLng latlng_marker_8 = new LatLng(26.079468, 119.299967);
        LatLng latlng_marker_9 = new LatLng(26.080581, 119.300111);
        LatLng latlng_marker_10 = new LatLng(26.081796, 119.299999);
        LatLng latlng_marker_11 = new LatLng(26.081352, 119.295444);
        LatLng latlng_marker_12 = new LatLng(26.083309, 119.295675);
        LatLng latlng_marker_13 = new LatLng(26.083641, 119.294077);
        LatLng latlng_marker_14 = new LatLng(26.084749, 119.299645);
        LatLng latlng_marker_15 = new LatLng(26.084614, 119.296201);
        LatLng latlng_marker_16 = new LatLng(26.085385, 119.297773);


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
        dataInfos.add(latlng_marker_13);
        dataInfos.add(latlng_marker_14);
        dataInfos.add(latlng_marker_15);
        dataInfos.add(latlng_marker_16);


        return dataInfos;
    }


    public ArrayList<String> setTitles() {

        String title1 = "林则徐纪念馆";
        String title2 = "光禄坊";
        String title3 = "刘家大院";
        String title4 = "福州三坊七巷美术馆";
        String title5 = "瑞来春堂";
        String title6 = "闽都民俗文化大观园";
        String title7 = "谢家祠";
        String title8 = "福州清真寺";
        String title9 = "吉庇巷";
        String title10 = "宫巷";
        String title11 = "文儒坊";
        String title12 = "杨桥巷";
        String title13 = "衣锦坊";
        String title14 = "塔巷";
        String title15 = "南后街";
        String title16 = "郎官巷";

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
        titles.add(title13);
        titles.add(title14);
        titles.add(title15);
        titles.add(title16);

        return titles;

    }


}
