package com.john.wathermvvm.repository.cache.city

import com.john.wathermvvm.model.City

class CityDaoServiceImpl
constructor(
    private val cityDao: CityDao
): CityDaoService {

    override suspend fun insertCity(mainWeather: City): Long {
        return cityDao.insertCity(mainWeather)
    }

    override suspend fun getCities(): List<City> {
        return cityDao.getCities()
    }

    override suspend fun getCity(cityId: Long): City {
       return cityDao.getCity(cityId)
    }

    override suspend fun deleteCity(cityId: Long) {
        return cityDao.deleteCity(cityId)

    }

    override suspend fun clearALL() {
      return cityDao.clearALL()
    }

}