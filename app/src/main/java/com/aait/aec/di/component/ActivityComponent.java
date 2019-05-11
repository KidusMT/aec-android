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

package com.aait.aec.di.component;

import com.aait.aec.di.PerActivity;
import com.aait.aec.di.module.ActivityModule;
import com.aait.aec.ui.about.AboutFragment;
import com.aait.aec.ui.addAnswerDialog.AddAnswerDialog;
import com.aait.aec.ui.create.CreateExamActivity;
import com.aait.aec.ui.feed.FeedActivity;
import com.aait.aec.ui.feed.blogs.BlogFragment;
import com.aait.aec.ui.feed.opensource.OpenSourceFragment;
import com.aait.aec.ui.forgot.ForgotActivity;
import com.aait.aec.ui.login.LoginActivity;
import com.aait.aec.ui.main.MainActivity;
import com.aait.aec.ui.main.rating.RateUsDialog;
import com.aait.aec.ui.register.RegisterActivity;
import com.aait.aec.ui.splash.SplashActivity;
import com.aait.aec.ui.student.StudentActivity;
import com.aait.aec.ui.subject.SubjectActivity;

import dagger.Component;

/**
 * Created by janisharali on 27/01/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(SplashActivity activity);

    void inject(FeedActivity activity);

    void inject(AboutFragment fragment);

    void inject(OpenSourceFragment fragment);

    void inject(BlogFragment fragment);

    void inject(RateUsDialog dialog);

    void inject(RegisterActivity activity);

    void inject(ForgotActivity activity);

    void inject(CreateExamActivity activity);

    void inject(SubjectActivity activity);

    void inject(StudentActivity activity);

    void inject(AddAnswerDialog dialog);

}
