package com.qiming.wcq.mymapdemo.javabean;

/**
 * Created by Administrator on 2017/2/27.
 */

public class HomeIconData {

    private String ImageId;
    private String IconName;
    private String urlParam;


    public HomeIconData(String imageId, String iconName, String urlParam) {
        ImageId = imageId;
        IconName = iconName;
        this.urlParam = urlParam;
    }

    @Override
    public String toString() {
        return "HomeIconData{" +
                "ImageId='" + ImageId + '\'' +

                ", IconName='" + IconName + '\'' +
                ", urlParam='" + urlParam + '\'' +
                '}';
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    public String getImageId() {
        return ImageId;
    }

    public void setImageId(String imageId) {
        ImageId = imageId;
    }

    public String getIconName() {
        return IconName;
    }

    public void setIconName(String iconName) {
        IconName = iconName;
    }
}
