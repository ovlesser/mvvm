package nu.bi.trafficmonitor;

import android.app.Application;

import nu.bi.trafficmonitor.db.AppDatabase;
import nu.bi.trafficmonitor.di.AppComponent;
import nu.bi.trafficmonitor.di.AppModule;
import nu.bi.trafficmonitor.di.DaggerAppComponent;
import nu.bi.trafficmonitor.di.RetrofitModule;
import nu.bi.trafficmonitor.di.RoomDbModule;

/**
 * Android Application class. Used for accessing singletons.
 */
public class MainApplication extends Application {

    private AppComponent mainActivityViewModelComponent;
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
        initDagger();
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }

    private void initDagger(){
        mainActivityViewModelComponent = DaggerAppComponent.builder()
                .retrofitModule(new RetrofitModule())
                .appModule(new AppModule(this))
                .roomDbModule(new RoomDbModule(this))
                .build();
    }
    
    public AppComponent getMainActivityViewModelComponent() {
        return mainActivityViewModelComponent;
    }

}
