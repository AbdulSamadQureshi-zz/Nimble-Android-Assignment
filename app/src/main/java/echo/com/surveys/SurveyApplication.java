package echo.com.surveys;

import android.app.Application;
import android.content.Context;
import echo.com.surveys.dependencyInjection.AppModule;
import echo.com.surveys.dependencyInjection.BasicComponent;
import echo.com.surveys.dependencyInjection.DaggerBasicComponent;

public class SurveyApplication extends Application {

    private static Context context;
    private static SurveyApplication app;
    public static Context getContext(){
        return context;
    }
    private BasicComponent basicComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        app = this;
        basicComponent = DaggerBasicComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }
    public static SurveyApplication app() {
        return app;
    }
    public BasicComponent basicComponent() {
        return basicComponent;
    }
}
