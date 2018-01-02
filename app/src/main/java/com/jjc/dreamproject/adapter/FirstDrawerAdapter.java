package com.jjc.dreamproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjc.dreamproject.R;

import java.util.List;

/**
 * Created by JJC on 2017/12/12.
 */

public class FirstDrawerAdapter extends BaseAdapter {
    private List<Integer> keys;
    private Context context;
    private LayoutInflater inflater;

    public FirstDrawerAdapter(List<Integer> keys, Context context) {
        this.keys = keys;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return keys.size();
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
        if (view == null) {
            view = inflater.inflate(R.layout.music_drawer_item, null);
            holder = new ViewHolder();
            holder.iv = view.findViewById(R.id.item_drawer_img);
            holder.tv = view.findViewById(R.id.item_drawer_titile);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        switch (keys.get(i)) {
            case 1:
                //我的美图
                holder.tv.setText("我的美图");
                holder.iv.setImageResource(R.drawable.ic_photo_first);
                break;
            case 2:
                //我的音乐
                holder.tv.setText("我的音乐");
                holder.iv.setImageResource(R.drawable.ic_audio_first);
                break;
            case 3:
                //我的视频
                holder.tv.setText("我的视频");
                holder.iv.setImageResource(R.drawable.ic_movie_first);
                break;
        }
        return view;
    }

    private class ViewHolder {
        ImageView iv;
        TextView tv;
    }
}
