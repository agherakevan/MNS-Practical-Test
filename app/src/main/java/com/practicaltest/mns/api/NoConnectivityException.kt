package com.practicaltest.mns.api

import java.io.IOException

/**
 * Created by kevan on 26/11/2020.
 */

class NoConnectivityException : IOException() {

    override val message: String
        get() = "No Internet Connection"
    // You can send any message whatever you want from here.
}