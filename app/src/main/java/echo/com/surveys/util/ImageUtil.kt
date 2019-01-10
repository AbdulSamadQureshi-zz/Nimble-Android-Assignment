package echo.com.surveys.util

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import com.squareup.picasso.Picasso
import echo.com.surveys.R


object ImageUtil {


    fun loadResizedImage(context: Context, imageView: ImageView, uri: String) {
        var uri = uri
        if (!TextUtils.isEmpty(uri)) {

            if (uri.contains("graph.facebook.com")) {
                uri = uri.replace("http".toRegex(), "https")
            }
            Picasso.with(context).load(uri).error(R.drawable.placeholder).fit().centerCrop().into(imageView)
        }
    }

}
