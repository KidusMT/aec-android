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

package com.aait.aec.ui.main;

import com.aait.aec.data.DataManager;
import com.aait.aec.ui.base.BasePresenter;
import com.aait.aec.utils.CommonUtils;
import com.aait.aec.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by janisharali on 27/01/17.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    private static final String TAG = "MainPresenter";

    @Inject
    public MainPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onDrawerOptionAboutClick() {
        getMvpView().closeNavigationDrawer();
    }

    @Override
    public void onDrawerOptionLogoutClick() {
        getMvpView().showLoading();

        getDataManager().setUserAsLoggedOut();
        getMvpView().hideLoading();
        getMvpView().openLoginActivity();

    }

    @Override
    public void onViewInitialized() {

    }

    @Override
    public void onNavMenuCreated() {
        if (!isViewAttached()) {
            return;
        }
        getMvpView().updateAppVersion();

        final String currentUserName = getDataManager().getCurrentUserName();
        if (currentUserName != null && !currentUserName.isEmpty()) {
            getMvpView().updateUserName(currentUserName);
        }

        final String currentUserEmail = getDataManager().getCurrentUserEmail();
        if (currentUserEmail != null && !currentUserEmail.isEmpty()) {
            getMvpView().updateUserEmail(currentUserEmail);
        }

        final String profilePicUrl = getDataManager().getCurrentUserProfilePicUrl();
        if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
            getMvpView().updateUserProfilePic(profilePicUrl);
        }
    }

    @Override
    public void loadExams() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getExams().subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(exams -> {
                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().showExams(exams);
//
//                    Log.e(TAG, String.format("userId:===> %s", getDataManager().getCurrentUserId()));
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().onError(CommonUtils.getErrorMessage(throwable));
                }));
    }

    @Override
    public void onDrawerCourseClick() {
        getMvpView().closeNavigationDrawer();
        getMvpView().openMyFeedActivity();
    }

    @Override
    public void onDrawerStudentsClick() {
        getMvpView().closeNavigationDrawer();
        getMvpView().openStudentsActivity();
    }
}
