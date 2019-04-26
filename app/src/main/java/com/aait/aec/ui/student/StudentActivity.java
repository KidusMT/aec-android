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

package com.aait.aec.ui.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.aait.aec.R;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.ui.forgot.ForgotActivity;
import com.aait.aec.ui.main.MainActivity;
import com.aait.aec.ui.register.RegisterActivity;
import com.aait.aec.utils.CommonUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by janisharali on 27/01/17.
 */

public class StudentActivity extends BaseActivity implements StudentMvpView {

    @Inject
    StudentMvpPresenter<StudentMvpView> mPresenter;

    @OnClick(R.id.btn_login)
    void onLoginClicked() {
        startActivity(MainActivity.getStartIntent(this));
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, StudentActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(StudentActivity.this);

        CommonUtils.hideKeyboard(this);
    }

    @OnClick(R.id.tv_forgot_pass)
    void onForgotPasswordClicked() {
        onForgotClicked();
    }

    @OnClick(R.id.tv_don_hv_account)
    void onRegisterClicked() {
        startActivity(RegisterActivity.getStartIntent(this));
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(StudentActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void onForgotClicked() {
        startActivity(ForgotActivity.getStartIntent(this));
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {

    }
}
