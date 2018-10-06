package nu.bi.trafficmonitor.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nu.bi.trafficmonitor.MainApplication;

@Module
public class AppModule {
    MainApplication mApplication;

    public AppModule(MainApplication application) {
        mApplication = application;
    }

    @Provides
    public Context getAppContext(){
        return mApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    MainApplication providesApplication() {
        return mApplication;
    }
}
