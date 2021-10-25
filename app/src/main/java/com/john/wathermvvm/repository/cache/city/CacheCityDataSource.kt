package com.john.wathermvvm.repository.cache.city

import com.john.wathermvvm.model.City
import com.john.wathermvvm.model.WeatherDataResponse

interface CacheCityDataSource {

    suspend fun insertCity(weatherModel: WeatherDataResponse): Long

    suspend fun updateCity(city: City)

    suspend fun insertList(weatherList: List<City>)

    suspend fun getCities(): List<City>

    suspend fun getCity(cityId: Long): City

    suspend fun deleteCity(cityId: Long): String

    suspend fun clearALL()

}