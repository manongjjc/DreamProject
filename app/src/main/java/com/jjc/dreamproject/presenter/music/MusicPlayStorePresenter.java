package com.jjc.dreamproject.presenter.music;

import android.content.Context;

import com.jjc.dreamproject.contract.music.MusicPlayContract;
import com.jjc.dreamproject.contract.music.MusicPlayStoreContract;
import com.jjc.dreamproject.model.music.MusicPlayStoreModel;
import com.jjc.dreamproject.view.lrc.LrcRow;

import java.util.List;

public class MusicPlayStorePresenter extends MusicPlayStoreContract.Presenter {


    @Override
    public void startLrc(String songId, Context context) {
        M().getLrc(songId,context);
    }

    @Override
    public void startShowLrc(boolean isSuccess, List<LrcRow> rows) {
        V().showLrc(isSuccess,rows);
    }


    @Override
    public MusicPlayStoreContract.Model attachModel() {
        return new MusicPlayStoreModel(this);
    }
}

