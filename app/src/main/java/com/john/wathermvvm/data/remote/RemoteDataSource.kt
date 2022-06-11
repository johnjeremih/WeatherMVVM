package com.john.wathermvvm.data.remote

import com.john.wathermvvm.model.City
import com.john.wathermvvm.model.DataResponse
import com.john.wathermvvm.model.Forecast

interface RemoteDataSource {

  suspend fun getCurrentWeather(lat: Double, lon: Double): DataResponse<City>
  suspend fun getForecast(lat: Double, lon: Double): DataResponse<Forecast>
}
