package com.aait.aec.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.aait.aec.data.db.model.Category;
import com.aait.aec.data.network.model.exam.Exam;
import com.aait.aec.di.ActivityContext;
import com.aait.aec.di.PerActivity;
import com.aait.aec.ui.addAnswerDialog.AddAnswerMvpPresenter;
import com.aait.aec.ui.addAnswerDialog.AddAnswerMvpView;
import com.aait.aec.ui.addAnswerDialog.AddAnswerPresenter;
import com.aait.aec.ui.addAnswerDialog.AnswerAdapter;
import com.aait.aec.ui.course.CoursePagerAdapter;
import com.aait.aec.ui.create.CreateExamMvpPresenter;
import com.aait.aec.ui.create.CreateExamMvpView;
import com.aait.aec.ui.create.CreateExamPresenter;
import com.aait.aec.ui.course.CourseMvpPresenter;
import com.aait.aec.ui.course.CourseMvpView;
import com.aait.aec.ui.course.CoursePresenter;
import com.aait.aec.ui.forgot.ForgotMvpPresenter;
import com.aait.aec.ui.forgot.ForgotMvpView;
import com.aait.aec.ui.forgot.ForgotPresenter;
import com.aait.aec.ui.login.LoginMvpPresenter;
import com.aait.aec.ui.login.LoginMvpView;
import com.aait.aec.ui.login.LoginPresenter;
import com.aait.aec.ui.main.MainAdapter;
import com.aait.aec.ui.main.MainMvpPresenter;
import com.aait.aec.ui.main.MainMvpView;
import com.aait.aec.ui.main.MainPresenter;
import com.aait.aec.ui.profile.ProfileMvpPresenter;
import com.aait.aec.ui.profile.ProfileMvpView;
import com.aait.aec.ui.profile.ProfilePresenter;
import com.aait.aec.ui.register.RegisterMvpPresenter;
import com.aait.aec.ui.register.RegisterMvpView;
import com.aait.aec.ui.register.RegisterPresenter;
import com.aait.aec.ui.result.ResultAdapter;
import com.aait.aec.ui.result.ResultMvpPresenter;
import com.aait.aec.ui.result.ResultMvpView;
import com.aait.aec.ui.result.ResultPresenter;
import com.aait.aec.ui.splash.SplashMvpPresenter;
import com.aait.aec.ui.splash.SplashMvpView;
import com.aait.aec.ui.splash.SplashPresenter;
import com.aait.aec.ui.student.StudentAdapter;
import com.aait.aec.ui.student.StudentMvpPresenter;
import com.aait.aec.ui.student.StudentMvpView;
import com.aait.aec.ui.student.StudentPresenter;
import com.aait.aec.ui.studentEdit.EditStudentMvpPresenter;
import com.aait.aec.ui.studentEdit.EditStudentMvpView;
import com.aait.aec.ui.studentEdit.EditStudentPresenter;
import com.aait.aec.ui.subject.SubjectMvpPresenter;
import com.aait.aec.ui.subject.SubjectMvpView;
import com.aait.aec.ui.subject.SubjectPresenter;
import com.aait.aec.utils.rx.AppSchedulerProvider;
import com.aait.aec.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(
            LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(
            MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    RegisterMvpPresenter<RegisterMvpView> provideRegisterMvpPresenter(
            RegisterPresenter<RegisterMvpView> presenter) {
        return presenter;
    }

    @Provides
    ForgotMvpPresenter<ForgotMvpView> provideForgotMvpPresenter(
            ForgotPresenter<ForgotMvpView> presenter) {
        return presenter;
    }

    @Provides
    SubjectMvpPresenter<SubjectMvpView> provideSubjectMvpPresenter(
            SubjectPresenter<SubjectMvpView> presenter) {
        return presenter;
    }

    @Provides
    CourseMvpPresenter<CourseMvpView> provideFeedPresenter(
            CoursePresenter<CourseMvpView> presenter) {
        return presenter;
    }

    @Provides
    CoursePagerAdapter provideFeedPagerAdapter() {
        return new CoursePagerAdapter(new ArrayList<Category>());
    }

    @Provides
    CreateExamMvpPresenter<CreateExamMvpView> provideCreateExamMvpPresenter(
            CreateExamPresenter<CreateExamMvpView> presenter) {
        return presenter;
    }

    @Provides
    StudentMvpPresenter<StudentMvpView> provideStudentMvpPresenter(
            StudentPresenter<StudentMvpView> presenter) {
        return presenter;
    }

    @Provides
    ResultMvpPresenter<ResultMvpView> provideResultMvpPresenter(
            ResultPresenter<ResultMvpView> presenter) {
        return presenter;
    }

    @Provides
    EditStudentMvpPresenter<EditStudentMvpView> provideEditStudentMvpPresenter(
            EditStudentPresenter<EditStudentMvpView> presenter) {
        return presenter;
    }

    @Provides
    ProfileMvpPresenter<ProfileMvpView> provideProfileMvpPresenter(
            ProfilePresenter<ProfileMvpView> presenter) {
        return presenter;
    }


    @Provides
    AddAnswerMvpPresenter<AddAnswerMvpView> provideAddAnswerMvpPresenter(
            AddAnswerPresenter<AddAnswerMvpView> presenter) {
        return presenter;
    }

    @Provides
    StudentAdapter provideStudentAdapter() {
        return new StudentAdapter(new ArrayList<>());
    }

    @Provides
    ResultAdapter provideResultAdapter() {
        return new ResultAdapter(new ArrayList<>());
    }

    @Provides
    MainAdapter provideMainAdapter() {
        return new MainAdapter(new ArrayList<Exam>());
    }

    @Provides
    AnswerAdapter provideAnswerAdapter() {
        return new AnswerAdapter(0);// we can pass empty by default
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
