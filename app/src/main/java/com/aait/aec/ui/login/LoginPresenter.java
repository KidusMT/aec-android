package com.aait.aec.ui.login;

import com.aait.aec.R;
import com.aait.aec.data.DataManager;
import com.aait.aec.data.network.model.LoginRequest;
import com.aait.aec.ui.base.BasePresenter;
import com.aait.aec.utils.CommonUtils;
import com.aait.aec.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by janisharali on 27/01/17.
 */

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {

    private static final String TAG = "RegisterPresenter";

    @Inject
    public LoginPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onServerLoginClick(String email, String password) {
//        validate email and password
        if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_username);
            return;
        }
//        if (!CommonUtils.isEmailValid(email)) {
//            getMvpView().onError(R.string.invalid_email);
//            return;
//        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        }
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .doServerLoginApiCall(new LoginRequest(email, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getDataManager().updateUserInfo(
                            response.getId(),
                            response.getUserId(),
                            DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                            String.format("%s %s",response.getUser().getFirstName(),  response.getUser().getLastName()),
                            response.getUser().getPhoneNo());


                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().openMainActivity();

                }, throwable -> {

                    if (!isViewAttached())
                        return;

                    getMvpView().hideLoading();
                    getMvpView().onError(CommonUtils.getErrorMessage(throwable));

                }));
    }

    public void onDecideNextActivity() {
        if (getDataManager().getCurrentUserLoggedInMode()
                != DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType()) {
            getMvpView().openMainActivity();
        }
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
//        onDecideNextActivity();
    }

}
