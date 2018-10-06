package nu.bi.trafficmonitor.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApi {

    private static TrafficApi sInstance;
    final static String BASE_URL = "https://localhost/";

    static public TrafficApi getTrafficService(){
        if (sInstance == null) {
            OkHttpClient okHttpClient = new OkHttpClient()
                    .newBuilder()
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .build();
            sInstance = retrofit.create(TrafficApi.class);
        }
        return sInstance;
    }
}
