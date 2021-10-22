package com.john.wathermvvm.repository.cache.forecast

import com.john.wathermvvm.model.City

class ForecastDaoServiceImpl
constructor(
    private val forecastDao: ForecastDao
): ForecastDaoService {
    override suspend fun insertForecast(forecast: City): Long {
       return forecastDao.insertForecast(forecast)
    }

    override suspend fun getForecast(cityId: Long): List<City> {
      return forecastDao.getForecast(cityId)
    }

    override suspend fun deleteForecast(cityId: Long) {
        return deleteForecast(cityId)
    }

    override suspend fun clearAllForecast() {
       return forecastDao.clearAllForecast()
    }


}