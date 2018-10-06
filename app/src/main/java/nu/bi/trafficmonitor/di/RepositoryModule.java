package nu.bi.trafficmonitor.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nu.bi.trafficmonitor.DataRepository;
import nu.bi.trafficmonitor.db.AppDatabase;
import nu.bi.trafficmonitor.rest.TrafficApi;

@Module(includes = {RoomDbModule.class,RetrofitModule.class})
public class RepositoryModule {
    @Provides
    @Singleton
    public DataRepository getRepository(AppDatabase database, TrafficApi trafficApi){
        return DataRepository.getInstance(database, trafficApi);
    }
}
