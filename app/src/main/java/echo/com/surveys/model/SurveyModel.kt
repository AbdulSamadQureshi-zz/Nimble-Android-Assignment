package echo.com.surveys.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SurveyModel :Serializable{
    @SerializedName("cover_image_url")
    @Expose
    var url: String? = null
    var title: String? = null
    var description: String? = null
    var isSelected: Boolean = false // for index adapter

    fun getHDUrl(): String {
        return url!! + "l"
    }


}