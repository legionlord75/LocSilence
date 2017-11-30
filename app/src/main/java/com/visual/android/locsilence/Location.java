package com.visual.android.locsilence;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.location.places.Place;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by RamiK on 10/14/2017.
 */

public class Location implements Parcelable{

    private String id;
    private String name;
    private String address;
    private double lat;
    private double lng;
    private String createdAt;
    private String updatedAt;
    private int vol_ringtone;
    private int vol_notifications;
    private int vol_alarms;
    private String cid;
    private int rad;
    private String customProximity;

    public Location(){}

    public Location(String id, String name, String address, double lat, double lng,
                    String createdAt, String updatedAt, String cid) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.vol_ringtone = 0;
        this.vol_notifications = 0;
        this.vol_alarms = 0;
        this.cid=cid;
        this.rad=100;
        this.customProximity = "null";
    }
    // default parcel constructor
    public Location(Parcel parcel) {
        this.id = parcel.readString();
        this.name = parcel.readString();
        this.address = parcel.readString();
        this.lat = parcel.readDouble();
        this.lng = parcel.readDouble();
        this.createdAt = parcel.readString();
        this.updatedAt = parcel.readString();
        this.vol_ringtone = parcel.readInt();
        this.vol_notifications = parcel.readInt();
        this.vol_alarms = parcel.readInt();
        this.cid = parcel.readString();
        this.rad = parcel.readInt();
        this.customProximity = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int args){
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.address);
        parcel.writeDouble(this.lat);
        parcel.writeDouble(this.lng);
        parcel.writeString(this.createdAt);
        parcel.writeString(this.updatedAt);
        parcel.writeInt(this.vol_ringtone);
        parcel.writeInt(this.vol_notifications);
        parcel.writeInt(this.vol_alarms);
        parcel.writeString(this.cid);
        parcel.writeInt(this.rad);
        parcel.writeString(this.customProximity);
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

    public String getAddress(){ return address; }

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

    public List<Integer> getVolumes(){
        return Arrays.asList(this.vol_ringtone, this.vol_notifications, this.vol_alarms);
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

    public String getCustomProximity(){ return customProximity; }

    // SET methods

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address){ this.address = address; }

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

    public void setVolumes(List<Integer> volumeLevels){
        this.setVolRingtone(volumeLevels.get(0));
        this.setVolNotifications(volumeLevels.get(1));
        this.setVolAlarms(volumeLevels.get(2));
    };

    public void setVolRingtone(int vol_ringtone) { this.vol_ringtone = vol_ringtone; }

    public void setVolNotifications(int vol_notifications) { this.vol_notifications = vol_notifications; }

    public void setVolAlarms(int vol_alarms) { this.vol_alarms = vol_alarms; }

    public void setCid(String cid) { this.cid = cid; }

    public void setRad(int rad) { this.rad = rad; }

    public void setCustomProximity(String customProximity){ this.customProximity = customProximity; }

    public void printLocation(){
        Log.i("logDB", "Location: (name: " + this.getName() + ") | " +
                "(Address: " + this.getAddress() + ") | " +
                "(LatLong: " + this.getLat() + ":" + this.getLng() + ") |" +
                "(vol_ringtone: " + this.getVolRingtone() + ") |" +
                "(vol_media: " + this.getVolNotifications() + ") |" +
                "(vol_alarms: " + this.getVolAlarms() + ") |" +
                "(ID: " + this.getId() + ") | " + "(Cid: " + this.getCid() + ") |" +
                "(Radius: " + this.getRad() + ") |" + "(customProx: " + this.getCustomProximity() + ") |"
        );
    }
}
