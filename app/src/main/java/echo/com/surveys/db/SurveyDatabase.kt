package echo.com.surveys.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import echo.com.surveys.model.SurveyModel

@Database(entities = [(SurveyModel::class)], version = 1)
abstract class SurveyDatabase : RoomDatabase() {

    abstract fun surveyDao(): SurveyDao

}