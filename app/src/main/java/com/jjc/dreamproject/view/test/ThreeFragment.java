package com.jjc.dreamproject.view.test;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjc.dreamproject.R;

import me.yokeyword.fragmentation.SupportFragment;


public class ThreeFragment extends SupportFragment {


    public ThreeFragment() {
        // Required empty public constructor
    }

    private ThreeFragment newInstance() {
        return new ThreeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        Log.d("JYD","Three  onCreateView");
        initView(view);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Log.d("JYD","three onLazyInitView");
        if(savedInstanceState!=null)
            Log.d("JYD","three savedInstanceState  =  "+savedInstanceState.getBoolean("test",false));
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        Log.d("JYD","three onSupportInvisible");
    }

    private void initView(View view) {

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("test",true);
        Log.d("JYD","three onSaveInstanceState");
    }

}

