package echo.com.surveys.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit


object ApiClient {


    val TAG = ApiClient::class.java.simpleName
    private var retrofit: Retrofit? = null

    /**
     * get http client for short time
     *
     * @param baseUrl
     * @return
     */
    fun getClient(baseUrl: String): Retrofit? {
        if (retrofit == null) {
            val logging = HttpLoggingInterceptor()
            // set your desired log level
            logging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .authenticator(TokenAuthenticator())
                .build()



            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        }
        return retrofit

    }


    /**
     * get http client for long time
     *
     * @param baseUrl
     * @return
     */
    fun getLongTimeClient(baseUrl: String): Retrofit? {
        if (retrofit == null) {
            val logging = HttpLoggingInterceptor()
            // set your desired log level
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.connectTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)

            httpClient.addInterceptor(logging)  // <-- this is the important line!
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .client(httpClient.build())

                .build()
        }
        return retrofit

    }
}