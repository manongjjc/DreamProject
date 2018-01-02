package com.jjc.dreamproject.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.jjc.dreamproject.contract.BaseContract;
import com.jjc.dreamproject.util.UtilCode;

public class BaseActivity<T extends BaseContract.Presenter> extends MPermissionsActivity implements BaseContract.View {

    protected T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        presenter = bindPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
        if(UtilCode.EXACT_SCREEN_HEIGHT==320){
            getDeviceDensity();
        }
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    public T bindPresenter() {
        return null;
    }

    /**
     * 获取当前设备的屏幕密度等基本参数
     */
    private void getDeviceDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        UtilCode.EXACT_SCREEN_HEIGHT = metrics.heightPixels;
        UtilCode.EXACT_SCREEN_WIDTH = metrics.widthPixels;
    }
}

