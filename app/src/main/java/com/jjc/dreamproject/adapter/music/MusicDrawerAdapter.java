package com.jjc.dreamproject.adapter.music;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjc.dreamproject.R;

import java.util.List;


/**
 * Created by JJC on 2017/12/9.
 */

public class MusicDrawerAdapter extends BaseAdapter {
    private List<Integer> tylps;
    private Context context;
    private LayoutInflater inflater;

    public MusicDrawerAdapter(List<Integer> tylps,Context context) {
        this.tylps = tylps;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tylps.size();
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
            view = inflater.inflate(R.layout.music_drawer_item,null);
            holder = new ViewHolder();
            holder.iv = view.findViewById(R.id.item_drawer_img);
            holder.tv = view.findViewById(R.id.item_drawer_titile);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        Integer intent = tylps.get(i);
        switch (intent){
            case 1:
                holder.tv.setText("排行榜");
                holder.iv.setImageResource(R.drawable.ic_hot);
                break;
            case 2:
                holder.tv.setText("最新歌曲");
                holder.iv.setImageResource(R.drawable.ic_new_top);
                break;
            case 3:
                holder.tv.setText("搜索");
                holder.iv.setImageResource(R.drawable.ic_search);
                break;
        }
        return view;
    }
    private class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}
