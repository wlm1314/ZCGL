package com.gdzc.app;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 王少岩 在 2017/2/27 创建了它
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    App getApp();

    @DefaultSP
    SharedPreferences getDefaultPreferences();

    SharedPreferences getUserPreferences();
}
