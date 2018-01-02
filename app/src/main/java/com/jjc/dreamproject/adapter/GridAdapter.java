package com.jjc.dreamproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jjc.dreamproject.R;
import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.bean.UrlPhotoEntity;
import com.jjc.dreamproject.dialog.ShowImagesDialog;
import com.jjc.dreamproject.util.ToastUtil;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonbubble.LemonBubbleInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by JJC on 2017/12/1.
 */

public  class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<UrlPhotoEntity.DataBean> datas;//数据



    //适配器初始化
    public GridAdapter(Context context,List<UrlPhotoEntity.DataBean> datas) {
        mContext=context;
        this.datas=datas;
    }
    @Override
    public int getItemViewType(int position) {
            return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //根据item类别加载不同ViewHolder
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_meizi_item, parent, false);//这个布局就是一个imageview用来显示图片
        MyViewHolder holder = new MyViewHolder(view);
        //给布局设置点击和长点击监听
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //将数据与item视图进行绑定，如果是MyViewHolder就加载网络图片，如果是MyViewHolder2就显示页数
        if(holder instanceof MyViewHolder){

            Glide.with(mContext)
                    .load(datas.get(position).getUrl())
                    .placeholder(R.drawable.ic_defout)
                    .crossFade()
                    .into(((MyViewHolder) holder).iv);//加载网络图片
            ((MyViewHolder) holder).iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowImagesDialog showImagesDialog = new ShowImagesDialog(mContext,datas.get(position).getUrl(),datas.get(position).getType());
                    showImagesDialog.show();
                }
            });
            ((MyViewHolder) holder).iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    BmobQuery<MediaBean> bmobQuery = new BmobQuery<MediaBean>();
                    bmobQuery.addWhereEqualTo("url",datas.get(position).getUrl());
                    bmobQuery.setLimit(50);
                    bmobQuery.findObjects(new FindListener<MediaBean>() {
                        @Override
                        public void done(List<MediaBean> list, BmobException e) {
                            if(e==null){
                                if(list==null||list.size()==0){
                                    MediaBean mediaBean = new MediaBean();
                                    mediaBean.setTitle(datas.get(position).getType());
                                    mediaBean.setTylp(1);
                                    mediaBean.setUrl(datas.get(position).getUrl());
                                    mediaBean.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if(e==null){
                                                LemonBubble.showRight(mContext,"收藏成功",2*1000);
                                            }else{
                                                Log.d("JYD",e.toString());
                                                LemonBubble.showError(mContext,"收藏失败，请重试",2*1000);
                                            }
                                        }
                                    });

                                }else{
                                    LemonBubble.showError(mContext,"已收藏，请勿重复收藏",2*1000);
                                }
                            }else{
                                if(e.getErrorCode()==101){
                                    MediaBean mediaBean = new MediaBean();
                                    mediaBean.setTitle(datas.get(position).getType());
                                    mediaBean.setTylp(1);
                                    mediaBean.setUrl(datas.get(position).getUrl());
                                    mediaBean.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if(e==null){
                                                LemonBubble.showRight(mContext,"收藏成功",2*1000);
                                            }else{
                                                Log.d("JYD",e.toString());
                                                LemonBubble.showError(mContext,"收藏失败，请重试",2*1000);
                                            }
                                        }
                                    });
                                }else{

                                    LemonBubble.showError(mContext,"收藏失败，请重试",2*1000);
                                }
                                Log.d("JYD",e.toString());
                            }
                        }
                    });


                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount()
    {
        return datas.size();//获取数据的个数
    }


    //自定义ViewHolder，用于加载图片
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView iv;

        public MyViewHolder(View view)
        {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
        }
    }

    public void setData(List<UrlPhotoEntity.DataBean> list){
        int size = datas.size();
        datas = list;
        notifyItemChanged(size,datas.size()-1);
    }

}

