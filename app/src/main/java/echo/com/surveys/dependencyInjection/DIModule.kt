package echo.com.surveys.dependencyInjection


import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import echo.com.surveys.util.SharedPrefUtility

import javax.inject.Singleton

@Module
class DIModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideSharedPreferenceUtility(sharedPreferences: SharedPreferences, gson: Gson): SharedPrefUtility {
        return SharedPrefUtility(sharedPreferences, gson)
    }

}
