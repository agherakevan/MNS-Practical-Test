package com.practicaltest.mns.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Created by kevan on 26/11/2020.
 */

class NetworkConnectionInterceptor(/*context: Context*/) : Interceptor {

    //private val mContext: Context = context

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        /*if (!isInternetAvailable(mContext)) {
            throw NoConnectivityException()
            // Throwing our custom exception 'NoConnectivityException'
        }*/
        val builder: Request.Builder =
            chain.request().newBuilder()

        val response = chain.proceed(builder.build())

        when (response.code) {
            404 -> {
                throw NoConnectivityException()
            }
            503 -> {
                throw NoConnectivityException()
            }
            else -> {
                return response
            }
        }
    }

    @Suppress("DEPRECATION")
    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

}