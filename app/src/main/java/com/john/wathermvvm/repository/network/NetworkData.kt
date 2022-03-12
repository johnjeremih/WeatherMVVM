package com.john.wathermvvm.repository.network

import com.john.wathermvvm.model.City
import com.john.wathermvvm.model.DataResponse
import com.john.wathermvvm.model.Forecast


interface NetworkData {

    suspend fun getCurrentWeather(lat: Double, lon: Double): DataResponse<City>
    suspend fun getForecast(lat: Double, lon: Double): DataResponse<Forecast>

}