package com.aait.aec.ui.addAnswerDialog;

import com.aait.aec.di.PerActivity;
import com.aait.aec.ui.base.MvpPresenter;

@PerActivity
public interface AddAnswerMvpPresenter<V extends AddAnswerMvpView> extends MvpPresenter<V> {

    void onCancelClicked();
    void onConfirmClicked();
}
