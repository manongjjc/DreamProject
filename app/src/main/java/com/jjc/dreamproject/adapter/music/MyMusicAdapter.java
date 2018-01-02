package com.jjc.dreamproject.adapter.music;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jjc.dreamproject.R;
import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.observer.StoreMusicObserver;
import com.jjc.dreamproject.util.UrlApi;
import com.jjc.dreamproject.view.music.MusicPlayActivity;
import com.jjc.dreamproject.view.music.MusicPlayStoreActivity;

import java.util.List;

/**
 * Created by JJC on 2017/12/13.
 */

public class MyMusicAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<MediaBean> beanList;
    private LayoutInflater inflater;

    public MyMusicAdapter(Context context, List<MediaBean> beanList) {
        this.context = context;
        this.beanList = beanList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.music_query_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder myHolder = (ViewHolder) holder;
        final MediaBean mediaBean = beanList.get(position);
        myHolder.songer.setText(mediaBean.getSingerName());
        myHolder.name.setText(mediaBean.getTitle());
        Glide.with(context).load(mediaBean.getPhotoUrl())
                .placeholder(R.drawable.ic_defout)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(myHolder.iv);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, MusicPlayStoreActivity.class);
                intent.putExtra("current",mediaBean);
                StoreMusicObserver.instance().addList(beanList);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView name;
        TextView songer;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.music_query_imgae);
            name = itemView.findViewById(R.id.music_query_name);
            songer = itemView.findViewById(R.id.music_query_songer);
            view =itemView;
        }
    }

    public void addData(List<MediaBean> list){
        this.beanList.addAll(list);
        notifyDataSetChanged();
    }
}
