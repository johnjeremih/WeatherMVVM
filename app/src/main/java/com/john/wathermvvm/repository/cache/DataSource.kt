package com.john.wathermvvm.repository.cache

import com.john.wathermvvm.model.City
import com.john.wathermvvm.model.Forecast

interface DataSource {

    //City

    suspend fun insertCity(city: City): Long

    suspend fun updateCity(city: City, cachedCity: City, cityId: Long?)

    suspend fun getCities(): List<City>

    suspend fun getCity(cityId: Long): City

    suspend fun deleteCity(cityId: Long)

    suspend fun clearALL()

    //Forecast
    suspend fun insertForecast(forecastList: List<Forecast>, cityId: Long)

    suspend fun updateForecast(forecastList: List<Forecast>,forecastCacheList: List<Forecast>, cityId: Long)

    suspend fun getForecast(cityId: Long): List<Forecast>

    suspend fun deleteForecast(cityId: Long)

    suspend fun clearAllForecast()

}