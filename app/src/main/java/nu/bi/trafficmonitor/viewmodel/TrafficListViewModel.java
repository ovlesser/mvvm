package nu.bi.trafficmonitor.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import nu.bi.trafficmonitor.MainApplication;
import nu.bi.trafficmonitor.DataRepository;
import nu.bi.trafficmonitor.db.entity.TrafficEntity;

import java.util.List;

import javax.inject.Inject;

public class TrafficListViewModel extends AndroidViewModel {

    @Inject
    DataRepository mRepository;
    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<TrafficEntity>> mObservableTraffic;

    @Inject
    public TrafficListViewModel(Application application) {
        super(application);

        ((MainApplication)application).getMainActivityViewModelComponent().inject(this);
        mObservableTraffic = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableTraffic.setValue(null);

        LiveData<List<TrafficEntity>> traffic = (mRepository.getTraffic());

        // observe the changes of the traffic from the database and forward them
        mObservableTraffic.addSource(traffic, mObservableTraffic::setValue);
    }

    public void getData(){
        mRepository.getTrafficFromWeb();
    }

    /**
     * Expose the LiveData Traffic query so the UI can observe it.
     */
    public LiveData<List<TrafficEntity>> getTraffic() {
        return mObservableTraffic;
    }
}
