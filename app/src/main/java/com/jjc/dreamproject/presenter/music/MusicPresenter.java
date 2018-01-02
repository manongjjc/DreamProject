package com.jjc.dreamproject.presenter.music;

import com.jjc.dreamproject.bean.HitMusicEntity;
import com.jjc.dreamproject.contract.music.MusicContract;
import com.jjc.dreamproject.model.music.MusicModel;

public class MusicPresenter extends MusicContract.Presenter {

    public MusicContract.Model attachModel() {
        return new MusicModel(this);
    }

    @Override
    public void startHotTop() {
        M().getHotTopMusic();
    }

    @Override
    public void startNewMusic() {
        M().getNewMusic();
    }

    @Override
    public void retrunHotTop(boolean isSucess ,HitMusicEntity musicEntity,String alert) {
        V().showHotTopMusic(isSucess,musicEntity,alert);
    }

    @Override
    public void retrunNewMusic(boolean isSucess ,HitMusicEntity musicEntity,String alert) {
        V().showNewMusic(isSucess,musicEntity,alert);
    }
}

