package com.aait.aec.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.aait.aec.R;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.ui.login.LoginActivity;
import com.aait.aec.ui.main.MainActivity;
import com.aait.aec.utils.CommonUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by janisharali on 27/01/17.
 */

public class RegisterActivity extends BaseActivity implements RegisterMvpView {

    @Inject
    RegisterMvpPresenter<RegisterMvpView> mPresenter;

    @BindView(R.id.register_et_first_name)
    EditText etFirstName;

    @BindView(R.id.register_et_last_name)
    EditText etLastName;

    @BindView(R.id.register_et_username)
    EditText etUsername;

    @BindView(R.id.register_et_sex)
    EditText etSex;

    @BindView(R.id.register_et_password)
    EditText etPassword;

    @BindView(R.id.register_et_password_confirm)
    EditText etPasswordConfirm;

    @BindView(R.id.register_et_phone_number)
    EditText etPhone;


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

    @OnClick(R.id.btn_register)
    void onRegisterClicked() {
        startActivity(LoginActivity.getStartIntent(this));
        mPresenter.onRegistrationClicked(etFirstName.getText().toString(), etLastName.getText().toString(),
                etUsername.getText().toString(), etSex.getText().toString(), etPassword.getText().toString(),
                etPasswordConfirm.getText().toString(), etPhone.getText().toString());
    }

    @OnClick(R.id.tv_hv_account)
    void onLoginClicked() {
        startActivity(LoginActivity.getStartIntent(this));
    }

    @Override
    public void openMainActivity() {
        hideLoading();
        Intent intent = MainActivity.getStartIntent(RegisterActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openLoginActivity() {
        hideLoading();
        Intent intent = LoginActivity.getStartIntent(RegisterActivity.this);
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
