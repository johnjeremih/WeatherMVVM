package com.john.weathermvvm.repository.network

import com.john.weathermvvm.model.City
import com.john.weathermvvm.model.DataResponse
import com.john.weathermvvm.model.Forecast
import com.john.weathermvvm.repository.service.WeatherService


class NetworkDataImpl(var weatherService: WeatherService): NetworkData {

    override suspend fun getCurrentWeather(lat: Double, lon: Double): DataResponse<City> {
        return weatherService.getCurrentWeather(lat, lon,"imperial")
    }

    override suspend fun getForecast(lat: Double, lon: Double): DataResponse<Forecast> {
        return weatherService.getForecast(lat, lon,"imperial")
    }


}