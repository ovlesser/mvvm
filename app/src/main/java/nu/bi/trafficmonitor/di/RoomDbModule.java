package nu.bi.trafficmonitor.di;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nu.bi.trafficmonitor.AppExecutors;
import nu.bi.trafficmonitor.MainApplication;
import nu.bi.trafficmonitor.db.AppDatabase;
import nu.bi.trafficmonitor.db.DataGenerator;
import nu.bi.trafficmonitor.db.dao.TrafficDao;
import nu.bi.trafficmonitor.db.entity.TrafficEntity;

import static nu.bi.trafficmonitor.db.AppDatabase.DATABASE_NAME;

@Module(includes = AppModule.class)
public class RoomDbModule {

    private static AppDatabase sInstance;

    public RoomDbModule(MainApplication mApplication) {
//        sInstance = Room.databaseBuilder(mApplication, AppDatabase.class, DATABASE_NAME).build();
        sInstance = getInstance(mApplication, mApplication.getAppExecutors());
    }

    @Singleton
    @Provides
    AppDatabase providesRoomDatabase() {
        return sInstance;
    }

    @Singleton
    @Provides
    TrafficDao providesTrafficDao(AppDatabase trafficDB) {
        return trafficDB.trafficDao();
    }

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.mExecutors = executors;
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        else {
            sInstance.setDatabaseCreated();
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        sInstance.updateDatabaseCreated(appContext.getApplicationContext());
//                        executors.diskIO().execute(() -> {
//                            // Add a delay to simulate a long-running operation
//                            addDelay();
//                            // Generate the data for pre-population
//                            AppDatabase database = getInstance(appContext, executors);
//                            List<TrafficEntity> traffic = DataGenerator.generateTraffic();
//
//                            database.insertData(traffic);
//                            // notify that the database was created and it's ready to be used
//                            database.setDatabaseCreated();
//                        });
                    }
                }).build();
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }
}
