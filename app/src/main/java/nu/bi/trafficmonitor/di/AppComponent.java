package nu.bi.trafficmonitor.di;

import javax.inject.Singleton;

import dagger.Component;
import nu.bi.trafficmonitor.db.dao.TrafficDao;
import nu.bi.trafficmonitor.rest.TrafficApi;
import nu.bi.trafficmonitor.ui.MainActivity;
import nu.bi.trafficmonitor.viewmodel.TrafficListViewModel;

@Singleton
@Component(modules = {AppModule.class,RetrofitModule.class,RepositoryModule.class,RoomDbModule.class})
public interface AppComponent {
    void inject(TrafficListViewModel viewModel);
    void inject(MainActivity activity);
    TrafficApi getTrafficApi();
    TrafficDao getTrafficDao();
}
