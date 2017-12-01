package com.visual.android.locsilence;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Thomas Brochard on 11/24/2017.
 */

public class Utility {

    public static RecursiveSilencePhoneTask recursiveSilencePhoneTask;
    public static boolean firstRecursiveExecution = true;

    public static void alertToast(Context context, String msg){
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
