package com.qiming.wcq.mymapdemo.entity;

import com.amap.api.maps.model.LatLng;

/**
 * Created by Administrator on 2017/6/16.
 */

public class MapJdDetail {
    private LatLng mLatLng;

    @Override
    public String toString() {
        return "MapJdDetail{" +
                "mLatLng=" + mLatLng +
                ", title='" + title + '\'' +
                '}';
    }

    public MapJdDetail(LatLng latLng, String title) {
        mLatLng = latLng;
        this.title = title;
    }

    private String title;


    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
