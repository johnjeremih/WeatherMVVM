package com.john.wathermvvm.repository.network

import com.john.wathermvvm.model.WeatherDataResponse

class CityNetworkDataImpl(var weatherService: WeatherService): CityNetworkData {
    override suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherDataResponse {
        return weatherService.getCurrentWeather(lat, lon,"imperial")
    }

    override suspend fun getForecast(lat: Double, lon: Double): WeatherDataResponse {
        return weatherService.getForecast(lat, lon,"imperial")
    }


}