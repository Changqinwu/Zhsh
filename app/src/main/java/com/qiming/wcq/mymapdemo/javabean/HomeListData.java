package com.qiming.wcq.mymapdemo.javabean;

/**
 * Created by Administrator on 2017/2/27.
 */

public class HomeListData {
    private String imageUrl;
    private String title;
    private String message;
    private String other;
    private String urlParam;

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    @Override
    public String toString() {
        return "HomeListData{" +
                "imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", other='" + other + '\'' +
                ", urlParam='" + urlParam + '\'' +
                '}';
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public HomeListData(String imageUrl, String title, String message, String other,String urlParam) {

        this.imageUrl = imageUrl;
        this.title = title;
        this.message = message;
        this.other = other;
        this.urlParam = urlParam;
    }
}
