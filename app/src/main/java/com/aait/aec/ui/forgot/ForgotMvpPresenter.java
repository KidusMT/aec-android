package com.aait.aec.ui.forgot;


import com.aait.aec.di.PerActivity;
import com.aait.aec.ui.base.MvpPresenter;

@PerActivity
public interface ForgotMvpPresenter<V extends ForgotMvpView> extends MvpPresenter<V> {

    void onLoginClicked();

    void onSubmitClicked(String email);

}
