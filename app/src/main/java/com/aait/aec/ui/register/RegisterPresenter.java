package com.aait.aec.ui.register;

import com.aait.aec.R;
import com.aait.aec.data.DataManager;
import com.aait.aec.data.network.model.RegistrationRequest;
import com.aait.aec.ui.base.BasePresenter;
import com.aait.aec.utils.CommonUtils;
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
    public void onRegistrationClicked(String firstName, String lastName, String username, String password, String confirmPassword, String phoneNumber) {
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
                .doRegistrationApiCall(new RegistrationRequest(firstName, lastName, phoneNumber, password, username))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(registrationRequest -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().openLoginActivity();

                }, throwable -> {

                    if (!isViewAttached())
                        return;

                    getMvpView().hideLoading();
                    getMvpView().onError(CommonUtils.getErrorMessage(throwable));

                }));

    }
}
