package com.visual.android.locsilence;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Thomas Brochard on 11/24/2017.
 */

public class Utility {

    public static RecursiveSilencePhoneTask recursiveSilencePhoneTask;
    public static boolean firstRecursiveExecution = true;
    private static final String TAG = CustomProximityMap.class.getSimpleName();


    public static String cropText(String text, int max_length, String append){
        if(text.length() > max_length)
            return text.substring(0, max_length-append.length()) + append;
        else
            return text;
    }

    public static void alertToast(Context context, String msg){
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public ArrayList<LatLng> customProxToList(String customProximityJson){
        try{
            Type listType =
                    new TypeToken<ArrayList<LatLng>>() {
                    }.getType();
            ArrayList<LatLng> boundary = new Gson().fromJson(customProximityJson, listType);
            return boundary;
        }
        catch(JsonParseException e){
            Log.e(TAG, "Error parsing json custom proximity boundary\n" + e.getStackTrace());
            return null;
        }
    }


}
