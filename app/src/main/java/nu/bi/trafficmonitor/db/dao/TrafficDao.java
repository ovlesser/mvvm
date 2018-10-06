package nu.bi.trafficmonitor.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import nu.bi.trafficmonitor.db.entity.TrafficEntity;

import java.util.List;

@Dao
public interface TrafficDao {
    @Query("SELECT * FROM traffic order by id desc")
    LiveData<List<TrafficEntity>> loadAllTraffic();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TrafficEntity> traffic);

    @Query("select * from traffic where id = :trafficId order by id desc")
    LiveData<TrafficEntity> loadTraffic(int trafficId);

    @Query("select * from traffic where id = :trafficId order by id desc")
    TrafficEntity loadTrafficSync(int trafficId);
}
