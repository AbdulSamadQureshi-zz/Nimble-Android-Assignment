package echo.com.surveys.activity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import echo.com.surveys.model.SurveyModel
import echo.com.surveys.state.NetworkState
import echo.com.surveys.util.SharedPrefUtility

class SurveyViewModel : ViewModel() {
     var surveyRepository: SurveyRepository

    private val state = MutableLiveData<NetworkState>()

    fun getState(): LiveData<NetworkState> {
        return state
    }

    fun setSurveyFetchingState(isFetching: Boolean) {
        state.value?.isFetching = isFetching
    }


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













