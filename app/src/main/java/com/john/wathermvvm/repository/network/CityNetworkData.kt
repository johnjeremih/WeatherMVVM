package com.john.wathermvvm.repository.network

import com.john.wathermvvm.model.WeatherDataResponse

interface CityNetworkData {

    suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherDataResponse
    suspend fun getForecast(lat: Double, lon: Double): WeatherDataResponse

}