package com.jjc.dreamproject.contract.music;

import com.jjc.dreamproject.bean.HitMusicEntity;
import com.jjc.dreamproject.contract.BaseContract;

public interface MusicContract {

    interface Model {
        void getHotTopMusic();

        void getNewMusic();
    }

    interface View extends BaseContract.View {
        void showHotTopMusic(boolean isSucess ,HitMusicEntity musicEntity,String alert);

        void showNewMusic(boolean isSucess ,HitMusicEntity musicEntity,String alert);
    }

    abstract class Presenter extends BaseContract.Presenter<View, Model> {
        public abstract void startHotTop();

        public abstract void startNewMusic();

        public abstract void retrunHotTop(boolean isSucess ,HitMusicEntity musicEntity,String alert);

        public abstract void retrunNewMusic(boolean isSucess ,HitMusicEntity musicEntity,String alert);
    }
}

