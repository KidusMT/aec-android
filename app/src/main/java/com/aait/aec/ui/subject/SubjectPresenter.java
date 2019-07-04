package com.aait.aec.ui.subject;

import com.aait.aec.data.DataManager;
import com.aait.aec.ui.base.BasePresenter;
import com.aait.aec.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SubjectPresenter<V extends SubjectMvpView> extends BasePresenter<V>
        implements SubjectMvpPresenter<V> {

    private static final String TAG = "RegisterPresenter";

    @Inject
    public SubjectPresenter(DataManager dataManager,
                            SchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
