package echo.com.surveys.dependencyInjection;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;
import echo.com.surveys.util.SharedPrefUtility;

import javax.inject.Singleton;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context provideContext(){
        return context;
    }

    @Singleton @Provides
    public SharedPreferences provideSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton @Provides
    public SharedPrefUtility provideObjectManager(SharedPreferences sharedPreferences){
        return new SharedPrefUtility(sharedPreferences);
    }

}
