package com.peter.schoolmarket.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by PetterChen on 2017/4/13.
 */

public class BroadUtils extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //表明发送端就是发送的是CONNECTIVITY_ACTION这个动作
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            if (isNetConnected(context)) {
                //Toast.makeText(context, "连接上网络了", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "无网络连接", Toast.LENGTH_SHORT).show();
            }


        }
    }
    public static boolean isNetConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            return false;
        } else {
            return networkInfo.isAvailable() & networkInfo.isConnected();
        }
    }
    public static boolean isPhoneNetConnected(Context context) {
        int typeMobile = ConnectivityManager.TYPE_MOBILE;//手机网络类型
        return isNetworkConnected(context, typeMobile);
    }
    public static boolean isWifiNetConnected(Context context) {
        int typeMobile = ConnectivityManager.TYPE_WIFI;//WIFI网络类型
        return isNetworkConnected(context, typeMobile);
    }
    private static boolean isNetworkConnected(Context context, int typeMobile) {
        if (!isNetConnected(context)) {
            return false;
        }
        ConnectivityManager connectManger = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectManger.getNetworkInfo(typeMobile);
        if (networkInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}
