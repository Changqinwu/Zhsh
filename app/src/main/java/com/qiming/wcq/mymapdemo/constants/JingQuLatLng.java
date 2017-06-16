package com.qiming.wcq.mymapdemo.constants;

import com.amap.api.maps.model.LatLng;
import com.qiming.wcq.mymapdemo.javabean.DataInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/16.
 */

public class JingQuLatLng {


    private ArrayList<LatLng> dataInfos = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    private Map<LatLng, String> map = new HashMap<>();
    private LatLng latlng_marker_1;
    private LatLng latlng_marker_2;
    private LatLng latlng_marker_3;
    private LatLng latlng_marker_4;
    private LatLng latlng_marker_5;
    private LatLng latlng_marker_6;
    private LatLng latlng_marker_7;
    private LatLng latlng_marker_8;
    private LatLng latlng_marker_9;
    private LatLng latlng_marker_10;
    private LatLng latlng_marker_11;
    private LatLng latlng_marker_12;
    private LatLng latlng_marker_13;
    private LatLng latlng_marker_14;
    private LatLng latlng_marker_15;
    private LatLng latlng_marker_16;
    private LatLng latlng_marker_17;
    private String title1;
    private String title2;
    private String title3;
    private String title4;
    private String title5;
    private String title6;
    private String title7;
    private String title8;
    private String title9;
    private String title10;
    private String title11;
    private String title12;
    private String title13;
    private String title14;
    private String title15;
    private String title16;
    private String title17;

    public ArrayList<LatLng> setJqLatLng() {


        latlng_marker_1 = new LatLng(26.078606, 119.297525);
        latlng_marker_2 = new LatLng(26.079083, 119.294526);
        latlng_marker_3 = new LatLng(26.0798, 119.295728);
        latlng_marker_4 = new LatLng(26.080389, 119.296185);
        latlng_marker_5 = new LatLng(26.080186, 119.297183);
        latlng_marker_6 = new LatLng(26.080548, 119.297413);
        latlng_marker_7 = new LatLng(26.080726, 119.298706);
        latlng_marker_8 = new LatLng(26.079468, 119.299967);
        latlng_marker_9 = new LatLng(26.080581, 119.300111);
        latlng_marker_10 = new LatLng(26.081796, 119.299999);
        latlng_marker_11 = new LatLng(26.081352, 119.295444);
        latlng_marker_12 = new LatLng(26.083309, 119.295675);
        latlng_marker_13 = new LatLng(26.083641, 119.294077);
        latlng_marker_14 = new LatLng(26.084749, 119.299645);
        latlng_marker_15 = new LatLng(26.084614, 119.296201);
        latlng_marker_16 = new LatLng(26.085385, 119.297773);
        latlng_marker_17 = new LatLng(26.02549, 119.268279);


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
        dataInfos.add(latlng_marker_17);


        return dataInfos;
    }


    public ArrayList<String> setTitles() {

        title1 = "林则徐纪念馆";
        title2 = "光禄坊";
        title3 = "刘家大院";
        title4 = "福州三坊七巷美术馆";
        title5 = "瑞来春堂";
        title6 = "闽都民俗文化大观园";
        title7 = "谢家祠";
        title8 = "福州清真寺";
        title9 = "吉庇巷";
        title10 = "宫巷";
        title11 = "文儒坊";
        title12 = "杨桥巷";
        title13 = "衣锦坊";
        title14 = "塔巷";
        title15 = "南后街";
        title16 = "郎官巷";
        title17 = "郎官巷";

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
        titles.add(title17);

        return titles;

    }


    public Map<LatLng, String> playVoiceLocation() {

        map.put(latlng_marker_1, title1);
        map.put(latlng_marker_2, title2);
        map.put(latlng_marker_3, title3);
        map.put(latlng_marker_4, title4);
        map.put(latlng_marker_5, title5);
        map.put(latlng_marker_6, title6);
        map.put(latlng_marker_7, title7);
        map.put(latlng_marker_8, title8);
        map.put(latlng_marker_9, title9);
        map.put(latlng_marker_10, title10);
        map.put(latlng_marker_11, title11);
        map.put(latlng_marker_12, title12);
        map.put(latlng_marker_13, title13);
        map.put(latlng_marker_14, title14);
        map.put(latlng_marker_15, title15);
        map.put(latlng_marker_16, title16);
        map.put(latlng_marker_17, title17);

        return map;
    }


}
