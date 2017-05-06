package com.peter.schoolmarket.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by PetterChen on 2017/5/6.
 */

public class TimeUtils {
    /*public static String timeDiff(long time){
        long diffMinute =(System.currentTimeMillis()-time)/60000;
        if (diffMinute < 60){
            return diffMinute+" minutes ago";
        }else if (diffMinute < 24*60){
            return diffMinute/60 +" hours ago";
        }else if (diffMinute < 24*60*30){
            return diffMinute/60/24 +" days ago";
        }else {
            return diffMinute/60/24/30 +" months ago";
        }
    }
    */
    // yyMMdd hh:mm:ss  yyyy-MM-dd hh:mm:ss  dd-MM-yyyy hh:mm:ss
    public static String getDateAndTime(long millTime) {
        Date d = new Date(millTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        //Log.d("time", "" + sdf.format(d));
        return sdf.format(d);
    }

    public static String getDate(long millTime) {
        Date d = new Date(millTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String[] tem = sdf.format(d).split(" ");
        return tem[0];
    }

    public static String getTime(long millTime) {
        Date d = new Date(millTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String[] tem = sdf.format(d).split(" ");
        return tem[1];
    }

}
