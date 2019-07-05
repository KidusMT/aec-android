package com.aait.aec.ui.splash;

import com.aait.aec.ui.base.MvpView;

public interface SplashMvpView extends MvpView {

    void openLoginActivity();

    void openMainActivity();

    void startSyncService();
}
