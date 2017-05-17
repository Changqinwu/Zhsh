package com.qiming.wcq.mymapdemo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.entity.AdImageEntity;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/12/12.
 */

public class NetworkImageHolderView implements Holder<AdImageEntity.AdpicturesBean>{

    private final ArrayList<AdImageEntity.AdpicturesBean> imaData;
    private final Context context;

    public NetworkImageHolderView(ArrayList<AdImageEntity.AdpicturesBean> imaData,Context context){
        this.context = context;
        this.imaData = imaData;
    }

    private ImageView imageView;
    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, AdImageEntity.AdpicturesBean data) {
        Glide.with(context).load(imaData.get(position).getImage_url()).into(imageView);
//        imageView.setImageResource(imaData.get(position));
//        ImageLoaderUtils.display(context,imageView,imaData.get(position));
    }
}
