package com.aait.aec.ui.forgot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.aait.aec.R;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.ui.login.LoginActivity;
import com.aait.aec.utils.CommonUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotActivity extends BaseActivity implements ForgotMvpView {

    @Inject
    ForgotMvpPresenter<ForgotMvpView> mPresenter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ForgotActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ForgotActivity.this);

        CommonUtils.hideKeyboard(this);
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this));
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClicked() {
        mPresenter.onLoginClicked();
    }

    @OnClick(R.id.tv_return_login)
    void onRegisterClicked() {
        mPresenter.onLoginClicked();
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
