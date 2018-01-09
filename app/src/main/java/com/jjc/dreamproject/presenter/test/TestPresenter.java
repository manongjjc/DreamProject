package com.jjc.dreamproject.presenter.test;

import com.jjc.dreamproject.contract.test.TestContract;
import com.jjc.dreamproject.model.test.TestModel;

public class TestPresenter extends TestContract.Presenter {

    public TestContract.Model attachModel() {
        return new TestModel();
    }
}

