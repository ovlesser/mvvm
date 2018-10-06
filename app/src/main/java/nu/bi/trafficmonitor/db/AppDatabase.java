package nu.bi.trafficmonitor.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import nu.bi.trafficmonitor.AppExecutors;
import nu.bi.trafficmonitor.db.converter.DateConverter;
import nu.bi.trafficmonitor.db.dao.TrafficDao;
import nu.bi.trafficmonitor.db.entity.TrafficEntity;

import java.util.List;

@Database(entities = {TrafficEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;
    public AppExecutors mExecutors;

    @VisibleForTesting
    public static final String DATABASE_NAME = "traffic-monitor-db";
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public abstract TrafficDao trafficDao();

//    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
//        if (sInstance == null) {
//            synchronized (AppDatabase.class) {
//                if (sInstance == null) {
//                    sInstance = buildDatabase(context.getApplicationContext(), executors);
//                    sInstance.mExecutors = executors;
//                    sInstance.updateDatabaseCreated(context.getApplicationContext());
//                }
//            }
//        }
//        else {
//            sInstance.setDatabaseCreated();
//        }
//        return sInstance;
//    }
//
//    private static AppDatabase buildDatabase(final Context appContext,
//                                             final AppExecutors executors) {
//        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
//                .addCallback(new RoomDatabase.Callback() {
//                    @Override
//                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                        super.onCreate(db);
//                        sInstance.updateDatabaseCreated(appContext.getApplicationContext());
//                    }
//                }).build();
//    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    public void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    public void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    public void insertData(final List<TrafficEntity> traffic) {
        mExecutors.diskIO().execute(() -> {
            runInTransaction(() -> {
                trafficDao().insertAll(traffic);
            });
        });
    }
}
