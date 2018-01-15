package com.jjc.dreamproject.view;

import android.content.Context;
import android.os.Bundle;

import me.yokeyword.fragmentation.SupportFragment;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjc.dreamproject.contract.BaseContract;

public class BaseFragment<T extends BaseContract.Presenter> extends SupportFragment implements BaseContract.View {

    protected T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = bindPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroyView();
    }

    public T bindPresenter() {
        return null;
    }
}

