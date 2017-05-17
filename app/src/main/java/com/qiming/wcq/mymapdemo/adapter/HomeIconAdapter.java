package com.qiming.wcq.mymapdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.javabean.HomeIconData;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/27.
 */

public class HomeIconAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<HomeIconData> list;

    public HomeIconAdapter(Context context, ArrayList<HomeIconData> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.home_gridview_itme_layout, null);
            ImageView ima_icon = (ImageView) view.findViewById(R.id.ima_icon);
            TextView tv_icon = (TextView) view.findViewById(R.id.tv_icon);
            Glide.with(context).load(list.get(position).getImageId()).into(ima_icon);
            tv_icon.setText(list.get(position).getIconName());
        }

        return view;
    }
}
