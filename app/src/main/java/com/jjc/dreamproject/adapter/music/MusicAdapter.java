package com.jjc.dreamproject.adapter.music;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jjc.dreamproject.R;
import com.jjc.dreamproject.bean.HitMusicEntity;
import com.jjc.dreamproject.util.UrlApi;
import com.jjc.dreamproject.view.music.MusicPlayActivity;

/**
 * Created by JJC on 2017/12/7.
 */

public class MusicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private HitMusicEntity entity;
    private Context context;

    public MusicAdapter(HitMusicEntity entity, Context context) {
        this.entity = entity;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.music_item, parent, false);
        MusicViewHolder holder = new MusicViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HitMusicEntity.SonglistBean songlistBean = entity.getSonglist().get(position);
        MusicViewHolder viewHolder = (MusicViewHolder) holder;
        viewHolder.musicName.setText(songlistBean.getSongName());
        viewHolder.musicSpesher.setText(songlistBean.getAlbumName());
        Glide.with(context).load(UrlApi.getMusicPictrue(300, Integer.parseInt(songlistBean.getAlbumId())))
                .placeholder(R.drawable.ic_defout)
                .crossFade()
                .into(viewHolder.musicPhoto);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String musicAdress = UrlApi.getMusicAdress(Integer.parseInt(songlistBean.getId()));
                Intent intent = new Intent();
                intent.setClass(context, MusicPlayActivity.class);
                intent.putExtra("name", songlistBean.getSongName());
                intent.putExtra("url", musicAdress);
                intent.putExtra("photoUrl", UrlApi.getMusicPictrue(500, Integer.parseInt(songlistBean.getAlbumId())));
                intent.putExtra("lyricId", songlistBean.getId());
                intent.putExtra("singerName", songlistBean.getSingerName());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return entity.getSonglist().size();
    }

    class MusicViewHolder extends RecyclerView.ViewHolder {
        private TextView musicName;
        private TextView musicSpesher;
        private ImageView musicPhoto;
        private View view;

        public MusicViewHolder(View itemView) {
            super(itemView);
            musicName = itemView.findViewById(R.id.music_name);
            musicSpesher = itemView.findViewById(R.id.music_special);
            musicPhoto = itemView.findViewById(R.id.music_imgae);
            view = itemView;
        }
    }

    public void setEntity(HitMusicEntity entity) {
        this.entity = entity;
        notifyDataSetChanged();

    }
}
