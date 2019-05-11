/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.aait.aec.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.aait.aec.data.db.model.Category;
import com.aait.aec.data.db.model.Exam;
import com.aait.aec.data.db.model.Student;
import com.aait.aec.data.network.model.BlogResponse;
import com.aait.aec.data.network.model.OpenSourceResponse;
import com.aait.aec.di.ActivityContext;
import com.aait.aec.di.PerActivity;
import com.aait.aec.ui.about.AboutMvpPresenter;
import com.aait.aec.ui.about.AboutMvpView;
import com.aait.aec.ui.about.AboutPresenter;
import com.aait.aec.ui.addAnswerDialog.AddAnswerMvpPresenter;
import com.aait.aec.ui.addAnswerDialog.AddAnswerMvpView;
import com.aait.aec.ui.addAnswerDialog.AddAnswerPresenter;
import com.aait.aec.ui.addAnswerDialog.AnswerAdapter;
import com.aait.aec.ui.create.CreateExamMvpPresenter;
import com.aait.aec.ui.create.CreateExamMvpView;
import com.aait.aec.ui.create.CreateExamPresenter;
import com.aait.aec.ui.feed.FeedMvpPresenter;
import com.aait.aec.ui.feed.FeedMvpView;
import com.aait.aec.ui.feed.FeedPagerAdapter;
import com.aait.aec.ui.feed.FeedPresenter;
import com.aait.aec.ui.feed.blogs.BlogAdapter;
import com.aait.aec.ui.feed.blogs.BlogMvpPresenter;
import com.aait.aec.ui.feed.blogs.BlogMvpView;
import com.aait.aec.ui.feed.blogs.BlogPresenter;
import com.aait.aec.ui.feed.opensource.OpenSourceAdapter;
import com.aait.aec.ui.feed.opensource.OpenSourceMvpPresenter;
import com.aait.aec.ui.feed.opensource.OpenSourceMvpView;
import com.aait.aec.ui.feed.opensource.OpenSourcePresenter;
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
import com.aait.aec.ui.main.rating.RatingDialogMvpPresenter;
import com.aait.aec.ui.main.rating.RatingDialogMvpView;
import com.aait.aec.ui.main.rating.RatingDialogPresenter;
import com.aait.aec.ui.register.RegisterMvpPresenter;
import com.aait.aec.ui.register.RegisterMvpView;
import com.aait.aec.ui.register.RegisterPresenter;
import com.aait.aec.ui.splash.SplashMvpPresenter;
import com.aait.aec.ui.splash.SplashMvpView;
import com.aait.aec.ui.splash.SplashPresenter;
import com.aait.aec.ui.student.StudentAdapter;
import com.aait.aec.ui.student.StudentMvpPresenter;
import com.aait.aec.ui.student.StudentMvpView;
import com.aait.aec.ui.student.StudentPresenter;
import com.aait.aec.ui.subject.SubjectMvpPresenter;
import com.aait.aec.ui.subject.SubjectMvpView;
import com.aait.aec.ui.subject.SubjectPresenter;
import com.aait.aec.utils.rx.AppSchedulerProvider;
import com.aait.aec.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by janisharali on 27/01/17.
 */

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
    AboutMvpPresenter<AboutMvpView> provideAboutPresenter(
            AboutPresenter<AboutMvpView> presenter) {
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
    RatingDialogMvpPresenter<RatingDialogMvpView> provideRateUsPresenter(
            RatingDialogPresenter<RatingDialogMvpView> presenter) {
        return presenter;
    }

    @Provides
    FeedMvpPresenter<FeedMvpView> provideFeedPresenter(
            FeedPresenter<FeedMvpView> presenter) {
        return presenter;
    }

    @Provides
    OpenSourceMvpPresenter<OpenSourceMvpView> provideOpenSourcePresenter(
            OpenSourcePresenter<OpenSourceMvpView> presenter) {
        return presenter;
    }

    @Provides
    BlogMvpPresenter<BlogMvpView> provideBlogMvpPresenter(
            BlogPresenter<BlogMvpView> presenter) {
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
    AddAnswerMvpPresenter<AddAnswerMvpView> provideAddAnswerMvpPresenter(
            AddAnswerPresenter<AddAnswerMvpView> presenter) {
        return presenter;
    }

    @Provides
    FeedPagerAdapter provideFeedPagerAdapter() {
        return new FeedPagerAdapter(new ArrayList<Category>());
    }

    @Provides
    StudentAdapter provideStudentAdapter() {
        return new StudentAdapter(new ArrayList<Student>());
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
    OpenSourceAdapter provideOpenSourceAdapter() {
        return new OpenSourceAdapter(new ArrayList<OpenSourceResponse.Repo>());
    }

    @Provides
    BlogAdapter provideBlogAdapter() {
        return new BlogAdapter(new ArrayList<BlogResponse.Blog>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
