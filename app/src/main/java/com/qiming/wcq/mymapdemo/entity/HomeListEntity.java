package com.qiming.wcq.mymapdemo.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */

public class HomeListEntity {

    private List<HomelistBean> homelist;

    public List<HomelistBean> getHomelist() {
        return homelist;
    }

    public void setHomelist(List<HomelistBean> homelist) {
        this.homelist = homelist;
    }

    public static class HomelistBean {
        /**
         * item_id : 1
         * item_title : 福州三坊七巷
         * item_content : 灯红酒绿、错综复杂的尘世里的一方净土
         * item_type : fzsfqx
         * image_url : http://999.zimeiping.com/lzhong/images/scenic_sfqx.jpg
         */

        private String item_id;
        private String item_title;
        private String item_content;
        private String item_type;
        private String image_url;

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_title() {
            return item_title;
        }

        public void setItem_title(String item_title) {
            this.item_title = item_title;
        }

        public String getItem_content() {
            return item_content;
        }

        public void setItem_content(String item_content) {
            this.item_content = item_content;
        }

        public String getItem_type() {
            return item_type;
        }

        public void setItem_type(String item_type) {
            this.item_type = item_type;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}
