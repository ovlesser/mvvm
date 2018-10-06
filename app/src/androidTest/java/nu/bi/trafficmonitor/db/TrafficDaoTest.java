package nu.bi.trafficmonitor.db;

import static nu.bi.trafficmonitor.db.TestData.TRAFFIC;
import static nu.bi.trafficmonitor.db.TestData.TRAFFIC_ENTITY;

import static junit.framework.Assert.assertTrue;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import nu.bi.trafficmonitor.LiveDataTestUtil;

import nu.bi.trafficmonitor.db.dao.TrafficDao;
import nu.bi.trafficmonitor.db.entity.TrafficEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Test the implementation of {@link TrafficDao}
 */
@RunWith(AndroidJUnit4.class)
public class TrafficDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase mDatabase;

    private TrafficDao mTrafficDao;

    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        mTrafficDao = mDatabase.trafficDao();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getTrafficWhenNoTrafficInserted() throws InterruptedException {
        List<TrafficEntity> traffic = LiveDataTestUtil.getValue(mTrafficDao.loadAllTraffic());

        assertTrue(traffic.isEmpty());
    }

    @Test
    public void getTrafficAfterInserted() throws InterruptedException {
        mTrafficDao.insertAll(TRAFFIC);

        List<TrafficEntity> traffic = LiveDataTestUtil.getValue(mTrafficDao.loadAllTraffic());

        assertThat(traffic.size(), is(TRAFFIC.size()));
    }

    @Test
    public void getTrafficById() throws InterruptedException {
        mTrafficDao.insertAll(TRAFFIC);

        TrafficEntity traffic = LiveDataTestUtil.getValue(mTrafficDao.loadTraffic
                (TRAFFIC_ENTITY.getId()));

        assertThat(traffic.getId(), is(TRAFFIC_ENTITY.getId()));
        assertThat(traffic.getUrl(), is(TRAFFIC_ENTITY.getUrl()));
        assertThat(traffic.getUsage(), is(TRAFFIC_ENTITY.getUsage()));
    }

}
