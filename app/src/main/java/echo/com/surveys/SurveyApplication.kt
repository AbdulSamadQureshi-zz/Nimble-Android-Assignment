package echo.com.surveys

import android.app.Application
import echo.com.surveys.dependencyInjection.DIComponent
import echo.com.surveys.dependencyInjection.DIModule
import echo.com.surveys.dependencyInjection.DaggerDIComponent

class SurveyApplication : Application() {
    private var component: DIComponent? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        component = DaggerDIComponent.builder().dIModule(DIModule(applicationContext))
            .build()
    }

    fun component(): DIComponent? {
        return component
    }

    companion object {
        var instance: SurveyApplication? = null
            private set
    }
}
