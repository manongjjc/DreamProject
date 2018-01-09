package com.jjc.dreamproject.view.test;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjc.dreamproject.R;

import me.yokeyword.fragmentation.SupportFragment;


public class TestFragment extends SupportFragment {


    public TestFragment() {
        // Required empty public constructor
    }

    private TestFragment newInstance(){
        return new TestFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.test_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWithPop(newInstance());
            }
        });
    }

}
