package com.aait.aec.ui.subject;

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

public class SubjectActivity extends BaseActivity implements SubjectMvpView {

    @Inject
    SubjectMvpPresenter<SubjectMvpView> mPresenter;

    @OnClick(R.id.btn_login)
    void onLoginClicked() {
        startActivity(MainActivity.getStartIntent(this));
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SubjectActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(SubjectActivity.this);

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
        Intent intent = MainActivity.getStartIntent(SubjectActivity.this);
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
