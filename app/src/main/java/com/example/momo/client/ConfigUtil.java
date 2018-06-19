package com.example.momo.client;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by momo on 18-6-19.
 */

public class ConfigUtil {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String userJson;

    public static final String USER_KEY = "user_key";
    public String getUserJson() {
        return sp.getString(USER_KEY, "");
    }

    public void setUserJson(String userJson) {
        editor.putString(USER_KEY, userJson);
        editor.commit();
    }

    public ConfigUtil(Context ctx){
        sp = ctx.getSharedPreferences("my_sp", Context.MODE_PRIVATE);
        editor = sp.edit();
    }
}
