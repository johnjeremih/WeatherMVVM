package com.john.wathermvvm.data.local.datasource.city

import com.john.wathermvvm.model.City

interface LocalCityDataSource {

    // City

    suspend fun insertCity(city: City): Long

    suspend fun updateCity(city: City, cachedCity: City, cityId: Long?)

    suspend fun getCities(): List<City>

    suspend fun getCity(cityId: Long): City

    suspend fun deleteCity(cityId: Long)

    suspend fun clearALL()
}