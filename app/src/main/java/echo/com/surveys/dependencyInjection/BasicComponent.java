package echo.com.surveys.dependencyInjection;

import dagger.Component;
import echo.com.surveys.activity.SurveyActivity;

import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class})
public interface BasicComponent {
    void inject(SurveyActivity activity);
}
