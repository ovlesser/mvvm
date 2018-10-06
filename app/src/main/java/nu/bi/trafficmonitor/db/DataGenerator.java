package nu.bi.trafficmonitor.db;

import nu.bi.trafficmonitor.db.entity.TrafficEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates data to pre-populate the database
 */
public class DataGenerator {

    private static final String[] URL = new String[]{
            "url0", "url1", "url2", "url3", "url4"};
    private static final String[] RES = new String[]{
            "/res0", "/res1", "/res2", "/res3"};

    public static List<TrafficEntity> generateTraffic() {
        List<TrafficEntity> traffics = new ArrayList<>(URL.length * RES.length);
        Random rnd = new Random();
        for (int i = 0; i < URL.length; i++) {
            for (int j = 0; j < RES.length; j++) {
                TrafficEntity traffic = new TrafficEntity();
                traffic.setUrl(URL[i] + " " + RES[j]);
                traffic.setUsage(rnd.nextInt(240));
//                traffic.setId(URL.length * i + j + 1);
                traffics.add(traffic);
            }
        }
        return traffics;
    }
}
