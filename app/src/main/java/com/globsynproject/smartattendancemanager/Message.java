package com.globsynproject.smartattendancemanager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * This is the class to display Toast or Debugging log messages.
 */

public class Message {
    /**This is the method to display toast messages, passing null in the length will
     * give a toast of short duration.
     */
    public static void toastMessage(Context context,String msg,String length){
        if(length.equalsIgnoreCase("long"))
            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    /**This is the method to display debugging log with the appropriate tag and message.*/
    public static void logMessages(String tag,String msg){
        Log.d(tag,msg);
    }
}