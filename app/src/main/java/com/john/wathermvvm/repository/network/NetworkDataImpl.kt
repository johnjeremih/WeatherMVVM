package com.john.wathermvvm.repository.network

import com.john.wathermvvm.model.City
import com.john.wathermvvm.model.DataResponse
import com.john.wathermvvm.model.Forecast
import com.john.wathermvvm.repository.service.WeatherService

class NetworkDataImpl(var weatherService: WeatherService) : NetworkData {

  override suspend fun getCurrentWeather(lat: Double, lon: Double): DataResponse<City> {
    return weatherService.getCurrentWeather(lat, lon, "imperial")
  }

  override suspend fun getForecast(lat: Double, lon: Double): DataResponse<Forecast> {
    return weatherService.getForecast(lat, lon, "imperial")
  }
}
