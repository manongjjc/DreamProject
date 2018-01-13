package com.jjc.dreamproject.view.test;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjc.dreamproject.R;

import me.yokeyword.fragmentation.SupportFragment;


public class OneFragment extends SupportFragment {


    public OneFragment() {
        // Required empty public constructor
    }

    private OneFragment newInstance() {
        return new OneFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        Log.d("JYD","one  onCreateView");
        initView(view);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Log.d("JYD","one onLazyInitView");
        if(savedInstanceState!=null){

            Log.d("JYD","one  savedInstanceState  =  "+savedInstanceState.getBoolean("test",false));
        }else{
            Log.d("JYD","one  savedInstanceState == null");
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        //fragment展示
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        //fragment隐藏
    }

    private void initView(View view) {

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("test",true);
        Log.d("JYD","one onSaveInstanceState");
    }

}

