package com.jjc.dreamproject.view.test;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjc.dreamproject.R;

import me.yokeyword.fragmentation.SupportFragment;


public class FiveFragment extends SupportFragment {


    public FiveFragment() {
        // Required empty public constructor
    }

    private FiveFragment newInstance() {
        return new FiveFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_five, container, false);
        Log.d("JYD","five  onCreateView");
        initView(view);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if(savedInstanceState!=null){
            //数据恢复
        }

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        Log.d("JYD","five onSupportInvisible");

    }

    private void initView(View view) {

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //数据保存
    }

}

