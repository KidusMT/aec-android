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

package com.aait.aec.ui.result;

import com.aait.aec.data.DataManager;
import com.aait.aec.ui.base.BasePresenter;
import com.aait.aec.utils.CommonUtils;
import com.aait.aec.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MultipartBody;

/**
 * Created by janisharali on 27/01/17.
 */

public class ResultPresenter<V extends ResultMvpView> extends BasePresenter<V>
        implements ResultMvpPresenter<V> {

    private static final String TAG = "RegisterPresenter";

    @Inject
    public ResultPresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

        getMvpView().showLoading();
        loadStudentsFromDb();
    }

    @Override
    public void onUploadClicked(String container, List<MultipartBody.Part> parts) {

        getCompositeDisposable().add(getDataManager()
                .upload(container, parts)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(imageUploadResponse -> {

                        },
                        throwable -> {

                            if (!isViewAttached())
                                return;

                            getMvpView().hideLoading();
                            getMvpView().onError(CommonUtils.getErrorMessage(throwable));
                        }));
    }

    @Override
    public void loadStudentsFromDb() {
        getMvpView().showLoading();
        getMvpView().showStudents(getDataManager().getStudents().blockingFirst());
    }
}
