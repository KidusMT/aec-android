package com.aait.aec.data.network;

import com.aait.aec.data.network.model.BlogResponse;
import com.aait.aec.data.network.model.LoginRequest;
import com.aait.aec.data.network.model.LoginResponse;
import com.aait.aec.data.network.model.LogoutResponse;
import com.aait.aec.data.network.model.OpenSourceResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by janisharali on 28/01/17.
 */

@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;
    private ApiCall mApiCall;

    @Inject
    public AppApiHelper(ApiHeader apiHeader, ApiCall apiCall) {
        mApiHeader = apiHeader;
        mApiCall = apiCall;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Observable<LoginResponse> doServerLoginApiCall(LoginRequest request) {
        return mApiCall.login(request);
    }


    @Override
    public Single<LogoutResponse> doLogoutApiCall() {
        return null;
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return null;
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return null;
    }
}

