package com.jjc.dreamproject.presenter.movie;

import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.contract.movie.MovieContract;
import com.jjc.dreamproject.model.movie.MovieModel;

import java.util.List;

public class MoviePresenter extends MovieContract.Presenter {

    public MovieContract.Model attachModel() {
        return new MovieModel(this);
    }

    @Override
    public void disposeMovie(int page) {
        M().start(page);
    }

    @Override
    public void returnRequest(boolean isSuccess, List<MediaBean> beanList, String message) {
        V().showNewMovies(isSuccess,beanList,message);
    }
}

