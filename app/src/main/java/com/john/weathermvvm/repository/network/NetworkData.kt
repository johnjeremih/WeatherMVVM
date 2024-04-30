package com.john.weathermvvm.repository.network

import com.john.weathermvvm.model.City
import com.john.weathermvvm.model.DataResponse
import com.john.weathermvvm.model.Forecast


interface NetworkData {

    suspend fun getCurrentWeather(lat: Double, lon: Double): DataResponse<City>
    suspend fun getForecast(lat: Double, lon: Double): DataResponse<Forecast>

}