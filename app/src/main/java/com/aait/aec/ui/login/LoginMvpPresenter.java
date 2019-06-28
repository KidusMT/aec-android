package com.aait.aec.ui.login;

import com.aait.aec.di.PerActivity;
import com.aait.aec.ui.base.MvpPresenter;

@PerActivity
public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void onServerLoginClick(String email, String password);

    void onDecideNextActivity();

}
