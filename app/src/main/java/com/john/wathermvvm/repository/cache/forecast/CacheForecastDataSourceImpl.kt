package com.john.wathermvvm.repository.cache.forecast

import com.john.wathermvvm.model.City
import com.john.wathermvvm.repository.mapper.ForecastMapper

class CacheForecastDataSourceImpl
constructor(
    private val forecastDaoService: ForecastDaoService,
    private val cacheMapper: ForecastMapper
) : CacheForecastDataSource {
    override suspend fun insertForecast(forecastList: List<City>, cityId: Long) {

        for (forecast in forecastList.take(5)) {

            forecastDaoService.insertForecast(cacheMapper.buildModel(forecast, null,cityId))

        }
    }

    override suspend fun updateForecast(forecastList: List<City>,forecastCacheList: List<City>, cityId: Long) {
        for ((positionCounter, forecast) in forecastList.take(5).withIndex()) {

            forecastDaoService.updateForecast(cacheMapper.buildModel(forecast, forecastCacheList[positionCounter],cityId))

        }    }


    override suspend fun getForecast(cityId: Long): List<City> {
        return forecastDaoService.getForecast(cityId)
    }

    override suspend fun deleteCity(cityId: Long) {
        return forecastDaoService.deleteForecast(cityId)
    }

    override suspend fun clearALL() {
        return forecastDaoService.clearAllForecast()
    }

}
