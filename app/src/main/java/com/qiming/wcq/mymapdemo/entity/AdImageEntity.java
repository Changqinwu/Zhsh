package com.qiming.wcq.mymapdemo.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */

public class AdImageEntity {

    private List<AdpicturesBean> adpictures;

    public List<AdpicturesBean> getAdpictures() {
        return adpictures;
    }

    public void setAdpictures(List<AdpicturesBean> adpictures) {
        this.adpictures = adpictures;
    }

    public static class AdpicturesBean {
        /**
         * image_id : 1
         * image_name : 轮播图1
         * image_url : http://999.zimeiping.com/wcq/images/1.jpg
         */

        private String image_id;
        private String image_name;
        private String image_url;

        public String getImage_id() {
            return image_id;
        }

        public void setImage_id(String image_id) {
            this.image_id = image_id;
        }

        public String getImage_name() {
            return image_name;
        }

        public void setImage_name(String image_name) {
            this.image_name = image_name;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}
