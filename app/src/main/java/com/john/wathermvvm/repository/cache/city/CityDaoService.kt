package com.john.wathermvvm.repository.cache.city

import com.john.wathermvvm.model.City


interface CityDaoService {

    suspend fun insertCity(mainWeather: City): Long

    suspend fun getCities(): List<City>

    suspend fun getCity(cityId: Long): City

    suspend fun deleteCity(cityId: Long)

    suspend fun clearALL()
}