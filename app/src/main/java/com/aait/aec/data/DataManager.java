package com.aait.aec.data;

import com.aait.aec.data.db.DbHelper;
import com.aait.aec.data.network.ApiHelper;
import com.aait.aec.data.prefs.PreferencesHelper;

import io.reactivex.Observable;

public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {

    void updateApiHeader(String accessToken);

    void setUserAsLoggedOut();

    void updateUserInfo(
            String accessToken,
            String userId,
            LoggedInMode loggedInMode,
            String userName,
            String phoneNumber);

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
