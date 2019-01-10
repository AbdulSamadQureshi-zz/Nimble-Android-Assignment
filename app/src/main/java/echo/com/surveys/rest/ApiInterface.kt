package echo.com.surveys.rest

import echo.com.surveys.model.Auth
import echo.com.surveys.model.AuthRequest
import echo.com.surveys.model.SurveyModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @GET("/surveys.json")
    fun getSurveys(
        @Query("access_token") token: String,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): Call<List<SurveyModel>>

    @POST("/oauth/token")
    fun getToken(@Body authRequest: AuthRequest): Call<Auth>
}