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
import javax.inject.Inject

class SurveyRepository{
    val PAGE_SIZE = 5
    var currentPage = 1

    @Inject
    lateinit var sharedPrefUtility: SharedPrefUtility

    fun getSurveys(): LiveData<List<SurveyModel>> {
        return SurveyApplication.database!!.surveyDao().getAllSurveys()
    }

    fun ApiCallAndPutInDB() {

//        val token = sharedPrefUtility.auth?.accessToken
        val token  = "3d716b21569b8dd3364b3864c8ab55293543d65a56dbf9d79eb7b9ca34943adb"
//        if (showProgress) {
//            showProgress()
//        }
        ApiUtils.getAPIService().getSurveys(token!!,currentPage,PAGE_SIZE).enqueue(object : Callback<List<SurveyModel>> {
            override fun onFailure(call: Call<List<SurveyModel>>, t: Throwable) {
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
                        SurveyApplication.database!!.surveyDao().deleteAllSurveys()
                        SurveyApplication.database!!.surveyDao().insertAllSurveys(response.body()!!)
                    }).start()

//                    updateIndexRecyclerView(response.body()!!)
//                    updateViewPager(response.body()!!)
                } else {
                    if(response.code() == 200){
//                        DialogUtils.showToast(this@SurveyActivity, getString(R.string.no_more_surveys))
                    } else {
//                        DialogUtils.showToast(this@SurveyActivity, getString(R.string.general_error))
                    }
                }
            }
        })
    }


    fun getAccessToken() {
        val authRequest = AuthRequest()
//        showProgress()
        ApiUtils.getAPIService().getToken(authRequest).enqueue(object : Callback<Auth> {
            override fun onFailure(call: Call<Auth>, t: Throwable) {
//                hideProgres()
//                DialogUtils.showToast(this@SurveyActivity, getString(R.string.general_error))
            }

            override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
//                hideProgres()
                if (response.body() != null) {
                    sharedPrefUtility?.updateAuth(response.body())
                    ApiCallAndPutInDB()
                }
            }

        })
    }

}