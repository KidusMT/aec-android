package com.aait.aec.ui.result;

import com.aait.aec.data.DataManager;
import com.aait.aec.ui.base.BasePresenter;
import com.aait.aec.utils.CommonUtils;
import com.aait.aec.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MultipartBody;

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
