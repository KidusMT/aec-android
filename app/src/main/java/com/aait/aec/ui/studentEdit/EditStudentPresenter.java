package com.aait.aec.ui.studentEdit;

import com.aait.aec.data.DataManager;
import com.aait.aec.ui.base.BasePresenter;
import com.aait.aec.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by KidusMT.
 */

public class EditStudentPresenter<V extends EditStudentMvpView> extends BasePresenter<V>
        implements EditStudentMvpPresenter<V> {

    private static final String TAG = "QRScanPresenter";

    @Inject
    public EditStudentPresenter(DataManager dataManager,
                                SchedulerProvider schedulerProvider,
                                CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onSubmitClicked() {

    }

    @Override
    public void onCancelClicked() {
        getMvpView().closeDialog();
    }

}
