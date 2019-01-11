package echo.com.surveys.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import echo.com.surveys.rest.ApiUtils
import echo.com.surveys.state.NetworkState
import echo.com.surveys.util.SharedPrefUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurveyRepository{
    val PAGE_SIZE = 5
    var currentPage = 0
    private val surveyList = ArrayList<SurveyModel>()
    private val surveys = MutableLiveData<List<SurveyModel>>()
    private val state = MutableLiveData<NetworkState>()

    fun getSurveys() : LiveData<List<SurveyModel>>{
        return surveys
    }

    fun reloadSurveys(token:String) {
        currentPage = 0
        surveyList.clear()
        surveys.value = surveyList
        loadSurveys(token)
    }


    fun loadSurveys(token: String){
        currentPage++
        setNetworkState(true)
        ApiUtils.apiService.getSurveys(token,currentPage,PAGE_SIZE).enqueue(object : Callback<List<SurveyModel>> {
            override fun onFailure(call: Call<List<SurveyModel>>, t: Throwable) {
                currentPage--
                setNetworkState(false)
//                DialogUtils.showToast(this@SurveyActivity, getString(R.string.general_error))
            }

            override fun onResponse(call: Call<List<SurveyModel>>, response: Response<List<SurveyModel>>) {
                setNetworkState(false)
                if (response.body() != null) {
                    if(currentPage == 1 && response.body()!!.isNotEmpty()){
                        // to show first index of indexer as selected
                        response.body()!![0].isSelected = true
                    }
                    surveyList.addAll(response.body()!!)
                    surveys.value = surveyList
                } else {
                    currentPage--
                    if(response.code() == 200){
//                        DialogUtils.showToast(this@SurveyActivity, getString(R.string.no_more_surveys))
                    } else {
//                        DialogUtils.showToast(this@SurveyActivity, getString(R.string.general_error))
                    }
                }
            }
        })
    }


    fun getAccessToken(sharedPrefUtility: SharedPrefUtility) {
        setNetworkState(true)
        val authRequest = AuthRequest()
        ApiUtils.apiService.getToken(authRequest).enqueue(object : Callback<Auth> {
            override fun onFailure(call: Call<Auth>, t: Throwable) {
                setNetworkState(false)
//                DialogUtils.showToast(this@SurveyActivity, getString(R.string.general_error))
            }

            override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
//                hideProgres()
                setNetworkState(false)
                if (response.body() != null) {
                    sharedPrefUtility.updateAuth(response.body()!!)
                    reloadSurveys(sharedPrefUtility.auth?.accessToken!!)
                }
            }

        })
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return state
    }

    fun setNetworkState(isFetching: Boolean) {
        state.value = NetworkState(isFetching)
    }

}