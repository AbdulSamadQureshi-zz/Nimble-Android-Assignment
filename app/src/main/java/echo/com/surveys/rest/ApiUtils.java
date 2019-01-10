package echo.com.surveys.rest;

import echo.com.surveys.util.Constants;

public class ApiUtils {

    private ApiUtils() {
    }

    public static ApiInterface getAPIService() {
        return ApiClient.getClient(Constants.BASE_URL).create(ApiInterface.class);
    }

}
