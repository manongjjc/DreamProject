package com.jjc.dreamproject.adapter.music;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.jjc.dreamproject.bean.QueryMusicEntity;
import com.jjc.dreamproject.util.SharedPreferencesUtil;
import com.jjc.dreamproject.util.ToastUtil;
import com.jjc.dreamproject.util.UrlApi;
import com.jjc.dreamproject.util.UtilCode;
import com.jjc.dreamproject.view.music.MusicPlayActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by JJC on 2017/12/10.
 */

public class MusicQueryAdapter extends BaseAdapter {
    private List<QueryMusicEntity.DataBean.SongBean.ListBean> songs;
    private String key;
    private Context context;
    private LayoutInflater inflater;

    public MusicQueryAdapter(List<QueryMusicEntity.DataBean.SongBean.ListBean> songs, Context context,String key) {
        this.songs = songs;
        this.context = context;
        this.key = key;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.music_query_item,null);
            holder.iv = view.findViewById(R.id.music_query_imgae);
            holder.name = view.findViewById(R.id.music_query_name);
            holder.songer = view.findViewById(R.id.music_query_songer);
            holder.view = view;
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        String f = songs.get(i).getF();
        String[] split = f.split("\\|");
        String singId = null;
        String PhotoId = null;
        try {
            singId = split[0];
            PhotoId = split[4];
        }catch (Exception e){
            singId = "0";
            PhotoId = "0";
        }

        final String name = songs.get(i).getFsong();
        final String singer = songs.get(i).getFsinger();
        Log.d("JYD",singer);
        Glide.with(context).load(UrlApi.getMusicPictrue(300,Integer.parseInt(PhotoId)))
                .placeholder(R.drawable.ic_defout)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(holder.iv);
        holder.name.setText(name);
        holder.songer.setText(singer);
        final String finalSingId = singId;
        final String finalPhotoId = PhotoId;
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sp = SharedPreferencesUtil.getInstance(context).getSP(UtilCode.SEARCH_HISTORY);
                String[] split1 = sp.split(",");
                if(split1!=null){
                    if(split1.length==0||sp.equals("")){
                        SharedPreferencesUtil.getInstance(context).putSP(UtilCode.SEARCH_HISTORY,key);
                    }else{
                        List<String> list = Arrays.asList(split1);
                        if(!list.contains(key))
                            SharedPreferencesUtil.getInstance(context).putSP(UtilCode.SEARCH_HISTORY,sp+","+key);
                    }
                }else{
                    SharedPreferencesUtil.getInstance(context).putSP(UtilCode.SEARCH_HISTORY,key);
                }
                String musicAdress = UrlApi.getMusicAdress(Integer.parseInt(finalSingId));
                Intent intent = new Intent();
                intent.setClass(context, MusicPlayActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("url",musicAdress);
                intent.putExtra("photoUrl",UrlApi.getMusicPictrue(500,Integer.parseInt(finalPhotoId)));
                intent.putExtra("lyricId", finalSingId);
                intent.putExtra("singerName",singer);
                context.startActivity(intent);
            }
        });
        return view;
    }

    private class ViewHolder{
        ImageView iv;
        TextView name;
        TextView songer;
        View view;
    }

    public void setData(List<QueryMusicEntity.DataBean.SongBean.ListBean> songs){
        int size = this.songs.size();
        if(size>=songs.size()){
            new ToastUtil().Short(context,"已获取全部数据~~~").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
        }else{
            for (int i = size; i < songs.size(); i++) {
                this.songs.add(songs.get(i));
            }
        }
        notifyDataSetChanged();
    }
}
