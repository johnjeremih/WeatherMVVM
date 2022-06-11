package com.john.wathermvvm.data.remote

import com.john.wathermvvm.model.City
import com.john.wathermvvm.model.DataResponse
import com.john.wathermvvm.model.Forecast
import com.john.wathermvvm.data.service.WeatherService

class RemoteDataSourceImpl(var weatherService: WeatherService) : RemoteDataSource {

  override suspend fun getCurrentWeather(lat: Double, lon: Double): DataResponse<City> {
    return weatherService.getCurrentWeather(lat, lon, "imperial")
  }

  override suspend fun getForecast(lat: Double, lon: Double): DataResponse<Forecast> {
    return weatherService.getForecast(lat, lon, "imperial")
  }
}
