package com.aait.aec.ui.course;

import com.aait.aec.data.DataManager;
import com.aait.aec.ui.base.BasePresenter;
import com.aait.aec.ui.base.MvpView;
import com.aait.aec.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by janisharali on 25/05/17.
 */

public class CoursePresenter<V extends MvpView> extends BasePresenter<V> implements
        CourseMvpPresenter<V> {

    private static final String TAG = "CoursePresenter";

    @Inject
    public CoursePresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
