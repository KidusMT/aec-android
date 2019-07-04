package com.aait.aec.ui.subject;


import com.aait.aec.di.PerActivity;
import com.aait.aec.ui.base.MvpPresenter;

@PerActivity
public interface SubjectMvpPresenter<V extends SubjectMvpView> extends MvpPresenter<V> {
}
