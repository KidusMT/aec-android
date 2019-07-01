package com.aait.aec.ui.studentEdit;

import com.aait.aec.di.PerActivity;
import com.aait.aec.ui.base.MvpPresenter;

/**
 * Created by KidusMT.
 */

@PerActivity
public interface EditStudentMvpPresenter<V extends EditStudentMvpView> extends MvpPresenter<V> {

    void onSubmitClicked();

    void onCancelClicked();
}
