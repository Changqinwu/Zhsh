package com.qiming.wcq.mymapdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.entity.HomeIconListEntity;
import com.qiming.wcq.mymapdemo.javabean.HomeIconData;
import com.qiming.wcq.mymapdemo.javabean.HomeListData;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/27.
 */

public class GridAdapter extends BaseRecyclerAdapter<GridAdapter.HomeViewHolder> {

    private final Context context;
    private final ArrayList<HomeIconListEntity.IconlistBean> listDatas;
    private MyItemClickListener mItemClickListener = null;

    public GridAdapter(Context context, ArrayList<HomeIconListEntity.IconlistBean> listDatas) {
        this.context = context;
        this.listDatas = listDatas;
    }


    @Override
    public HomeViewHolder getViewHolder(View view) {
        return new HomeViewHolder(view, false, mItemClickListener);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_gridview_itme_layout, null
        );

        HomeViewHolder holder = new HomeViewHolder(view, true, mItemClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position, boolean isItem) {
        Glide.with(context).load(listDatas.get(position).getImage_url()).into(holder.ima_icon);
        holder.tv_icon.setText(listDatas.get(position).getIcon_name());
    }


    @Override
    public int getAdapterItemCount() {
        return listDatas.size();
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder {


        private final TextView tv_icon;
        private ImageView ima_icon;

        public HomeViewHolder(View itemView, boolean isItem, final MyItemClickListener listener) {
            super(itemView);
            //item点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(view, getPosition());
                    }
                }
            });

            ima_icon = (ImageView) itemView.findViewById(R.id.ima_icon);
            tv_icon = (TextView) itemView.findViewById(R.id.tv_icon);

        }


    }


    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }
}
