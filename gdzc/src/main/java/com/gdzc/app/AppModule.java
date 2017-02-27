package com.gdzc.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 王少岩 在 2017/2/27 创建了它
 */
@Module
public class AppModule {
    private App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public App provideApp(){
        return mApp;
    }

    @Provides
    @Singleton
    @DefaultSP
    public SharedPreferences provideDefaultSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(mApp);
    }

    @Provides
    @Singleton
    public SharedPreferences provideUserSharedPreferences(){
        return mApp.getSharedPreferences("user_pref", Context.MODE_PRIVATE);
    }
}
