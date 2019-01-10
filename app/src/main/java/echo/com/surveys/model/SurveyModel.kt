package echo.com.surveys.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Surveys")
class SurveyModel :Serializable{
    @SerializedName("cover_image_url")
    @Expose
    var url: String? = null
    @PrimaryKey
    var title: String = ""
    var description: String? = null
    var isSelected: Boolean = false // for index adapter

    fun getHDUrl(): String {
        return url!! + "l"
    }


}