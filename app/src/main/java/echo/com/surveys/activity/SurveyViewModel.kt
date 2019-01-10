package echo.com.surveys.activity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import echo.com.surveys.model.SurveyModel
import echo.com.surveys.util.SharedPrefUtility

class SurveyViewModel : ViewModel() {
    lateinit var surveyRepository: SurveyRepository

    init {
            surveyRepository = SurveyRepository()
    }

    fun getAllSurveysFromDb(): LiveData<List<SurveyModel>>{
        return surveyRepository.getSurveysFromDb()
    }

    fun getSurveysFromApiAndStore(token: String){
        surveyRepository.reloadSurveys(token)
    }

    fun getSurveys(token: String){
        surveyRepository.getSurveys(token)
    }

    fun getAccessToken(sharedPrefUtility: SharedPrefUtility) {
        surveyRepository.getAccessToken(sharedPrefUtility)
    }
}













