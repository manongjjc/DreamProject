package com.jjc.dreamproject.view.test;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjc.dreamproject.R;

import me.yokeyword.fragmentation.SupportFragment;


public class NewTestFragment extends SupportFragment {


    public NewTestFragment() {
        // Required empty public constructor
    }

    private NewTestFragment newInstance() {
        return new NewTestFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_newtest, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState != null) {
            //data recovery
        }

    }

    private void initView(View view) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //The exception status data is saved.
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        //fragment  show
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        //fragment hide
    }
}

