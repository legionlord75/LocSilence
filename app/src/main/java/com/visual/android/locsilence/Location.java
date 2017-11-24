package com.visual.android.locsilence;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.location.places.Place;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by RamiK on 10/14/2017.
 */

public class Location implements Parcelable{

    private String id;
    private String name;
    private double lat;
    private double lng;
    private String createdAt;
    private String updatedAt;
    private int vol_ringtone;
    private int vol_notifications;
    private int vol_alarms;
    private String cid;
    private int rad;

    public Location(){}

    // default parcel constructor
    public Location(Parcel parcel) {
        this.id = parcel.readString();
        this.name = parcel.readString();
        this.createdAt = parcel.readString();
        this.updatedAt = parcel.readString();
        this.lat = parcel.readDouble();
        this.lng = parcel.readDouble();
        this.vol_ringtone = parcel.readInt();
        this.vol_notifications = parcel.readInt();
        this.vol_alarms = parcel.readInt();
        this.cid = parcel.readString();
        this.rad = parcel.readInt();
    }

    public Location(String id, String name, double lat, double lng,
                    String createdAt, String updatedAt, String cid, int rad) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.vol_ringtone = 0;
        this.vol_notifications = 0;
        this.vol_alarms = 0;
        this.cid=cid;
        this.rad=rad;
    }

    @Override
    public void writeToParcel(Parcel parcel, int args){
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.createdAt);
        parcel.writeString(this.updatedAt);
        parcel.writeDouble(this.lat);
        parcel.writeDouble(this.lng);
        parcel.writeInt(this.vol_ringtone);
        parcel.writeInt(this.vol_notifications);
        parcel.writeInt(this.vol_alarms);
        parcel.writeString(this.cid);
        parcel.writeInt(this.rad);
    }

    public static final Creator<Location> CREATOR=new Creator<Location>(){
        @Override
        public Location createFromParcel(Parcel parcel){
            return new Location(parcel);
        }
        @Override
        public Location[] newArray(int i) {
            return new Location[i];
        }
    };
    @Override
    public int describeContents(){return 0;}


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

    public int getVolRingtone() {
        return vol_ringtone;
    }

    public int getVolNotifications() {
        return vol_notifications;
    }

    public int getVolAlarms() {
        return vol_alarms;
    }

    public String getCid() { return cid; }

    public int getRad() { return rad; }

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

    public void setVolRingtone(int vol_ringtone) { this.vol_ringtone = vol_ringtone; }

    public void setVolNotifications(int vol_media) { this.vol_notifications = vol_notifications; }

    public void setVolAlarms(int vol_alarms) { this.vol_alarms = vol_alarms; }

    public void setCid(String cid) { this.cid = cid; }

    public void setRad(int rad) { this.rad = rad; }
}
