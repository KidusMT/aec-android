package com.aait.aec.ui.create;

import com.aait.aec.data.DataManager;
import com.aait.aec.data.db.model.Student;
import com.aait.aec.data.network.model.exam.Answers;
import com.aait.aec.data.network.model.exam.Exam;
import com.aait.aec.ui.base.BasePresenter;
import com.aait.aec.utils.CommonUtils;
import com.aait.aec.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class CreateExamPresenter<V extends CreateExamMvpView> extends BasePresenter<V>
        implements CreateExamMvpPresenter<V> {

    private static final String TAG = "RegisterPresenter";

    @Inject
    public CreateExamPresenter(DataManager dataManager,
                               SchedulerProvider schedulerProvider,
                               CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        loadCourse();// from the api
        loadStudents();// from the db
    }

    @Override
    public void createExam(String examName, String examType, int examWeight, List<Answers> answers, List<Student> students) {

        Exam exam = new Exam(
                examName,// exam name
                examType,// exam type
                examWeight,// exam weight
                answers,// exam answers
                getDataManager().getCurrentUserId(),// userId
                students// students
        );

        getCompositeDisposable().add(getDataManager()
                .createExam(exam).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(exam1 -> {
                    if (!isViewAttached()) {
                        return;
                    }


                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();
                    getMvpView().onError(CommonUtils.getErrorMessage(throwable));
                }));
    }

    @Override
    public void loadCourse() {
        getCompositeDisposable().add(getDataManager()
                .getCourses().subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(courses -> {
                    if (!isViewAttached()) {
                        return;
                    }

                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();
                    getMvpView().onError(CommonUtils.getErrorMessage(throwable));
                }));
    }

    @Override
    public void loadStudents() {
        getCompositeDisposable().add(getDataManager().getStudents()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(students -> {
                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().updateExam(students);
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();
                    getMvpView().onError(CommonUtils.getErrorMessage(throwable));
                }));
    }
}
