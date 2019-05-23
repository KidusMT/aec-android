package com.aait.aec.data.network;

import javax.inject.Singleton;

@Singleton
public class ApiHeader {

    public static final String API_AUTH_TYPE = "API_AUTH_TYPE";
    public static final String PUBLIC_API = "PUBLIC_API";
    public static final String PROTECTED_API = "PROTECTED_API";

    public static final String HEADER_PARAM_API_KEY = "api_key";
    public static final String HEADER_PARAM_ACCESS_TOKEN = "Authorization";
    public static final String HEADER_PARAM_USER_ID = "user_id";

    private String mAccessToken;

    public ApiHeader(String mAccessToken) {
        this.mAccessToken = mAccessToken;
    }


    public String getAccessToken() {
        return "Bearer "+ mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

}
