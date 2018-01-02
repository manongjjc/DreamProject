package com.jjc.dreamproject.adapter.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjc.dreamproject.R;
import com.jjc.dreamproject.util.SharedPreferencesUtil;
import com.jjc.dreamproject.util.UtilCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJC on 2017/12/10.
 */

public class MusicSearchHistory extends BaseAdapter {
    private List<String> historys;
    private Context context;
    private LayoutInflater inflater;

    public MusicSearchHistory(List<String> historys, Context context) {
        this.historys = historys;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return historys.size();
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
        HolderTitile holderTitile = null;
        HolderBody holderBody = null;
        if(view==null){
            if(i==0){
                view = inflater.inflate(R.layout.item_history_tilter,null);
                holderTitile = new HolderTitile();
                holderTitile.tv = view.findViewById(R.id.historyTilter);
                holderTitile.iv = view.findViewById(R.id.historyDelete);
                view.setTag(holderTitile);

            }else{
                view = inflater.inflate(R.layout.item_history_body,null);
                holderBody = new HolderBody();
                holderBody.tv = view.findViewById(R.id.historyName);
                view.setTag(holderBody);
            }
        }else {
            if(i==0){
                holderTitile = (HolderTitile) view.getTag();
            }else{
                holderBody = (HolderBody) view.getTag();
            }
        }
        if(i==0){
            holderTitile.tv.setText(historys.get(i));
            if(historys.size()==1){
                holderTitile.iv.setVisibility(View.GONE);
            }else{
                holderTitile.iv.setVisibility(View.VISIBLE);
                holderTitile.iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<String> strings = new ArrayList<>();
                        strings.add("搜索历史");
                        setHistorys(strings);
                        SharedPreferencesUtil.getInstance(context).removeSP(UtilCode.SEARCH_HISTORY);
                    }
                });
            }
        }else{
            holderBody.tv.setText(historys.get(i));
        }
        return view;
    }

    public void setHistorys(List<String> historys){
        this.historys = historys;
        notifyDataSetChanged();
    }
    private class HolderTitile{
        TextView tv;
        ImageView iv;
    }

    private class HolderBody{
        TextView tv;
    }
}
