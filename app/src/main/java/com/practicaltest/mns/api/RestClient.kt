package com.practicaltest.mns.api


import com.practicaltest.mns.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient private constructor() {

    companion object {
        private lateinit var mApiServices: ApiInterface
        private var mInstance: RestClient? = null
        fun getInstance(): RestClient {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = RestClient()
                }
            }
            return mInstance!!
        }
    }

    init {
        val levelType: HttpLoggingInterceptor.Level =
            if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
                HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val logging = HttpLoggingInterceptor()
        logging.level = levelType

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(300, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .connectTimeout(300, TimeUnit.SECONDS)
            .addNetworkInterceptor(NetworkConnectionInterceptor())
            .build()

        val retrofit = Retrofit.Builder().baseUrl(Config.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mApiServices = retrofit.create(ApiInterface::class.java)
    }

    fun getApiService() = mApiServices
}