package echo.com.surveys.rest

import echo.com.surveys.util.Constants

object ApiUtils {

    val apiService: ApiInterface
        get() = ApiClient.getClient(Constants.BASE_URL)!!.create(ApiInterface::class.java)

}
