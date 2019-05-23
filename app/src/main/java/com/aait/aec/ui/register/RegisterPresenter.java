/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.aait.aec.ui.register;

import com.aait.aec.R;
import com.aait.aec.data.DataManager;
import com.aait.aec.data.network.model.RegistrationRequest;
import com.aait.aec.ui.base.BasePresenter;
import com.aait.aec.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by janisharali on 27/01/17.
 */

public class RegisterPresenter<V extends RegisterMvpView> extends BasePresenter<V>
        implements RegisterMvpPresenter<V> {

    private static final String TAG = "RegisterPresenter";

    @Inject
    public RegisterPresenter(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onRegistrationClicked(String firstName, String lastName, String username, String sex, String password, String confirmPassword, String phoneNumber) {
        if (firstName == null || firstName.isEmpty()) {
            getMvpView().onError(R.string.empty_first_name);
            return;
        }

        if (username == null || username.isEmpty()) {
            getMvpView().onError(R.string.empty_username);
            return;
        }

        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        }

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            getMvpView().onError(R.string.empty_phone_number);
            return;
        }
        getMvpView().showLoading();


        getCompositeDisposable().add(getDataManager()
                .doRegistrationApiCall(new RegistrationRequest(firstName, lastName, phoneNumber, password, sex, username))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(registrationRequest -> {
                    if (!isViewAttached()) {
                        return;
                    }

                    // todo handle api response here

                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }

                    // todo handle api error here

                }));

    }
}
