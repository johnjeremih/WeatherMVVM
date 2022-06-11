package com.john.wathermvvm.repository.cache

import com.john.wathermvvm.model.Forecast
import com.john.wathermvvm.repository.cache.forecast.ForecastDaoService
import com.john.wathermvvm.repository.mapper.ForecastMapper
import com.john.wathermvvm.util.takeFirstFive

class ForecastDataSourceImpl
constructor(
    private val forecastDaoService: ForecastDaoService,
    private val forecastMapper: ForecastMapper,
) : ForecastDataSource {

  override suspend fun insertForecast(forecastList: List<Forecast>, cityId: Long) {
    for (forecast in forecastList.takeFirstFive) {
      forecastDaoService.insertForecast(forecastMapper.buildModel(forecast, null, cityId))
    }
  }

  override suspend fun updateForecast(
    forecastList: List<Forecast>,
    forecastCacheList: List<Forecast>,
    cityId: Long
  ) {
    val firstFive = 5

    forecastList.take(firstFive).withIndex().forEach { (positionCounter, forecast) ->
      forecastDaoService.updateForecast(
        forecastMapper.buildModel(forecast, forecastCacheList[positionCounter], cityId)
      )
    }
  }

  override suspend fun getForecast(cityId: Long): List<Forecast> {
    return forecastDaoService.getForecast(cityId)
  }

  override suspend fun deleteForecast(cityId: Long) {
    forecastDaoService.deleteForecast(cityId)
  }

  override suspend fun clearAllForecast() {
    forecastDaoService.clearAllForecast()
  }
}
