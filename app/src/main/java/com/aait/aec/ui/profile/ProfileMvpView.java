package com.aait.aec.ui.profile;

import com.aait.aec.ui.base.MvpView;

public interface ProfileMvpView extends MvpView {

    void loadProfile(String name, String email);
}
