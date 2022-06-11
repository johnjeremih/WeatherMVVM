package com.john.wathermvvm.data.remote

sealed class NetworkDataState<out R> {
  data class Success<out T>(val data: T) : NetworkDataState<T>()
  data class Error(val exception: Exception) : NetworkDataState<Nothing>()
  object Loading : NetworkDataState<Nothing>()
}
