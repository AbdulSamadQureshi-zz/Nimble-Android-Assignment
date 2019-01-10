package echo.com.surveys

import android.app.Application
import android.arch.persistence.room.Room
import echo.com.surveys.db.SurveyDatabase
import echo.com.surveys.dependencyInjection.DIComponent
import echo.com.surveys.dependencyInjection.DIModule
import echo.com.surveys.dependencyInjection.DaggerDIComponent

class SurveyApplication : Application() {
    private var component: DIComponent? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(applicationContext, SurveyDatabase::class.java, "survey_db").fallbackToDestructiveMigration().build()
        component = DaggerDIComponent.builder().dIModule(DIModule(applicationContext))
            .build()
    }

    fun component(): DIComponent? {
        return component
    }

    companion object {

        var database: SurveyDatabase? = null
        var instance: SurveyApplication? = null
            private set
    }
}
