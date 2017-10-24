package com.visual.android.locsilence;

/**
 * Created by RamiK on 10/14/2017.
 */

public class Location {

    private String id;
    private String name;
    private double lat;
    private double lng;
    private String createdAt;
    private String updatedAt;
    private int volume;

    // default constructor
    public Location() {}

    public Location(String id, String name, double lat, double lng,
                    String createdAt, String updatedAt, int volume) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
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

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
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

    public void setLng(float lng) {
        this.lng = lng;
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
