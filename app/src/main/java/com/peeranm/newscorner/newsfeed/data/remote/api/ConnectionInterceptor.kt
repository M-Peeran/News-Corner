package com.peeranm.newscorner.newsfeed.data.remote.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

/**
 * Created by Peeran M on 25-09-2022
 *
 * [ConnectionInterceptor] will notify about the network status when a network call is made
 */

class ConnectionInterceptor(private val context: Context): Interceptor {

    private fun preAndroidMInternetCheck(connectivityManager: ConnectivityManager): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo ?: return false
        val hasWifiConnection = activeNetwork.type == ConnectivityManager.TYPE_WIFI
        val hasCellularConnection = activeNetwork.type == ConnectivityManager.TYPE_MOBILE
        return hasWifiConnection or hasCellularConnection
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun postAndroidMInternetCheck(connectivityManager: ConnectivityManager): Boolean {
        val connection = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            ?: return false
        val hasWifiConnection = connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        val hasCellularConnection = connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        return hasWifiConnection or hasCellularConnection
    }

    private fun isConnectionOn(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) postAndroidMInternetCheck(connectivityManager)
        else preAndroidMInternetCheck(connectivityManager)
    }

    private fun isInternetAvailable(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockAddress = InetSocketAddress("8.8.8.8", 53)
            sock.connect(sockAddress, timeoutMs)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }
    }

    @Throws(NoConnectivityException::class, NoInternetException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isConnectionOn()) throw NoConnectivityException()
        else if(!isInternetAvailable()) throw NoInternetException()
        else chain.proceed(chain.request())
    }
}

class NoConnectivityException : IOException("No network available, please check your WiFi or Data connection")
class NoInternetException : IOException("No internet available, please check your connected WIFi or Data")
