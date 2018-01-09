package com.jjc.dreamproject.contract.test;

import com.jjc.dreamproject.contract.BaseContract;

public interface TestContract {

    interface Model {

    }

    interface View extends BaseContract.View {

    }

    abstract class Presenter extends BaseContract.Presenter<View, Model> {

    }
}

