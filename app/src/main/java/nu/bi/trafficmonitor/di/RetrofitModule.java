package nu.bi.trafficmonitor.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nu.bi.trafficmonitor.rest.TrafficApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = OkHttpClientModule.class)
public class RetrofitModule {
    final static String BASE_URL = "https://localhost/";

    @Provides
    @Singleton
    public TrafficApi getTrafficService(Retrofit getClient){
        return getClient.create(TrafficApi.class);
    }

    @Provides
    public Retrofit getClient(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory, Gson gson){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    public Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }
}

