package com.aait.aec.ui.forgot;

import com.aait.aec.R;
import com.aait.aec.data.DataManager;
import com.aait.aec.ui.base.BasePresenter;
import com.aait.aec.utils.CommonUtils;
import com.aait.aec.utils.rx.SchedulerProvider;
import com.google.android.gms.common.internal.service.Common;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ForgotPresenter<V extends ForgotMvpView> extends BasePresenter<V>
        implements ForgotMvpPresenter<V> {

    private static final String TAG = "RegisterPresenter";

    @Inject
    public ForgotPresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onLoginClicked() {
        getMvpView().openLoginActivity();
    }

    @Override
    public void onSubmitClicked(String email) {
        //validate email and password
        if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
            return;
        }
        if (!CommonUtils.isEmailValid(email)) {
            getMvpView().onError(R.string.invalid_email);
            return;
        }

        getMvpView().showMessage(R.string.password_reset_request_sent_to_registrar);
    }
}
