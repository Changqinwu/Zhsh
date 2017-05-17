package com.qiming.wcq.mymapdemo.constants;

import android.util.ArrayMap;

import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.javabean.HomeIconData;
import com.qiming.wcq.mymapdemo.javabean.HomeListData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/27.
 */

public class HomeData {
    //轮播图
    private static ArrayList<String> localImages = new ArrayList<>();
    //首页图标
    private static ArrayList<HomeIconData> localIcon = new ArrayList<>();
    //首页图标文字
    private static List<HomeListData> listDatas = new ArrayList<>();
    //列表对应标题
    private static Map<String, String> listTitle = new HashMap<>();
    //三坊七巷语音介绍轮播图
    private static ArrayList<String> localSoundBanner = new ArrayList<>();
    //轮播图下面三张图片
    private static ArrayList<String> couponPic = new ArrayList<>();



    public static ArrayList<String> locaPicture() {
        localImages.clear();
        localImages.add(Constant.AD1);
        localImages.add(Constant.AD2);
        localImages.add(Constant.AD3);
        return localImages;
    }

    public static ArrayList<String> localSoundPic() {
        localSoundBanner.clear();
        localSoundBanner.add(Constant.SOUNDPIC1);
        localSoundBanner.add(Constant.SOUNDPIC2);
        return localSoundBanner;
    }

    public static ArrayList<String> loadCouponPic() {
        localSoundBanner.clear();
        localSoundBanner.add(Constant.COUPONPIC1);
        localSoundBanner.add(Constant.COUPONPIC2);
        localSoundBanner.add(Constant.COUPONPIC3);
        return localSoundBanner;
    }


    public static ArrayList<HomeIconData> locaHomeIcon() {
        localIcon.clear();
        localIcon.add(new HomeIconData(Constant.ICON1, "政务平台", "zwpt"));
        localIcon.add(new HomeIconData(Constant.ICON2, "智屏发布", "zpfb"));
        localIcon.add(new HomeIconData(Constant.ICON3, "景点导览", "0"));
        localIcon.add(new HomeIconData(Constant.ICON4, "周边服务", "zbfw"));
        localIcon.add(new HomeIconData(Constant.ICON5, "农副产品", "4"));
        localIcon.add(new HomeIconData(Constant.ICON6, "酒店客栈", "jdkz"));
        localIcon.add(new HomeIconData(Constant.ICON7, "美食天地", "3"));
        localIcon.add(new HomeIconData(Constant.ICON8, "商行超市", "2"));
        return localIcon;
    }

    public static List<HomeListData> locaListData() {
        listDatas.clear();
        listDatas.add(new HomeListData(Constant.LISTPIC1, "福州三坊七巷", "灯红酒绿、错综复杂的尘世里的一方净土", "景点", "fzsfqx"));
        listDatas.add(new HomeListData(Constant.LISTPIC2, "福州西湖公园", "十里柳如丝，湖光晚更奇” 我在西湖等你", "景点", "fzxhgy"));
        listDatas.add(new HomeListData(Constant.LISTPIC3, "福州永泰县", "福州后花园”，风景如画，令人流连忘返", "景点", "fzytx"));
        listDatas.add(new HomeListData(Constant.LISTPIC4, "福州西禅寺", "荔树四朝传宋代，钟声千古响唐音” 巍峨而壮观", "景点", "fzxcs"));
        listDatas.add(new HomeListData(Constant.LISTPIC5, "福州沃尔戴斯酒店", "华灯初上，万物升平，匠心独具、金雕玉砌、浑然天成", "酒店", "wedsjd"));
        listDatas.add(new HomeListData(Constant.LISTPIC6, "福州悦华酒店", "劳顿烟消云散，尽享都市风情", "酒店", "yhjd"));
        listDatas.add(new HomeListData(Constant.LISTPIC7, "苏宁购物广场", "苏宁“易”起来,生活“购”精彩", "酒店", "sngwgc"));
        listDatas.add(new HomeListData(Constant.LISTPIC8, "远恒大酒店", "浓重而不失活泼的色调、奔放且大气的布局", "酒店", "yhdjd"));
        listDatas.add(new HomeListData(Constant.LISTPIC9, "宝龙城市广场", "春日微风拂面、街道繁花似锦", "酒店", "blcsgc"));
        return listDatas;
    }

    public static Map<String,String> titleDatas() {
        listTitle.clear();
        listTitle.put("fzsfqx","福州三坊七巷");
        listTitle.put("fzxhgy","福州西湖公园");
        listTitle.put("fzytx","福州永泰县");
        listTitle.put("fzxcs","福州西禅寺");
        listTitle.put("wedsjd","沃尔戴斯酒店");
        listTitle.put("yhjd","福州悦华酒店");
        listTitle.put("yhdjd","远恒大酒店");
        listTitle.put("psjd","福州璞宿酒店");
        listTitle.put("sngwgc","苏宁购物广场");
        listTitle.put("blcsgc","宝龙城市广场");
        listTitle.put("gzrhjh","锅滋然黄记煌");
        listTitle.put("xsmms","星山妈妈手");
        return listTitle;
    }


}
