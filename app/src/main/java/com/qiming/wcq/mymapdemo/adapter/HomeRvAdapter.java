package com.qiming.wcq.mymapdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.entity.HomeListEntity;
import com.qiming.wcq.mymapdemo.javabean.HomeListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */

public class HomeRvAdapter extends BaseRecyclerAdapter<HomeRvAdapter.HomeViewHolder> {

    private final Context context;
    private final List<HomeListEntity.HomelistBean> listDatas;
    private MyItemClickListener mItemClickListener = null;

    public HomeRvAdapter(Context context, List<HomeListEntity.HomelistBean> listDatas) {
        this.context = context;
        this.listDatas = listDatas;
    }


    @Override
    public HomeViewHolder getViewHolder(View view) {
        return new HomeViewHolder(view, false,mItemClickListener);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_rv_item_layout, null
        );


        HomeViewHolder holder = new HomeViewHolder(view,true,mItemClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position, boolean isItem) {
        Glide.with(context).load(listDatas.get(position).getImage_url()).into(holder.item_imageView);
        holder.tv_title.setText(listDatas.get(position).getItem_title());
        holder.tv_message.setText(listDatas.get(position).getItem_content());
//        ViewGroup.LayoutParams lp_lin = holder.lin_item.getLayoutParams();
//        lp_lin.height = height;
    }


    @Override
    public int getAdapterItemCount() {
        return listDatas.size();
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder {


        private LinearLayout lin_item;
        private TextView tv_title;
        private ImageView item_imageView;
        private TextView tv_message;
        private TextView tv_other;

        public HomeViewHolder(View itemView, boolean isItem, final MyItemClickListener listener) {
            super(itemView);
            //item点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(view,getPosition());
                    }
                }
            });
            item_imageView = (ImageView) itemView.findViewById(R.id.home_rv_item_ima);
            tv_title = (TextView) itemView.findViewById(R.id.home_rv_item_title);
            tv_message = (TextView) itemView.findViewById(R.id.tv_mesaage);
            tv_other = (TextView) itemView.findViewById(R.id.tv_other);
            lin_item = (LinearLayout) itemView.findViewById(R.id.lin_item);
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
