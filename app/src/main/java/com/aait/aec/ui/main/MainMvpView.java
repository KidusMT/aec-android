package com.aait.aec.ui.main;

import com.aait.aec.data.network.model.exam.Exam;
import com.aait.aec.ui.base.MvpView;

import java.util.List;


public interface MainMvpView extends MvpView {

    void openLoginActivity();

    void updateUserName(String currentUserName);

    void updateUserEmail(String currentUserEmail);

    void updateUserProfilePic(String currentUserProfilePicUrl);

    void updateAppVersion();

    void openStudentsActivity();

    void closeNavigationDrawer();

    void openMyFeedActivity();

    void lockDrawer();

    void unlockDrawer();

    void openProfileActivity();

    void showExams(List<Exam> exams);
}
