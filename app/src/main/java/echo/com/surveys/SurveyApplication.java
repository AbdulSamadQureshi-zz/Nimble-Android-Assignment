package echo.com.surveys;

import android.app.Application;
import echo.com.surveys.dependencyInjection.DIModule;
import echo.com.surveys.dependencyInjection.DIComponent;
import echo.com.surveys.dependencyInjection.DaggerDIComponent;

public class SurveyApplication extends Application {

    private static SurveyApplication instance;
    private DIComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerDIComponent.builder().dIModule(new DIModule(getApplicationContext()))
                .build();
    }
    public static SurveyApplication getInstance() {
        return instance;
    }
    public DIComponent component() {
        return component;
    }
}
