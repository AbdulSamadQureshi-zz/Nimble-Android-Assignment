package echo.com.surveys.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import echo.com.surveys.rest.ApiUtils
import echo.com.surveys.state.MessageState
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
    private val networkState = MutableLiveData<NetworkState>()
    private val messageState = MutableLiveData<MessageState>()

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
                setMessageState("Something went wrong, please try again later...")
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
//                        TODO use strings.xml for strings
                        setMessageState("No more surveys available...")
                    } else {
                        setMessageState("Something went wrong, please try again later...")
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
                setMessageState("Something went wrong, please try again later...")
            }

            override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                setNetworkState(false)
                if (response.body() != null) {
                    sharedPrefUtility.updateAuth(response.body()!!)
                    reloadSurveys(sharedPrefUtility.auth?.accessToken!!)
                }
            }

        })
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return networkState
    }

    fun setNetworkState(isFetching: Boolean) {
        networkState.value = NetworkState(isFetching)
    }

    fun getMessageState(): LiveData<MessageState> {
        return messageState
    }

    fun setMessageState(message: String) {
        messageState.value = MessageState(message)
    }

}