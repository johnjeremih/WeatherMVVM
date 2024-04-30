package com.john.weathermvvm.repository.cache

import com.john.weathermvvm.model.City
import com.john.weathermvvm.model.Forecast
import com.john.weathermvvm.repository.cache.city.CityDaoService
import com.john.weathermvvm.repository.cache.forecast.ForecastDaoService
import com.john.weathermvvm.repository.mapper.ForecastMapper
import com.john.weathermvvm.repository.mapper.CityMapper

class DataSourceImpl
constructor(
    private val cityDaoService: CityDaoService,
    private val forecastDaoService: ForecastDaoService,
    private val forecastMapper: ForecastMapper,
    private val cityMapper: CityMapper
) : DataSource {

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

    override suspend fun insertForecast(forecastList: List<Forecast>, cityId: Long) {

        for (forecast in forecastList.take(5)) {

            forecastDaoService.insertForecast(forecastMapper.buildModel(forecast, null, cityId))

        }
    }

    override suspend fun updateForecast(
        forecastList: List<Forecast>,
        forecastCacheList: List<Forecast>,
        cityId: Long
    ) {

        for ((positionCounter, forecast) in forecastList.take(5).withIndex()) {

            forecastDaoService.updateForecast(
                forecastMapper.buildModel(
                    forecast,
                    forecastCacheList[positionCounter],
                    cityId
                )
            )

        }
    }

    override suspend fun getForecast(cityId: Long): List<Forecast> {
        return forecastDaoService.getForecast(cityId)
    }

    override suspend fun deleteForecast(cityId: Long) {
        forecastDaoService.deleteForecast(cityId)
    }

    override suspend fun clearAllForecast() {
        forecastDaoService.clearAllForecast()
    }


}
