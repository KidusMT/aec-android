package com.aait.aec.ui.profile;

import com.aait.aec.data.db.model.User;
import com.aait.aec.ui.base.MvpView;

public interface ProfileMvpView extends MvpView {

    void loadProfile(User user);
}
