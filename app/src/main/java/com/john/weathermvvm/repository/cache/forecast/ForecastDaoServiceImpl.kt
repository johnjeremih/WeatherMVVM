package com.john.weathermvvm.repository.cache.forecast

import com.john.weathermvvm.model.Forecast

class ForecastDaoServiceImpl
constructor(
    private val forecastDao: ForecastDao
): ForecastDaoService {

    override suspend fun insertForecast(forecast: Forecast): Long {
        return forecastDao.insertForecast(forecast)
    }

    override suspend fun updateForecast(forecast: Forecast) {
        return forecastDao.updateForecast(forecast)
    }

    override suspend fun getForecast(cityId: Long): List<Forecast> {
        return forecastDao.getForecast(cityId)
    }

    override suspend fun deleteForecast(cityId: Long) {
        forecastDao.deleteForecast(cityId)
    }

    override suspend fun clearAllForecast() {
        forecastDao.clearAllForecast()
    }



}