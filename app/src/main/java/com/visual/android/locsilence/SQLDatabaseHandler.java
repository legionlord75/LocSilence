package com.visual.android.locsilence;

/**
 * Created by RamiK on 10/13/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class  SQLDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 7;

    // Database Name
    private static final String DATABASE_NAME = "Locations.db";

    // Contacts table name
    private static final String TABLE_LOCATIONS = "Locations";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LONG = "long";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_UPDATED_AT = "updated_at";
    private static final String KEY_VOL_RINGTONE = "vol_ringtone";
    private static final String KEY_VOL_MEDIA = "vol_media";
    private static final String KEY_VOL_ALARMS = "vol_alarms";
    private static final String KEY_VOL_CALL = "vol_call";
    private static final String KEY_CID = "circle_id";
    private static final String KEY_RAD = "radius";

    public SQLDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LOCATIONS + "("
                + KEY_ID + " VARCHAR(255) PRIMARY KEY," + KEY_NAME + " VARCHAR(255),"
                + KEY_LAT + " FLOAT(255, 255)," + KEY_LONG + " FLOAT(255, 255),"
                + KEY_CREATED_AT + " DATETIME," + KEY_UPDATED_AT + " DATETIME," +
                KEY_VOL_RINGTONE + " TINYINT(255)," + KEY_VOL_MEDIA + " TINYINT(255)," +
                KEY_VOL_ALARMS + " TINYINT(255)," + KEY_VOL_CALL + " TINYINT(255)," +
                KEY_CID + " VARCHAR(255)," + KEY_RAD + " TINY(255))";
        System.out.println(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public boolean addLocation(Location location) {
        boolean responseCode = true;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, location.getId());
        values.put(KEY_NAME, location.getName());
        values.put(KEY_LAT, location.getLat());
        values.put(KEY_LONG, location.getLng());
        values.put(KEY_CREATED_AT, location.getCreatedAt());
        values.put(KEY_UPDATED_AT, location.getUpdatedAt());
        values.put(KEY_VOL_RINGTONE, location.getVolRingtone());
        values.put(KEY_VOL_MEDIA, location.getVolNotifications());
        values.put(KEY_VOL_ALARMS, location.getVolAlarms());
        values.put(KEY_CID, location.getCid());
        values.put(KEY_RAD, location.getRad());

        // Inserting Row
        try {
            db.insertOrThrow(TABLE_LOCATIONS, null, values);
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
            responseCode = false;
        }

        db.close(); // Closing database connection
        return responseCode;
    }

    // Getting single contact
    public Location getLocation(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOCATIONS, new String[] { KEY_ID,
                KEY_NAME, KEY_LAT, KEY_LONG, KEY_CREATED_AT, KEY_UPDATED_AT,
                KEY_VOL_RINGTONE, KEY_VOL_MEDIA, KEY_VOL_ALARMS, KEY_VOL_CALL,
                        KEY_CID,KEY_RAD}, KEY_ID + "=?",
                new String[] { id }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        if (cursor != null && cursor.getCount() > 0) {
            Location location = new Location();
            location.setId(cursor.getString(0));
            location.setName(cursor.getString(1));
            location.setLat(cursor.getFloat(2));
            location.setLng(cursor.getFloat(3));
            location.setCreatedAt(cursor.getString(4));
            location.setUpdatedAt(cursor.getString(5));
            location.setVolRingtone(cursor.getInt(6));
            location.setVolNotifications(cursor.getInt(7));
            location.setVolAlarms(cursor.getInt(8));
            location.setCid(cursor.getString(9));
            location.setRad(cursor.getInt(10));

          //  cursor.close();
            return location;

        }

        cursor.close();

        return null;
    }

    // Getting All Contacts
    public List<Location> getAllLocations() {
        List<Location> allLocations = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Location location = new Location();
                location.setId(cursor.getString(0));
                location.setName(cursor.getString(1));
                location.setLat(cursor.getFloat(2));
                location.setLng(cursor.getFloat(3));
                location.setCreatedAt(cursor.getString(4));
                location.setUpdatedAt(cursor.getString(5));
                location.setVolRingtone(cursor.getInt(6));
                location.setVolNotifications(cursor.getInt(7));
                location.setVolAlarms(cursor.getInt(8));
                location.setCid(cursor.getString(9));
                location.setRad(cursor.getInt(10));
                // Adding contact to list
                allLocations.add(location);
            } while (cursor.moveToNext());
        }

        cursor.close();

        Collections.sort(allLocations, new Comparator<Location>() {
            public int compare(Location o1, Location o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        // return contact list
        return allLocations;
    }

    // Updating single contact
    public int updateLocalGame(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, location.getId());
        values.put(KEY_NAME, location.getName());
        values.put(KEY_LAT, location.getLat());
        values.put(KEY_LONG, location.getLng());
        values.put(KEY_CREATED_AT, location.getCreatedAt());
        values.put(KEY_UPDATED_AT, location.getUpdatedAt());
        values.put(KEY_VOL_RINGTONE, location.getVolRingtone());
        values.put(KEY_VOL_MEDIA, location.getVolNotifications());
        values.put(KEY_VOL_ALARMS, location.getVolAlarms());
        values.put(KEY_CID, location.getCid());
        values.put(KEY_RAD, location.getRad());
        // updating row
        return db.update(TABLE_LOCATIONS, values, KEY_ID + "='" + location.getId() + "'", null);
    }

    // Deleting single contact
    public void deleteLocalGame(String uniqueID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATIONS, KEY_ID + " = ?",
                new String[]{uniqueID});
        db.close();
    }


    // Getting contacts Count
    public int getLocalGamesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOCATIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cursorCount = cursor.getCount();
        cursor.close();

        // return count
        return cursorCount;
    }

    // Checks if Location already exists in db
    public boolean locationInDB(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from " + TABLE_LOCATIONS + " where " + KEY_ID + " = '" + id + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    // prints formatted DB for testing
    public void printDB() {
        List<Location> locations = getAllLocations();
        for (Location location : locations) {
            Log.i("logDB", "(name: " + location.getName() + ") | " +
                    "(LatLong: " + location.getLat() + ":" + location.getLng() + ") |" +
                    "(vol_ringtone: " + location.getVolRingtone() + ") |" +
                    "(vol_media: " + location.getVolNotifications() + ") |" +
                    "(vol_alarms: " + location.getVolAlarms() + ") |" +
                    "(ID: " + location.getId() + ") | " + "(Cid: " + location.getCid() + ") |" +
                    "(Radius: " + location.getRad() + ") |"
            );
        }

    }

}

