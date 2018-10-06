package nu.bi.trafficmonitor;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;

import nu.bi.trafficmonitor.db.AppDatabase;
import nu.bi.trafficmonitor.db.entity.TrafficEntity;
import nu.bi.trafficmonitor.rest.TrafficApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

/**
 * Repository handling the work with traffic.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private final TrafficApi mTrafficApi;
    private MediatorLiveData<List<TrafficEntity>> mObservableTraffic;

    @Inject
    public DataRepository(final AppDatabase database, final TrafficApi trafficApi) {
        mDatabase = database;
        mTrafficApi = trafficApi;
        mObservableTraffic = new MediatorLiveData<>();
        mObservableTraffic.addSource(mDatabase.trafficDao().loadAllTraffic(),
                trafficEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableTraffic.postValue(trafficEntities);
                    }
                });
    }

    public static DataRepository getInstance(final AppDatabase database, final TrafficApi trafficApi) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database, trafficApi);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of traffic from the database and get notified when the data changes.
     */
    public LiveData<List<TrafficEntity>> getTraffic() {
        return mObservableTraffic;
    }

    public LiveData<TrafficEntity> loadTraffic(final int trafficId) {
        return mDatabase.trafficDao().loadTraffic(trafficId);
    }

    public void getTrafficFromWeb(){
        mTrafficApi.getTraffic().enqueue(new Callback<List<TrafficEntity>>() {
            @Override
            public void onResponse(Call<List<TrafficEntity>> call, Response<List<TrafficEntity>> response) {
                if (response.isSuccessful()) {
                    mDatabase.insertData(response.body());
                } else {
                    // error case
                    switch (response.code()) {
                        case 404:
                            break;
                        case 500:
                            break;
                        default:
                            break;
                    }
                }
            }
            @Override
            public void onFailure(Call<List<TrafficEntity>> call, Throwable t) {
                List<TrafficEntity> traffic = generateFakeTraffic();
                mDatabase.insertData(traffic);
            }
        });
    }

    private List<TrafficEntity> generateFakeTraffic() {
        final String[] URL = new String[]{
                "url0", "url1", "url2", "url3", "url4"};
        final String[] RES = new String[]{
                "/res0", "/res1", "/res2", "/res3", "/res4"};

        List<TrafficEntity> traffics = new ArrayList<>();
        Random rnd = new Random();
        int i = rnd.nextInt(URL.length);
        for (int j = 0; j < 1+rnd.nextInt(RES.length-1); j++) {
            TrafficEntity traffic = new TrafficEntity();
            traffic.setUrl(URL[i] + " " + RES[j]);
            traffic.setUsage(rnd.nextInt(1024*1024));
            traffics.add(traffic);
        }
        return traffics;
    }
}
