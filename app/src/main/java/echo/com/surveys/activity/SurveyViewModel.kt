package echo.com.surveys.activity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import echo.com.surveys.model.SurveyModel

class SurveyViewModel : ViewModel() {
    lateinit var surveyRepository: SurveyRepository

    init {
            surveyRepository = SurveyRepository()
    }

    fun getAllSurveys(): LiveData<List<SurveyModel>>{
        return surveyRepository.getSurveys()
    }

    fun getSurveysFromApiAndStore(){
        surveyRepository.ApiCallAndPutInDB()
    }
}













