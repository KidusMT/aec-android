package com.aait.aec.ui.settings;

import com.aait.aec.di.PerActivity;
import com.aait.aec.ui.base.MvpPresenter;

@PerActivity
public interface SettingsMvpPresenter<V extends SettingsMvpView> extends MvpPresenter<V> {

    void resolveCurrentLanguage();

}
