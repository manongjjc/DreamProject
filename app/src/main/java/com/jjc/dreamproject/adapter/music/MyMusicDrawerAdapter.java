package com.jjc.dreamproject.adapter.music;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jjc.dreamproject.R;
import com.jjc.dreamproject.bean.MediaBean;

import java.util.List;

/**
 * Created by JJC on 2017/12/15.
 */

public class MyMusicDrawerAdapter extends RecyclerView.Adapter {
    private List<MediaBean> mediaBeanList;
    private MediaBean mediaBean;
    private Context context;
    private LayoutInflater inflater;
    private ReturnCurrentMedia inter;

    public MyMusicDrawerAdapter(List<MediaBean> mediaBeanList, MediaBean mediaBean, Context context) {
        this.mediaBeanList = mediaBeanList;
        this.mediaBean = mediaBean;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.mymusic_show_item, null, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        final MediaBean bean = mediaBeanList.get(position);
        Glide.with(context).load(bean.getPhotoUrl())
                .placeholder(R.drawable.ic_defout)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(viewHolder.im);
        viewHolder.tv.setText(bean.getTitle());
        if (bean.getUrl().equals(mediaBean.getUrl())) {
            viewHolder.tv.setTextColor(context.getResources().getColor(R.color.colorYellow));
            viewHolder.tv.setTextSize(19);
        } else {
            viewHolder.tv.setTextColor(context.getResources().getColor(R.color.NORMAL));
            viewHolder.tv.setTextSize(17);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("JYD", "onclick inter");
                if (inter != null)
                    inter.currentMedia(bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mediaBeanList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView im;
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            im = itemView.findViewById(R.id.mymusic_Image);
            tv = itemView.findViewById(R.id.mymusic_name);
        }
    }

    public void setCurrentMediaBean(MediaBean mediaBean) {
        this.mediaBean = mediaBean;
        notifyDataSetChanged();
    }

    public void setMediaBeanList(List<MediaBean> list) {
        mediaBeanList = list;
        notifyDataSetChanged();
    }

    public void setInter(ReturnCurrentMedia inter) {
        this.inter = inter;
    }

    public interface ReturnCurrentMedia {
        void currentMedia(MediaBean mediaBean);
    }
}
//public class MyMusicDrawerAdapter extends BaseAdapter {
//    private List<MediaBean> mediaBeanList;
//    private MediaBean mediaBean;
//    private Context context;
//    private LayoutInflater inflater;
//
//    public MyMusicDrawerAdapter(List<MediaBean> mediaBeanList, MediaBean mediaBean, Context context) {
//        this.mediaBeanList = mediaBeanList;
//        this.mediaBean = mediaBean;
//        this.context = context;
//        inflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    public int getCount() {
//        return mediaBeanList.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        MyViewHolder holder;
//        if (view == null) {
//            view = inflater.inflate(R.layout.mymusic_show_item, null, false);
//            holder = new MyViewHolder(view);
//            view.setTag(holder);
//        } else {
//            holder = (MyViewHolder) view.getTag();
//        }
//        final MediaBean bean = mediaBeanList.get(i);
//        Glide.with(context).load(bean.getPhotoUrl())
//                .placeholder(R.drawable.ic_defout)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .crossFade()
//                .into(holder.im);
//        holder.tv.setText(bean.getTitle());
//        if (bean.getUrl().equals(mediaBean.getUrl())) {
//            holder.tv.setTextColor(context.getResources().getColor(R.color.colorYellow));
//            holder.tv.setTextSize(19);
//        } else {
//            holder.tv.setTextColor(context.getResources().getColor(R.color.NORMAL));
//            holder.tv.setTextSize(17);
//        }
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("JYD", "onclick inter 111");
//            }
//        });
//        return view;
//    }
//
//    private class MyViewHolder extends RecyclerView.ViewHolder {
//        ImageView im;
//        TextView tv;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            im = itemView.findViewById(R.id.mymusic_Image);
//            tv = itemView.findViewById(R.id.mymusic_name);
//        }
//    }
//
//    public void setCurrentMediaBean(MediaBean mediaBean) {
//        this.mediaBean = mediaBean;
//        notifyDataSetChanged();
//    }
//}