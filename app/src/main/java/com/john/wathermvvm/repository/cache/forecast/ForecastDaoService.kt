package com.john.wathermvvm.repository.cache.forecast

import com.john.wathermvvm.model.City


interface ForecastDaoService {

    suspend fun insertForecast(forecast: City): Long

    suspend fun updateForecast(forecast: City)

    suspend fun getForecast(cityId: Long): List<City>

    suspend fun deleteForecast(cityId: Long)

    suspend fun clearAllForecast()
}