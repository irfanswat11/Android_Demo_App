package com.irfanulhaq.restaurantreservation.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.google.common.primitives.Ints;
import com.irfanulhaq.restaurantreservation.mvp.models.CustomerModel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

/*data manipulation from different local sources
 * will be handled here
 *
 *
 * */
public class DataUtils {

    public static <T> boolean saveData(Context context, List<T> list, String key) {
        String jsonString = (list == null) ? null : JsonUtility.fromListToJsonString(list);
        return DataUtils.SharedPrefDataUtils.setStringSharedPref(context, key, jsonString);
    }

    public static <T, V> boolean saveData(Context context, HashMap<T, V> hashMap, String key) {
        String jsonString = (hashMap == null || !(hashMap.keySet().size() > 0)) ? null : JsonUtility.toJsonFromHashMap(hashMap);
        return DataUtils.SharedPrefDataUtils.setStringSharedPref(context, key, jsonString);
    }

    public static <T> ArrayList<T> getData(Class<T> clazz, Context context, String key) throws IOException {
        String jsonString = DataUtils.SharedPrefDataUtils.getStringSharedPref(context, key);
        return (jsonString == null) ? null : (ArrayList<T>) JsonUtility.toListFromJsonString(clazz, jsonString);
    }

    public static <T, V> HashMap<T, V> getData(Class<T> kyClazz, Class<V> vlClazz, Context context, String key) throws IOException {
        String jsonString = DataUtils.SharedPrefDataUtils.getStringSharedPref(context, key);
        return (jsonString == null) ? null : JsonUtility.toHashMap(kyClazz, vlClazz, jsonString);
    }


    public static class SharedPrefDataUtils {
        //String operations
        public static boolean setStringSharedPref(Context context, String key, String value) {
            SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = defaultSharedPref.edit();
            if (defaultSharedPref != null && !TextUtils.isEmpty(key)) {
                editor.putString(key, value);
                return editor.commit();
            }
            return false;
        }

        public static String getStringSharedPref(Context context, String key) {
            SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            if (defaultSharedPref != null) {
                return defaultSharedPref.getString(key, null);
            }
            return null;
        }

        //boolean operations
        public static boolean setBooleanSharedPref(Context context, String key, boolean value) {
            SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = defaultSharedPref.edit();
            if (defaultSharedPref != null && !TextUtils.isEmpty(key)) {
                editor.putBoolean(key, value);
                return editor.commit();
            }
            return false;
        }

        public static boolean getBooleanSharedPref(Context context, String key) {
            SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            if (defaultSharedPref != null) {
                return defaultSharedPref.getBoolean(key, false);
            }
            return false;
        }

        /*More operation for Int, double,long could be added*/


    }

    public static class DatabaseDataUtils {
        //Database operations
    }

    public static class fileDataUtils {
        //operation for data on file
    }
}
