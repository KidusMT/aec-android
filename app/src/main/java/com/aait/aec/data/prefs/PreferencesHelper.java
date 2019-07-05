package com.aait.aec.data.prefs;

import com.aait.aec.data.DataManager;


public interface PreferencesHelper {

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    String getCurrentUserId();

    void setCurrentUserId(String userId);

    String getCurrentUserName();

    void setCurrentUserName(String userName);

    String getCurrentUserEmail();

    void setCurrentUserEmail(String email);

    String getCurrentUserProfilePicUrl();

    void setCurrentUserProfilePicUrl(String profilePicUrl);

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getCurrentLanguage();

    void setCurrentLanguage(String language);

}
