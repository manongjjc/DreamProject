package com.jjc.dreamproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjc.dreamproject.R;
import com.jjc.dreamproject.util.ToastUtil;
import com.jjc.dreamproject.view.movie.MovieActivity;
import com.jjc.dreamproject.view.music.MusicActivity;
import com.jjc.dreamproject.view.photo.PhotoShowActivity;

import java.util.List;

/**
 * Created by JJC on 2017/12/10.
 */

public class FirstActivityAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Integer> list;
    private LayoutInflater inflater;

    public FirstActivityAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = inflater.inflate(R.layout.item_first_activity, parent, false);
        ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;
        switch (list.get(position)){
            case 1:
                holder1.tv.setText("美图");
                holder1.iv.setImageResource(R.drawable.ic_photo);
                break;
            case 2:
                holder1.tv.setText("音乐");
                holder1.iv.setImageResource(R.drawable.ic_audio);
                break;
            case 3:
                holder1.tv.setText("电影");
                holder1.iv.setImageResource(R.drawable.ic_movie);
                break;
        }
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (list.get(position)){
                    case 1:
                        intent = new Intent();
                        intent.setClass(context, PhotoShowActivity.class);
                        context.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent();
                        intent.setClass(context, MusicActivity.class);
                        context.startActivity(intent);
                        break;
                    case 3:
//                        new ToastUtil().Short(context,"更新之后").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                        intent = new Intent();
                        intent.setClass(context, MovieActivity.class);
                        context.startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.frist_item_image);
            tv = itemView.findViewById(R.id.frist_item_titler);
        }
    }
}
