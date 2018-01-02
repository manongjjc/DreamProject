package com.jjc.dreamproject.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jjc.dreamproject.R;
import com.jjc.dreamproject.adapter.GridAdapter;
import com.jjc.dreamproject.util.UtilCode;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by JJC on 2017/12/4.
 */

public class ShowImagesDialog extends Dialog {

    private View mView ;
    private Context mContext;
    private TextView mIndexText;
    private String mImgUrls;
    private String mTitles;
    private PhotoView mPhoto;

    public ShowImagesDialog(@NonNull Context context, String imgUrls,String title) {
        super(context, R.style.transparentBgDialog);
        this.mContext = context;
        this.mImgUrls = imgUrls;
        this.mTitles = title;
        initView();
        initData();
    }

    private void initView() {
        mView = View.inflate(mContext, R.layout.dialog_images_brower, null);
        mIndexText = (TextView) mView.findViewById(R.id.tv_image_title);
        mPhoto = mView.findViewById(R.id.photo_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        wl.height = UtilCode.EXACT_SCREEN_HEIGHT;
        wl.width = UtilCode.EXACT_SCREEN_WIDTH;
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
    }

    private void initData() {
        Glide.with(mContext)
                .load(mImgUrls)
                .placeholder(R.drawable.ic_defout)
                .crossFade()
                .into(mPhoto);//加载网络图片
    }

}