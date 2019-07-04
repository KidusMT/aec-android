package com.aait.aec.ui.profile;

import com.aait.aec.data.DataManager;
import com.aait.aec.ui.base.BasePresenter;
import com.aait.aec.utils.CommonUtils;
import com.aait.aec.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class ProfilePresenter<V extends ProfileMvpView> extends BasePresenter<V>
        implements ProfileMvpPresenter<V> {

    private static final String TAG = "RegisterPresenter";

    @Inject
    public ProfilePresenter(DataManager dataManager,
                            SchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadProfileData() {
        String fullName = String.valueOf(getDataManager().getCurrentUserName());
        String email = String.valueOf(getDataManager().getCurrentUserEmail());
        String phoneNumber = String.valueOf(getDataManager().getCurrentUserEmail());
        String username = String.valueOf(getDataManager().getCurrentUserId());

        getMvpView().loadProfile(fullName, email, phoneNumber, username);
    }
}
