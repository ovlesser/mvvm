package nu.bi.trafficmonitor.rest;

import java.util.List;

import nu.bi.trafficmonitor.db.entity.TrafficEntity;
import nu.bi.trafficmonitor.model.Traffic;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TrafficApi {
    @GET("traffic.json")
    Call<List<TrafficEntity>> getTraffic();
}
