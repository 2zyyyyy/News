package com.example.tony.news.adapter;

/*
 * Created by Tony on 16/11/19.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tony.news.R;
import com.example.tony.news.bean.BannerBean;
import com.example.tony.news.bean.Data;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

public class FirstPapgerAdapter extends RecyclerView.Adapter {

    private final int TYPE_HEAD = 0;//表示首个位置，直接显示轮播图片
    private final int TYPE_NORMAL = 1;//表示正常的item布局
    private final int TYPE_FOOT = 2;//表示刷新布局

    private Context mContext;

    private List<Data> item_data;//轮播图片的路径

    private BannerBean bean;

    public FirstPapgerAdapter(Context context, List<Data> item_data, BannerBean bean) {
        this.mContext = context;
        this.item_data = item_data;
        this.bean = bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_HEAD) {
            //这里创建顶部banner的ViewHolder
            holder = new BannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.itemhander_banner,
                    parent, false));
        } else if (viewType == TYPE_NORMAL){
            //这里创建中间的ViewHolder
            holder = new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_firstfragment,
                    parent, false));

        } else if (viewType == TYPE_FOOT) {
            //这里创建底部的ViewHolder
            holder = new FootViewHolder(LayoutInflater.from(mContext).inflate(R.layout.itemfooter,
                    parent, false));
        }
        return holder;
    }

    //填充数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BannerViewHolder) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            //设置banner样式
            //1、圆形指示器
            bannerViewHolder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            //2、设置标题
            bannerViewHolder.banner.setBannerTitle(bean.getTitle());
            bannerViewHolder.banner.setImages(bean.getImg_url());
        } else if (holder instanceof ItemViewHolder) {
            //处理每一个item的布局
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.simpleView.setImageURI(item_data.get(position - 1).getThumbnail());
            itemViewHolder.textView.setText(item_data.get(position - 1).getTitle());

            if (listener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(view, position);
                    }
                });
            }

        } else if (holder instanceof FootViewHolder) {
            if (item_data.size() > 0) {
                //判断有数据时在显示底部加载数据动画
                ((FootViewHolder) holder).progress_lin.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
            return item_data.size() + 1 + 1;
    }

    //告诉创建什么类型的viewHolder
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        }else if (position + 1 == getItemCount()) {
            return TYPE_FOOT;
        }else {
            return TYPE_NORMAL;
        }
    }

    //正常的item的布局管理
    class ItemViewHolder extends RecyclerView.ViewHolder {
        //初始化
        SimpleDraweeView simpleView;
        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            simpleView = (SimpleDraweeView) itemView.findViewById(R.id.simpleView);
            textView = (TextView) itemView.findViewById(R.id.news_text);
        }
    }

    //首位的banner
    class BannerViewHolder extends RecyclerView.ViewHolder {

        Banner banner;

        public BannerViewHolder(View itemView) {
            super(itemView);

            banner = (Banner) itemView.findViewById(R.id.banner);
        }
    }
    //底部刷新的布局
    class FootViewHolder extends RecyclerView.ViewHolder {
        LinearLayout progress_lin;
        public FootViewHolder(View itemView) {
            super(itemView);
            progress_lin = (LinearLayout) itemView.findViewById(R.id.progress_lin);
        }
    }
    //RecyclerView item 的单击事件回调接口
    public interface MyItemClickListener {
        void onClick(View itemView, int position);
    }
    //本类中保存的一个接口的引用
    private MyItemClickListener listener;

    public void setMyItemClickListener(MyItemClickListener listener) {
        this.listener = listener;
    }
}
