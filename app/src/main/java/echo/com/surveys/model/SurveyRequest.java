package echo.com.surveys.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyRequest {
    int page = 1;
    int perPage = 10;
    @SerializedName("access_token")
    @Expose
    String accessToken = "";

    public SurveyRequest(int page, int perPage, String accessToken) {
        this.page = page;
        this.perPage = perPage;
        this.accessToken = accessToken;
    }

    public SurveyRequest(String accessToken) {
        this.accessToken = accessToken;
    }
}
