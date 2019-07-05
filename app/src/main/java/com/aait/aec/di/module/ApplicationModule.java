package com.aait.aec.di.module;

import android.app.Application;
import android.content.Context;

import com.aait.aec.BuildConfig;
import com.aait.aec.R;
import com.aait.aec.data.AppDataManager;
import com.aait.aec.data.DataManager;
import com.aait.aec.data.db.AppDbHelper;
import com.aait.aec.data.db.DbHelper;
import com.aait.aec.data.network.ApiCall;
import com.aait.aec.data.network.ApiHeader;
import com.aait.aec.data.network.ApiHelper;
import com.aait.aec.data.network.ApiInterceptor;
import com.aait.aec.data.network.AppApiHelper;
import com.aait.aec.data.prefs.AppPreferencesHelper;
import com.aait.aec.data.prefs.PreferencesHelper;
import com.aait.aec.di.ApiInfo;
import com.aait.aec.di.ApplicationContext;
import com.aait.aec.di.DatabaseInfo;
import com.aait.aec.di.PreferenceInfo;
import com.aait.aec.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @Singleton
    ApiHeader provideApiHeader(PreferencesHelper preferencesHelper) {
        return new ApiHeader(preferencesHelper.getAccessToken());
    }

    @Provides
    @Singleton
    ApiCall provideApiCall(ApiInterceptor apiInterceptor) {
        return ApiCall.Factory.create(apiInterceptor);
    }
}
