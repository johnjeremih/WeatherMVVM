package com.john.wathermvvm.repository.cache.forecast

import com.john.wathermvvm.model.City

interface CacheForecastDataSource {

    suspend fun insertForecast(forecastList: List<City>, cityId: Long)

    suspend fun updateForecast(forecastList: List<City>,forecastCacheList: List<City>, cityId: Long)

    suspend fun getForecast(cityId: Long): List<City>

    suspend fun deleteCity(cityId: Long)

    suspend fun clearALL()


}