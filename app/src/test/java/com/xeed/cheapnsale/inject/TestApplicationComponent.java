package com.xeed.cheapnsale.inject;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface TestApplicationComponent extends ApplicationComponent{
}
