package com.simform.androidstartertemplate.extension

import android.content.Context
import android.net.ConnectivityManager
import android.view.View

/**
 * Enables a view by settings [View.isEnabled] property to true
 */
fun View.enable() {
    isEnabled = true
}

/**
 * Disables a view by settings [View.isEnabled] property to false
 */
fun View.disable() {
    isEnabled = false
}

/**
 * Extension function to check whether internet connectivity is available or not
 * @return true is internet connectivity is available, false otherwise
 * */
// TODO check this for new android versions and find work around
fun Context.hasInternet(): Boolean =
    this.getService<ConnectivityManager>(Context.CONNECTIVITY_SERVICE)
        ?.activeNetworkInfo?.isConnected
        ?: false

/**
 * gets system service and casts it to the requested type else throws [ClassCastException]
 * */
inline fun <reified T> Context.getService(serviceName: String): T? =
    getSystemService(serviceName).let { service ->
        return when (service) {
            is T -> service
            else -> null
        }
    }