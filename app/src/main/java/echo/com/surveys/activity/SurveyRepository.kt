package echo.com.surveys.activity

import android.arch.lifecycle.LiveData
import echo.com.surveys.SurveyApplication
import echo.com.surveys.model.Auth
import echo.com.surveys.model.AuthRequest
import echo.com.surveys.model.SurveyModel
import echo.com.surveys.rest.ApiUtils
import echo.com.surveys.util.SharedPrefUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurveyRepository{
    val PAGE_SIZE = 5
    var currentPage = 0


    fun getSurveysFromDb(): LiveData<List<SurveyModel>> {
        return SurveyApplication.database!!.surveyDao().getAllSurveys()
    }

    fun reloadSurveys(token:String) {

        currentPage = 0

        Thread(Runnable {
                SurveyApplication.database!!.surveyDao().deleteAllSurveys()
        }).start()
        getSurveys(token)
    }

    fun getSurveys(token: String){
//            showProgress()
        currentPage++
        ApiUtils.apiService.getSurveys(token,currentPage,PAGE_SIZE).enqueue(object : Callback<List<SurveyModel>> {
            override fun onFailure(call: Call<List<SurveyModel>>, t: Throwable) {
                currentPage--
//                hideProgres()
//                DialogUtils.showToast(this@SurveyActivity, getString(R.string.general_error))
            }

            override fun onResponse(call: Call<List<SurveyModel>>, response: Response<List<SurveyModel>>) {
//                hideProgres()
                if (response.body() != null) {
                    if(currentPage == 1 && response.body()!!.isNotEmpty()){
                        // to show first index of indexer as selected
                        response.body()!![0].isSelected = true
                    }
                    Thread(Runnable {
                        SurveyApplication.database!!.surveyDao().insertAllSurveys(response.body()!!)
                    }).start()

//                    updateIndexRecyclerView(response.body()!!)
//                    updateViewPager(response.body()!!)
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
        val authRequest = AuthRequest()
//        showProgress()
        ApiUtils.apiService.getToken(authRequest).enqueue(object : Callback<Auth> {
            override fun onFailure(call: Call<Auth>, t: Throwable) {
//                hideProgres()
//                DialogUtils.showToast(this@SurveyActivity, getString(R.string.general_error))
            }

            override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
//                hideProgres()
                if (response.body() != null) {
                    sharedPrefUtility.updateAuth(response.body()!!)
                    reloadSurveys(sharedPrefUtility.auth?.accessToken!!)
                }
            }

        })
    }

}