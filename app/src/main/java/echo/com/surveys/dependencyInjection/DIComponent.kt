package echo.com.surveys.dependencyInjection

import dagger.Component
import echo.com.surveys.activity.SurveyActivity

import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(DIModule::class))
interface DIComponent {
    fun inject(activity: SurveyActivity)
}
