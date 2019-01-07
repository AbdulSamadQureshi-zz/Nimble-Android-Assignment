package echo.com.surveys.util;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import echo.com.surveys.model.Auth;



public class SharedPrefUtility {

    private SharedPreferences sharedPreferences;

    public SharedPrefUtility(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;

    }


    public void savePrefrences(String key, String value) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(key, value).commit();
        }
    }

    public void savePrefrences(String key, int value) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putInt(key, value).commit();
        }
    }

    public void savePrefrences(String key, boolean value) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putBoolean(key, value).commit();
        }
    }

    public void savePrefrences(String key, long value) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putLong(key, value).commit();
        }
    }

    public void savePrefrences(String key, Object object) {
        if (sharedPreferences != null) {
            Gson gson = new Gson();
            String objectJson = gson.toJson(object);
            savePrefrences(key, objectJson);
        }
    }


    public String getStringValue(String key) {
        return sharedPreferences.getString(key, null);
    }

    public boolean getBooleanValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public int getIntValue(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public long getLongValue(String key) {
        return sharedPreferences.getLong(key, -1);
    }


    public void updateAuth(Auth auth) {
        Gson gson = new Gson();
        String authJson = gson.toJson(auth);
        //        TODO encript the json
        savePrefrences(Constants.Keys.AUTH, authJson);
    }


    public Auth getAuth() {
        Gson gson = new Gson();
        String json = getStringValue(Constants.Keys.AUTH);
        if (!TextUtils.isEmpty(json)) {
            return gson.fromJson(json, Auth.class);
        } else {
            return null;
        }
    }

}
