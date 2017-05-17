package com.qiming.wcq.mymapdemo.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 */

public class JdDetailEntity {

    private List<JdDetailBean> JdDetail;

    public List<JdDetailBean> getJdDetail() {
        return JdDetail;
    }

    public void setJdDetail(List<JdDetailBean> JdDetail) {
        this.JdDetail = JdDetail;
    }

    public static class JdDetailBean {
        /**
         * id : 1
         * name : 图片1
         * image_url : http://999.zimeiping.com/wcq/images/ljdy_1.jpg
         */

        private String id;
        private String name;
        private String image_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}

