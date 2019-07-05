package com.aait.aec.ui.settings;

import com.aait.aec.data.DataManager;
import com.aait.aec.ui.base.BasePresenter;
import com.aait.aec.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SettingsPresenter<V extends SettingsMvpView> extends BasePresenter<V>
        implements SettingsMvpPresenter<V> {

    private static final String TAG = SettingsPresenter.class.getSimpleName();

    @Inject
    public SettingsPresenter(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }


    @Override
    public void resolveCurrentLanguge() {
        getMvpView().setCurrentLanguage(getDataManager().getCurrentLanguage());

    }

}
