package com.john.wathermvvm.model

import com.google.gson.annotations.SerializedName

data class DataResponse<T>(@SerializedName("data") val data: ArrayList<T>)
