package com.aait.aec.ui.settings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.aait.aec.R;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        return intent;
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

        mStorageSwitch.setClickable(false);
        mLocationSwitch.setClickable(false);
        mMicrophoneSwitch.setClickable(false);
        mCameraSwitch.setClickable(false);
        mPresenter.resolveCurrentLanguge();

    }

    @Override
    public void setCurrentLanguage(String language) {
        if(language.equals("en")){
            mCurrentLanguage.setText("English");
        }else if(language.equals("am")){
            mCurrentLanguage.setText("Amharic");
        }
    }

    @OnClick(R.id.layout_current_language)
    public void onClickCurrentLanguage(View view){
        startActivity(new Intent(this, LanguageActivity.class));
    }

    private boolean checkLocationPermissionStatus(){
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private boolean checkStoragePermissionStatus(){
        return hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private boolean checkMicrophonePermissionStatus(){
        return hasPermission(Manifest.permission.RECORD_AUDIO);
    }

    private boolean checkCameraPermissionStatus(){
        return hasPermission(Manifest.permission.CAMERA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resolvePermissionSatuses();
    }

    private void resolvePermissionSatuses(){
        if(checkCameraPermissionStatus()){
            mCameraSwitch.setChecked(true);
        }else {
            mCameraSwitch.setChecked(false);
        }

        if(checkLocationPermissionStatus()){
            mLocationSwitch.setChecked(true);
        }else{
            mLocationSwitch.setChecked(false);
        }

        if(checkMicrophonePermissionStatus()){
            mMicrophoneSwitch.setChecked(true);
        }else{
            mMicrophoneSwitch.setChecked(false);
        }

        if(checkStoragePermissionStatus()){
            mStorageSwitch.setChecked(true);
        }else{
            mStorageSwitch.setChecked(false);
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }

    @OnClick({R.id.switch_storage_wrapper,R.id.switch_camera_wrapper,R.id.switch_location_wrapper,R.id.switch_microphone_wrapper})
    public void goToPermissionsSetting(View v){
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
