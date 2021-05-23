package com.git.trending.util

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast


/**Created by Shiv Jalkote on 10-May-2021. **/

object AndroidUtil {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }

    fun showToastMessage(context: Context, message: String, duration: Int) {
        Toast.makeText(context, message, duration).show()
    }

}