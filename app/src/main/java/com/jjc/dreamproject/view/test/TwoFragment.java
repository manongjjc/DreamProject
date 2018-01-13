package com.jjc.dreamproject.view.test;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjc.dreamproject.R;

import me.yokeyword.fragmentation.SupportFragment;


public class TwoFragment extends SupportFragment {


    public TwoFragment() {
        // Required empty public constructor
    }

    private TwoFragment newInstance() {
        return new TwoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        Log.d("JYD","two  onCreateView");
        initView(view);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Log.d("JYD","two onLazyInitView");
        if(savedInstanceState!=null)
            Log.d("JYD","two  savedInstanceState  =  "+savedInstanceState.getBoolean("test",false));
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        Log.d("JYD","two onSupportInvisible");
    }
    private void initView(View view) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("test",true);
        Log.d("JYD","two onSaveInstanceState");
    }

}

