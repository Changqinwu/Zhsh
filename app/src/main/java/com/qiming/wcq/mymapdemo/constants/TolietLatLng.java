package com.qiming.wcq.mymapdemo.constants;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/16.
 */

public class TolietLatLng {

    private ArrayList<LatLng> dataInfos = new ArrayList<>();

    public ArrayList<LatLng> setTolietLatLng() {


        LatLng latlng_marker_1 = new LatLng(26.082276, 119.29722200000003);
        LatLng latlng_marker_2 = new LatLng(26.080712, 119.29742599999997);
        LatLng latlng_marker_3 = new LatLng(26.083368, 119.29648700000001);
        LatLng latlng_marker_4 = new LatLng(26.080708, 119.29539999999997);
        LatLng latlng_marker_5 = new LatLng(26.081422, 119.29369400000002);
        LatLng latlng_marker_6 = new LatLng(26.081592, 119.29361900000004);
        LatLng latlng_marker_7 = new LatLng(26.084822, 119.29662200000001);
        LatLng latlng_marker_8 = new LatLng(26.084913, 119.29667799999999);
        LatLng latlng_marker_9 = new LatLng(26.078094, 119.29729199999997);
        LatLng latlng_marker_10 = new LatLng(26.085401, 119.29549400000002);


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


        return dataInfos;
    }
}
