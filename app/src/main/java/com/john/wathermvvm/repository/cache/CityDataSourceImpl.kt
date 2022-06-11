package com.john.wathermvvm.repository.cache

import com.john.wathermvvm.model.City
import com.john.wathermvvm.repository.cache.city.CityDaoService
import com.john.wathermvvm.repository.mapper.CityMapper

class CityDataSourceImpl
constructor(private val cityDaoService: CityDaoService, private val cityMapper: CityMapper) :
    CityDataSource {

  override suspend fun insertCity(city: City): Long {
    return cityDaoService.insertCity(city)
  }

  override suspend fun updateCity(city: City, cachedCity: City, cityId: Long?) {
    cityDaoService.updateCity(cityMapper.buildModel(city, cachedCity, cityId))
  }

  override suspend fun getCities(): List<City> {
    return cityDaoService.getCities()
  }

  override suspend fun getCity(cityId: Long): City {
    return cityDaoService.getCity(cityId)
  }

  override suspend fun deleteCity(cityId: Long) {
    cityDaoService.deleteCity(cityId).toString()
  }

  override suspend fun clearALL() {
    return cityDaoService.clearALL()
  }
}
