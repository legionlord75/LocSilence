package com.visual.android.locsilence;

/**
 * Created by RamiK on 10/14/2017.
 */

public class Location {

    private String id;
    private String name;
    private float lat;
    private float longitude;
    private String createdAt;
    private String updatedAt;
    private int volume;

    // default constructor
    public Location() {}

    public Location(String id, String name, float lat, float longitude,
                    String createdAt, String updatedAt, int volume) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.volume = volume;
    }

    // GET methods

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getLat() {
        return lat;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getVolume() {
        return volume;
    }

    // SET methods

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
