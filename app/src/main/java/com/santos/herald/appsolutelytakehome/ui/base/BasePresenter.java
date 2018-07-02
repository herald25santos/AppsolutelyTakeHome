package com.santos.herald.appsolutelytakehome.ui.base;

import android.support.annotation.NonNull;

public interface BasePresenter<V extends BaseView> extends BasePresenterLifecycle {
    void attachView(@NonNull V view);
}