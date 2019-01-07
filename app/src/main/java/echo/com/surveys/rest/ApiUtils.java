package echo.com.surveys.rest;

import android.content.Context;
import echo.com.surveys.util.Constants;

/**
 * Created by on 12/06/2017.
 */

public class ApiUtils {

    private ApiUtils() {
    }

    public static ApiInterface getAPIService(Context context) {
        return ApiClient.getClient(context, Constants.BASE_URL).create(ApiInterface.class);
    }

}
