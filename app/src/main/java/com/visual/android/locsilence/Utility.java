package com.visual.android.locsilence;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Thomas Brochard on 11/24/2017.
 */

public class Utility {

    public static RecursiveSilencePhoneTask recursiveSilencePhoneTask;
    public static boolean firstRecursiveExecution = true;

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

}
