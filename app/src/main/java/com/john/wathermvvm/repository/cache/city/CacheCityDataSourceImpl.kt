package com.john.wathermvvm.repository.cache.city

import com.john.wathermvvm.model.City
import com.john.wathermvvm.model.WeatherDataResponse

class CacheCityDataSourceImpl
constructor(
    private val cityDaoService: CityDaoService
) : CacheCityDataSource {
    override suspend fun insertCity(weatherModel: WeatherDataResponse): Long {
        return cityDaoService.insertCity(weatherModel.data[0])
    }

    override suspend fun updateCity(city: City) {
        cityDaoService.updateCity(city)
    }

    override suspend fun insertList(weatherList: List<City>) {
        for (weather in weatherList) {
            cityDaoService.insertCity(weather)
        }    }

    override suspend fun getCities(): List<City> {
        return cityDaoService.getCities()
    }

    override suspend fun getCity(cityId: Long): City {
        return cityDaoService.getCity(cityId)
    }

    override suspend fun deleteCity(cityId: Long): String {
        return cityDaoService.deleteCity(cityId).toString()
    }

    override suspend fun clearALL() {
       return cityDaoService.clearALL()
    }


}
