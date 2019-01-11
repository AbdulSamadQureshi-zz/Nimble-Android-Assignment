package echo.com.surveys.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import echo.com.surveys.state.NetworkState
import echo.com.surveys.util.SharedPrefUtility

class SurveyViewModel : ViewModel() {
    private var surveyRepository: SurveyRepository = SurveyRepository()
    private val state = MutableLiveData<NetworkState>()

    fun getState(): LiveData<NetworkState> {
        return state
    }

    fun getSurveys(): LiveData<List<SurveyModel>> {
        return surveyRepository.getSurveys()
    }

    fun setSurveyFetchingState(isFetching: Boolean) {
        state.value?.isFetching = isFetching
    }

    // for first time fetching
    fun getSurveysFromApi(token: String) {
        surveyRepository.reloadSurveys(token)
    }

    // for pagination
    fun loadSurveys(token: String) {
        surveyRepository.loadSurveys(token)
    }

    fun getAccessToken(sharedPrefUtility: SharedPrefUtility) {
        surveyRepository.getAccessToken(sharedPrefUtility)
    }
}













