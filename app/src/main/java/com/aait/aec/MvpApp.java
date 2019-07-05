package com.aait.aec;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.aait.aec.data.DataManager;
import com.aait.aec.di.component.ApplicationComponent;
import com.aait.aec.di.component.DaggerApplicationComponent;
import com.aait.aec.di.module.ApplicationModule;
import com.aait.aec.utils.AppConstants;
import com.aait.aec.utils.AppLogger;
import com.aait.aec.utils.LocaleManager;

import java.util.Locale;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.aait.aec.utils.AppConstants.PREF_KEY_CURRENT_LANGUAGE;

public class MvpApp extends Application {

    @Inject
    DataManager mDataManager;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    public static final String TAG = MvpApp.class.getSimpleName();

    private ApplicationComponent mApplicationComponent;

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        AppLogger.init();

        CalligraphyConfig.initDefault(mCalligraphyConfig);

        context = this;
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    public static Context getContext(){
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
        Log.d(TAG, "attachBaseContext");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
        Log.d(TAG, "onConfigurationChanged: " + newConfig.locale.getLanguage());
    }
}
