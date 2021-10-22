package com.john.wathermvvm.repository

import com.john.wathermvvm.model.City
import com.john.wathermvvm.repository.cache.forecast.CacheForecastDataSource
import com.john.wathermvvm.repository.cache.city.CacheCityDataSource
import com.john.wathermvvm.repository.network.NetworkDataState
import com.john.wathermvvm.repository.network.CityNetworkData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository
constructor(
    private val cacheCityDataSource: CacheCityDataSource,
    private val forecastDataSource: CacheForecastDataSource,
    private val networkDataSource: CityNetworkData,
) {
    private var latitude: Double =0.0
    private var longitude: Double =0.0

    suspend fun getCities(
        lat: Double?,
        lon: Double?
    ): Flow<NetworkDataState<List<City>>> = flow {
        emit(NetworkDataState.Loading)

        // If Latitude or Longitude are null get the data from database
        if (lat == null || lon == null) {
            val cachedWeather = cacheCityDataSource.getCities()
            emit(NetworkDataState.Success(cachedWeather))
        } else {
            latitude = lat
            longitude = lon
            val cityFromNetwork = networkDataSource.getCurrentWeather(lat, lon)
            cacheCityDataSource.insertCity(cityFromNetwork)
            val cachedList = cacheCityDataSource.getCities()
            emit(NetworkDataState.Success(cachedList))
        }

    }

    suspend fun getCity(cityId: Long, isRefreshing: Boolean): Flow<NetworkDataState<City>> = flow {
        emit(NetworkDataState.Loading)
        var cachedCity = cacheCityDataSource.getCity(cityId)

        //If isRefreshing is true pull from the network, otherwise pull from database
        if (isRefreshing){
            val cityFromNetwork = networkDataSource.getCurrentWeather(cachedCity.lat!!, cachedCity.lon!!)
            cacheCityDataSource.updateCity(cityFromNetwork)

            cachedCity = cacheCityDataSource.getCity(cityId)
            emit(NetworkDataState.Success(cachedCity))

        }else{
            cachedCity = cacheCityDataSource.getCity(cityId)
            emit(NetworkDataState.Success(cachedCity))
        }

    }
    suspend fun deleteCity(cityId: Long):  Flow<NetworkDataState<String>> = flow {
        emit(NetworkDataState.Loading)
        val cachedWeather = cacheCityDataSource.deleteCity(cityId)
        emit(NetworkDataState.Success(cachedWeather))
    }

    suspend fun clearAll() = flow {
        emit(NetworkDataState.Loading)
        val cachedWeather = cacheCityDataSource.clearALL()
        emit(NetworkDataState.Success(cachedWeather))
    }


    suspend fun getForecast(
        cityId: Long?,
        isRefreshing: Boolean
    ): Flow<NetworkDataState<List<City>>> = flow {
        emit(NetworkDataState.Loading)

        var cachedWeather = forecastDataSource.getForecast(cityId!!)

        // If the forecast is not cached in the database or isRefreshing is true pull from the network, otherwise pull from database
        if (cachedWeather.isEmpty()|| isRefreshing){
            val cachedCity = cacheCityDataSource.getCity(cityId)

            val networkForecast = networkDataSource.getForecast(cachedCity.lat!!, cachedCity.lon!!)

            forecastDataSource.insertForecast(networkForecast.data, cityId)
            cachedWeather = forecastDataSource.getForecast(cityId)
            emit(NetworkDataState.Success(cachedWeather))
        }else{

            emit(NetworkDataState.Success(cachedWeather))
        }
    }

}