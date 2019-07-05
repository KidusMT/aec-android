package com.aait.aec.di.component;

import android.app.Application;
import android.content.Context;

import com.aait.aec.MvpApp;
import com.aait.aec.data.DataManager;
import com.aait.aec.di.ApplicationContext;
import com.aait.aec.di.module.ApplicationModule;
import com.aait.aec.service.SyncService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MvpApp app);

    void inject(SyncService service);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}