package echo.com.surveys.rest;

import echo.com.surveys.model.Auth;
import echo.com.surveys.model.AuthRequest;
import echo.com.surveys.model.Survey;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiInterface {

    @GET("/surveys.json")
    Call<List<Survey>> getSurveys(@Query("access_token") String token);

    @POST("/oauth/token")
    Call<Auth> getToken(@Body AuthRequest authRequest);
}
