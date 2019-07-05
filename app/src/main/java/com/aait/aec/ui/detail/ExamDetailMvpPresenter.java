package com.aait.aec.ui.detail;

import com.aait.aec.di.PerActivity;
import com.aait.aec.ui.base.MvpPresenter;

@PerActivity
public interface ExamDetailMvpPresenter<V extends ExamDetailMvpView> extends MvpPresenter<V> {

    void onCancelClicked();
    void onConfirmClicked();
}
