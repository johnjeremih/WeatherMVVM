package com.john.wathermvvm.data.local.datasource.forecast

import com.john.wathermvvm.model.Forecast

interface LocalForecastDataSource {

  // Forecast

  suspend fun insertForecast(forecastList: List<Forecast>, cityId: Long)

  suspend fun updateForecast(
      forecastList: List<Forecast>,
      forecastCacheList: List<Forecast>,
      cityId: Long
  )

  suspend fun getForecast(cityId: Long): List<Forecast>

  suspend fun deleteForecast(cityId: Long)

  suspend fun clearAllForecast()
}
