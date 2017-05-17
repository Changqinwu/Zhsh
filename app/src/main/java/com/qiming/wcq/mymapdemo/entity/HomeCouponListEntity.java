package com.qiming.wcq.mymapdemo.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */

public class HomeCouponListEntity {

    private List<CouponlistBean> couponlist;

    public List<CouponlistBean> getCouponlist() {
        return couponlist;
    }

    public void setCouponlist(List<CouponlistBean> couponlist) {
        this.couponlist = couponlist;
    }

    public static class CouponlistBean {
        /**
         * coupon_id : 1
         * coupon_name : 优惠券1
         * image_url : http://999.zimeiping.com/lzhong/qxfj/images/banner_1.jpg
         */

        private String coupon_id;
        private String coupon_name;
        private String image_url;

        public String getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
        }

        public String getCoupon_name() {
            return coupon_name;
        }

        public void setCoupon_name(String coupon_name) {
            this.coupon_name = coupon_name;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}
