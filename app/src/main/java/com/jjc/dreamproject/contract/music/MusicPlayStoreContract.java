package com.jjc.dreamproject.contract.music;

import android.content.Context;

import com.jjc.dreamproject.contract.BaseContract;
import com.jjc.dreamproject.view.lrc.LrcRow;

import java.util.List;

public interface MusicPlayStoreContract {

    interface Model {
        void getLrc(String songId, Context context);
    }

    interface View extends BaseContract.View {
        void showLrc(boolean isSuccess, List<LrcRow> rows);
    }

    abstract class Presenter extends BaseContract.Presenter<MusicPlayStoreContract.View, MusicPlayStoreContract.Model> {
        public abstract void startLrc(String songId,Context context);

        public abstract void startShowLrc(boolean isSuccess, List<LrcRow> rows);
    }
}

