package com.john.weathermvvm.repository.cache.city

import com.john.weathermvvm.model.City


interface CityDaoService {

    //City

    suspend fun insertCity(city: City): Long

    suspend fun updateCity(city: City)

    suspend fun getCities(): List<City>

    suspend fun getCity(cityId: Long): City

    suspend fun deleteCity(cityId: Long)

    suspend fun clearALL()

}