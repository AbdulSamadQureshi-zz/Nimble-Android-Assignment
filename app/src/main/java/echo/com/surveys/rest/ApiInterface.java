package echo.com.surveys.rest;

import echo.com.surveys.model.Auth;
import echo.com.surveys.model.AuthRequest;
import echo.com.surveys.model.Survey;
import echo.com.surveys.model.SurveyRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by on 10/06/2017.
 */


public interface ApiInterface {


    @GET("/surveys.json")
    Call<Survey> getSurveys(@Body SurveyRequest surveyRequest);

    @POST("/oauth/token")
    Call<Auth> signInUser(@Body AuthRequest authRequest);
}
