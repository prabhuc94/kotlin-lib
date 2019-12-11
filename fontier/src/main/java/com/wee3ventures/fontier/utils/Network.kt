package com.wee3ventures.fontier.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import org.jetbrains.anko.connectivityManager
import java.net.URL
import java.net.URLConnection


object Network {

    fun isNetworkAvailable(mContext : Context?) : Boolean {
        val cm = mContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun isConnectionAvailable(context: Context?): Boolean {
        var isMobile = false
        var isWifi = false
        val infoAvailableNetworks: Array<NetworkInfo> = context?.connectivityManager?.allNetworkInfo!!
        if (infoAvailableNetworks != null) {
            for (network in infoAvailableNetworks) {
                if (network.type == ConnectivityManager.TYPE_WIFI) {
                    if (network.isConnected && network.isAvailable) isWifi = true
                }
                if (network.type == ConnectivityManager.TYPE_MOBILE) {
                    if (network.isConnected && network.isAvailable) isMobile = true
                }
            }
        }
        return isMobile || isWifi
    }

    private fun isAbleToConnect(url: String ?= "http://www.google.com", timeout: Int ?= 1000): Boolean {
        return try {
            val myUrl = URL(url)
            val connection: URLConnection = myUrl.openConnection()
            timeout?.let { connection.connectTimeout = it }
            connection.connect()
            true
        } catch (e: Exception) {
            Console.log("Network",message = "Exception:\t$e",throwable = e)
            false
        }
    }
}