package nu.bi.trafficmonitor.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import nu.bi.trafficmonitor.model.Traffic;

@Entity(tableName = "traffic")
public class TrafficEntity implements Traffic {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String url;
    private int usage;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public TrafficEntity() {
    }

    public TrafficEntity(int id, String name, int price) {
        this.id = id;
        this.url = name;
        this.usage = price;
    }

    public TrafficEntity(Traffic traffic) {
        this.id = traffic.getId();
        this.url = traffic.getUrl();
        this.usage = traffic.getUsage();
    }
}
