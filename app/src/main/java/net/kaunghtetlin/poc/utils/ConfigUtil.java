package net.kaunghtetlin.poc.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kaung Htet Lin on 1/5/2018.
 */

public class ConfigUtil {


    private static final String KEY_PAGE_INDEX = "KEY_PAGE_INDEX";

    private SharedPreferences mSharedPreferences;

    private static ConfigUtil sObjInstance;

    private ConfigUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences("ConfigUtils", Context.MODE_PRIVATE);
    }

    public static void initConfigUtils(Context context) {
        sObjInstance = new ConfigUtil(context);
    }

    public static ConfigUtil getObjInstance() {
        return sObjInstance;
    }

    public void savePageIndex(int pageIndex) {
        mSharedPreferences.edit().putInt(KEY_PAGE_INDEX, pageIndex).apply();
    }

    public int loadPageIndex() {
        return mSharedPreferences.getInt(KEY_PAGE_INDEX, 1);
    }

}
