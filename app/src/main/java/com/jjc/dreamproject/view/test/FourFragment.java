package com.jjc.dreamproject.view.test;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjc.dreamproject.R;

import me.yokeyword.fragmentation.SupportFragment;


public class FourFragment extends SupportFragment {


    public FourFragment() {
        // Required empty public constructor
    }

    private FourFragment newInstance() {
        return new FourFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        Log.d("JYD","four  onCreateView");
        initView(view);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Log.d("JYD","four onLazyInitView");
        if(savedInstanceState!=null)
            Log.d("JYD","four savedInstanceState  =  "+savedInstanceState.getBoolean("test",false));
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        Log.d("JYD","four onSupportInvisible");
    }
    private void initView(View view) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("test",true);
        Log.d("JYD","four onSaveInstanceState");
    }
}

