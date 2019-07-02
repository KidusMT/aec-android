package com.aait.aec.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.aait.aec.R;
import com.aait.aec.data.db.model.User;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.utils.CommonUtils;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity implements ProfileMvpView {

    @BindView(R.id.profile_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.profile_name)
    TextView mProfileName;

    @BindView(R.id.profile_email)
    TextView mProfileEmail;

    @BindView(R.id.profile_phone_number)
    TextView mProfilePhoneNumber;

    @BindView(R.id.profile_address)
    TextView mProfilePhoneAddress;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ProfileActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_profile);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        CommonUtils.hideKeyboard(this);

        setUp();
    }

    @Override
    protected void setUp() {

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadProfile(User user) {
        if (user!=null){
            mProfileName.setText(user.getName());
        }
    }
}
