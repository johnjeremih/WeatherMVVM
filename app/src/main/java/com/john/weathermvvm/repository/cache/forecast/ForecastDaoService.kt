package com.john.weathermvvm.repository.cache.forecast

import com.john.weathermvvm.model.Forecast


interface ForecastDaoService {

    suspend fun insertForecast(forecast: Forecast): Long

    suspend fun updateForecast(forecast: Forecast)

    suspend fun getForecast(cityId: Long): List<Forecast>

    suspend fun deleteForecast(cityId: Long)

    suspend fun clearAllForecast()
}