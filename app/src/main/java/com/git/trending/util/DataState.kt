package com.git.trending.util


/**Created by Shiv Jalkote on 08-May-2021. **/

sealed class DataState<out R> {
    data class Success<out T>(val data: T?) : DataState<T>()
    data class Error(val exception: Exception, val message: String?) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}