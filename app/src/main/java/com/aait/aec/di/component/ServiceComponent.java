package com.aait.aec.di.component;

import com.aait.aec.di.PerService;
import com.aait.aec.di.module.ServiceModule;
import com.aait.aec.service.SyncService;

import dagger.Component;

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(SyncService service);

}
