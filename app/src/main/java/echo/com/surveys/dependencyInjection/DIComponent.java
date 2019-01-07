package echo.com.surveys.dependencyInjection;

import dagger.Component;
import echo.com.surveys.activity.SurveyActivity;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DIModule.class})
public interface DIComponent {
    void inject(SurveyActivity activity);
}
