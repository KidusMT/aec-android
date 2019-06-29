package com.aait.aec.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.aait.aec.R;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.ui.forgot.ForgotActivity;
import com.aait.aec.ui.main.MainActivity;
import com.aait.aec.ui.register.RegisterActivity;
import com.aait.aec.utils.CommonUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;

    @BindView(R.id.login_et_username)
    EditText etUsername;

    @BindView(R.id.login_et_password)
    EditText etPassword;

    @OnClick(R.id.btn_login)
    void onLoginClicked() {
        mPresenter.onServerLoginClick(etUsername.getText().toString(), etPassword.getText().toString());
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(LoginActivity.this);

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
        hideLoading();
        startActivity(MainActivity.getStartIntent(this));
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
