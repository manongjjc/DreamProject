package com.jjc.dreamproject.contract.movie;

import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.contract.BaseContract;

import java.util.List;

public interface MovieContract {

    interface Model {
        void start(int page);
    }

    interface View extends BaseContract.View {
        void showNewMovies(boolean isSuccess, List<MediaBean> beanList,String message);
    }

    abstract class Presenter extends BaseContract.Presenter<View, Model> {
        public abstract void disposeMovie(int page);

        public abstract void returnRequest(boolean isSuccess, List<MediaBean> beanList,String message);
    }
}

