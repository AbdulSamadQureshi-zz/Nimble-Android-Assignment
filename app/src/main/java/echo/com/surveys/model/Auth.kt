package echo.com.surveys.model

import com.google.gson.annotations.SerializedName

class Auth {

    @SerializedName("access_token")
    var accessToken: String? = null
    @SerializedName("token_type")
    var tokenType: String? = null
    @SerializedName("expires_in")
    var expiresIn: Int? = null
    @SerializedName("created_at")
    var createdAt: Int? = null
}