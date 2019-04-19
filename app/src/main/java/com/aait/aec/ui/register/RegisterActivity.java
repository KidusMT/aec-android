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

package com.aait.aec.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.aait.aec.R;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.ui.login.LoginActivity;
import com.aait.aec.ui.main.MainActivity;
import com.aait.aec.utils.CommonUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by janisharali on 27/01/17.
 */

public class RegisterActivity extends BaseActivity implements RegisterMvpView {

    @Inject
    RegisterMvpPresenter<RegisterMvpView> mPresenter;

//    @BindView(R.id.et_email)
//    EditText mEmailEditText;
//
//    @BindView(R.id.et_password)
//    EditText mPasswordEditText;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(RegisterActivity.this);

        CommonUtils.hideKeyboard(this);
    }

    @OnClick(R.id.tv_hv_account)
    void onLoginClicked() {
        startActivity(LoginActivity.getStartIntent(this));
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(RegisterActivity.this);
        startActivity(intent);
        finish();
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
