package echo.com.surveys.util

import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson
import echo.com.surveys.model.Auth


class SharedPrefUtility(private val sharedPreferences: SharedPreferences?, private val gson: Gson) {


    val auth: Auth?
        get() {
            val json = getStringValue(Constants.Keys.AUTH)
            return if (!TextUtils.isEmpty(json)) {
                gson.fromJson(json, Auth::class.java)
            } else {
                null
            }
        }


    fun savePrefrences(key: String, value: String) {
        sharedPreferences?.edit()?.putString(key, value)?.commit()
    }

    fun savePrefrences(key: String, value: Int) {
        sharedPreferences?.edit()?.putInt(key, value)?.commit()
    }

    fun savePrefrences(key: String, value: Boolean) {
        sharedPreferences?.edit()?.putBoolean(key, value)?.commit()
    }

    fun savePrefrences(key: String, value: Long) {
        sharedPreferences?.edit()?.putLong(key, value)?.commit()
    }

    fun savePrefrences(key: String, `object`: Any) {
        if (sharedPreferences != null) {
            val objectJson = gson.toJson(`object`)
            savePrefrences(key, objectJson)
        }
    }


    fun getStringValue(key: String): String? {
        return sharedPreferences!!.getString(key, null)
    }

    fun getBooleanValue(key: String): Boolean {
        return sharedPreferences!!.getBoolean(key, false)
    }

    fun getIntValue(key: String): Int {
        return sharedPreferences!!.getInt(key, 0)
    }

    fun getLongValue(key: String): Long {
        return sharedPreferences!!.getLong(key, -1)
    }


    fun updateAuth(auth: Auth) {
        val authJson = gson.toJson(auth)
        //        TODO encript the json
        savePrefrences(Constants.Keys.AUTH, authJson)
    }

}
