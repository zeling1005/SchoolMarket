package com.peter.schoolmarket.data.storage;

import android.content.Context;

import com.peter.schoolmarket.data.pojo.User;

/**
 * Created by PetterChen on 2017/4/20.
 */

public class LoginInfoExecutor {

    public static User getUser(final Context context) {
        return ManageLoginInfo.getInstance().getUser(context);
    }

    public static void logIn(final Context context, User user){
        ManageLoginInfo.getInstance().saveUser(context, user);
    }

    public static void logOut(final Context context){
        ManageLoginInfo.getInstance().clear(context);
    }
}
