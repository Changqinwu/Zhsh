package com.qiming.wcq.mymapdemo.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */

public class HomeIconListEntity {


    private List<IconlistBean> iconlist;

    public List<IconlistBean> getIconlist() {
        return iconlist;
    }

    public void setIconlist(List<IconlistBean> iconlist) {
        this.iconlist = iconlist;
    }

    public static class IconlistBean {
        /**
         * icon_id : 1
         * icon_type : zwpt
         * icon_name : 政务平台
         * image_url : http://999.zimeiping.com/lzhong/qxfj/images/ioc_1.png
         */

        private String icon_id;
        private String icon_type;
        private String icon_name;
        private String image_url;

        public String getIcon_id() {
            return icon_id;
        }

        public void setIcon_id(String icon_id) {
            this.icon_id = icon_id;
        }

        public String getIcon_type() {
            return icon_type;
        }

        public void setIcon_type(String icon_type) {
            this.icon_type = icon_type;
        }

        public String getIcon_name() {
            return icon_name;
        }

        public void setIcon_name(String icon_name) {
            this.icon_name = icon_name;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}
