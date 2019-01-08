package echo.com.surveys.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import echo.com.surveys.R;


public class ImageUtil {

    private ImageUtil() {

    }


    public static void loadResizedImage(Context context, ImageView imageView, String uri) {
        if (!TextUtils.isEmpty(uri)) {

            if (uri.contains("graph.facebook.com")) {
                uri = uri.replaceAll("http", "https");
            }
            Picasso.with(context).load(uri).error(R.drawable.placeholder).fit().centerCrop().into(imageView);
        }
    }

}
