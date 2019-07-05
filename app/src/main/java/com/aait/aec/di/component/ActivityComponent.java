package com.aait.aec.di.component;

import com.aait.aec.di.PerActivity;
import com.aait.aec.di.module.ActivityModule;
import com.aait.aec.ui.addAnswerDialog.AddAnswerDialog;
import com.aait.aec.ui.course.CourseActivity;
import com.aait.aec.ui.create.CreateExamActivity;
import com.aait.aec.ui.forgot.ForgotActivity;
import com.aait.aec.ui.login.LoginActivity;
import com.aait.aec.ui.main.MainActivity;
import com.aait.aec.ui.profile.ProfileActivity;
import com.aait.aec.ui.register.RegisterActivity;
import com.aait.aec.ui.result.ResultActivity;
import com.aait.aec.ui.settings.LanguageActivity;
import com.aait.aec.ui.settings.SettingsActivity;
import com.aait.aec.ui.splash.SplashActivity;
import com.aait.aec.ui.student.StudentActivity;
import com.aait.aec.ui.studentEdit.EditStudentDialog;
import com.aait.aec.ui.subject.SubjectActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(SplashActivity activity);

    void inject(RegisterActivity activity);

    void inject(ForgotActivity activity);

    void inject(CreateExamActivity activity);

    void inject(SubjectActivity activity);

    void inject(StudentActivity activity);

    void inject(AddAnswerDialog dialog);

    void inject(ResultActivity activity);

    void inject(CourseActivity activity);

    void inject(EditStudentDialog dialog);

    void inject(ProfileActivity activity);

    void inject(LanguageActivity activity);

    void inject(SettingsActivity activity);

}
