package com.aait.aec.ui.settings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.aait.aec.R;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.utils.LocaleManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aait.aec.utils.LocaleManager.LANGUAGE_AMHARIC;
import static com.aait.aec.utils.LocaleManager.LANGUAGE_ENGLISH;

public class SettingsActivity extends BaseActivity implements SettingsMvpView {

    @Inject
    SettingsMvpPresenter<SettingsMvpView> mPresenter;

    @BindView(R.id.lbl_current_language)
    TextView mCurrentLanguage;

    @BindView(R.id.switch_location)
    SwitchCompat mLocationSwitch;

    @BindView(R.id.switch_microphone)
    SwitchCompat mMicrophoneSwitch;

    @BindView(R.id.switch_storage)
    SwitchCompat mStorageSwitch;

    @BindView(R.id.switch_camera)
    SwitchCompat mCameraSwitch;

    @BindView(R.id.settings_toolbar)
    Toolbar mToolbar;


    public static Intent getStartIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        // the presenter
        mPresenter.onAttach(SettingsActivity.this);

        setUp();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mStorageSwitch.setClickable(false);
        mLocationSwitch.setClickable(false);
        mMicrophoneSwitch.setClickable(false);
        mCameraSwitch.setClickable(false);
        mPresenter.resolveCurrentLanguage();
    }

    @Override
    public void setCurrentLanguage(String language) {
        if (LocaleManager.getLanguage(this).equals(LANGUAGE_ENGLISH)) {
            mCurrentLanguage.setText(getString(R.string.english));
        } else {
            mCurrentLanguage.setText(getString(R.string.amharic));
        }
    }

    @OnClick(R.id.layout_current_language)
    public void onClickCurrentLanguage(View view) {
        startActivity(new Intent(this, LanguageActivity.class));
    }

    private boolean checkLocationPermissionStatus() {
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private boolean checkStoragePermissionStatus() {
        return hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private boolean checkMicrophonePermissionStatus() {
        return hasPermission(Manifest.permission.RECORD_AUDIO);
    }

    private boolean checkCameraPermissionStatus() {
        return hasPermission(Manifest.permission.CAMERA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resolvePermissionSatuses();
    }

    private void resolvePermissionSatuses() {
        if (checkCameraPermissionStatus()) {
            mCameraSwitch.setChecked(true);
        } else {
            mCameraSwitch.setChecked(false);
        }

        if (checkLocationPermissionStatus()) {
            mLocationSwitch.setChecked(true);
        } else {
            mLocationSwitch.setChecked(false);
        }

        if (checkMicrophonePermissionStatus()) {
            mMicrophoneSwitch.setChecked(true);
        } else {
            mMicrophoneSwitch.setChecked(false);
        }

        if (checkStoragePermissionStatus()) {
            mStorageSwitch.setChecked(true);
        } else {
            mStorageSwitch.setChecked(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.switch_storage_wrapper, R.id.switch_camera_wrapper, R.id.switch_location_wrapper, R.id.switch_microphone_wrapper})
    public void goToPermissionsSetting(View v) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
