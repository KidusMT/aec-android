package com.aait.aec.ui.main;

import com.aait.aec.di.PerActivity;
import com.aait.aec.ui.base.MvpPresenter;

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    void onDrawerOptionAboutClick();

    void onDrawerOptionLogoutClick();

    void onDrawerCourseClick();

    void onDrawerStudentsClick();

    void onViewInitialized();

    void onNavMenuCreated();

    void onDrawerOptionSettingsClicked();

    void loadExams();
}
