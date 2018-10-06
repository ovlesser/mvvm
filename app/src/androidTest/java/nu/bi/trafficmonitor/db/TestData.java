package nu.bi.trafficmonitor.db;

import nu.bi.trafficmonitor.db.entity.TrafficEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Utility class that holds values to be used for testing.
 */
public class TestData {

    static final TrafficEntity TRAFFIC_ENTITY = new TrafficEntity(1, "name",
            3);
    static final TrafficEntity TRAFFIC_ENTITY2 = new TrafficEntity(2, "name2",
            20);
    static final List<TrafficEntity> TRAFFIC = Arrays.asList(TRAFFIC_ENTITY, TRAFFIC_ENTITY2);
}
