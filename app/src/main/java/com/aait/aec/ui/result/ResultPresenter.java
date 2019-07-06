package com.aait.aec.ui.result;

import com.aait.aec.data.DataManager;
import com.aait.aec.data.network.model.correct.CorrectRequest;
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
    public void onUploadClicked(String container, List<MultipartBody.Part> parts, CorrectRequest request) {

        getCompositeDisposable().add(getDataManager()
                .upload(container, parts)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(imageUploadResponse -> {

                            if (!isViewAttached())
                                return;

                            onCorrect(request);
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

    @Override
    public void onCorrect(CorrectRequest request) {

        getCompositeDisposable().add(getDataManager()
                .correct(request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(o -> {

                    if (!isViewAttached())
                        return;

                    getMvpView().showMessage("Successfully Marked");

                }));
    }
}
