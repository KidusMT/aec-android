package com.aait.aec.ui.addAnswerDialog;

import com.aait.aec.data.DataManager;
import com.aait.aec.ui.base.BasePresenter;
import com.aait.aec.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AddAnswerPresenter<V extends AddAnswerMvpView> extends BasePresenter<V>
        implements AddAnswerMvpPresenter<V> {

    private static final String TAG = "SplashPresenter";

    @Inject
    public AddAnswerPresenter(DataManager dataManager,
                              SchedulerProvider schedulerProvider,
                              CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onCancelClicked() {
        getMvpView().closeDialog();
    }

    @Override
    public void onConfirmClicked() {
        getMvpView().openConfirmReturnDialog();
    }

}
