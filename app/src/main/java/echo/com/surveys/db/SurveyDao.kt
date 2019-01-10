package echo.com.surveys.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import echo.com.surveys.model.SurveyModel

@Dao
interface SurveyDao{
    @Query("SELECT * FROM Surveys")
    fun getAllSurveys(): LiveData<List<SurveyModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSurveys(surveyList : List<SurveyModel>)

    @Query("DELETE FROM Surveys")
    fun deleteAllSurveys()

}