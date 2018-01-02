package com.jjc.dreamproject.contract;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public interface BaseContract {

    interface View {
        Context getContext();

        Activity getActivity();
    }

    abstract class Presenter<V extends View, M> {

        protected M model;

        protected Reference<V> viewRef;

        public Presenter() {
            model = attachModel();
        }

        public abstract M attachModel();

        public void attachView(V view) {
            viewRef = new WeakReference<V>(view);
        }

        public void detachView() {
            if (viewRef != null) {
                viewRef.clear();
                viewRef = null;
            }
        }

        protected V V() {
            return viewRef.get();
        }

        protected M M() {
            return model;
        }

        public boolean isAttached() {
            return viewRef != null && viewRef.get() != null;
        }

    }
}

