package com.john.weathermvvm.repository

import com.john.weathermvvm.model.City
import com.john.weathermvvm.model.Forecast
import com.john.weathermvvm.repository.cache.DataSource
import com.john.weathermvvm.repository.network.NetworkData
import com.john.weathermvvm.repository.network.NetworkDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class Repository
constructor(
    private val dataSource: DataSource,
    private val networkDataSource: NetworkData
) {

    fun getCities(
        lat: Double?,
        lon: Double?
    ): Flow<NetworkDataState<List<City>>> = flow {
        emit(NetworkDataState.Loading)

        // If Latitude or Longitude are null get the data from database
        if (lat == null || lon == null) {
            val cachedWeather = dataSource.getCities()
            emit(NetworkDataState.Success(cachedWeather))
        } else {

            try {

                val cityFromNetwork = networkDataSource.getCurrentWeather(lat, lon)
                dataSource.insertCity(cityFromNetwork.data[0])
                val cachedList = dataSource.getCities()
                emit(NetworkDataState.Success(cachedList))

            } catch (e: HttpException) {
                emit(NetworkDataState.Error(e))
            } catch (e: IOException) {
                emit(NetworkDataState.Error(e))
            } catch (e: UnknownHostException) {
                emit(NetworkDataState.Error(e))

            }

        }

    }

    fun getCity(cityId: Long, isRefreshing: Boolean): Flow<NetworkDataState<City>> = flow {
        emit(NetworkDataState.Loading)
        var cachedCity = dataSource.getCity(cityId)

        //If isRefreshing is true pull from the network, otherwise pull from database
        if (isRefreshing) {

            try {

                val cityFromNetwork =
                    networkDataSource.getCurrentWeather(cachedCity.lat!!, cachedCity.lon!!)
                dataSource.updateCity(cityFromNetwork.data[0], cachedCity, cachedCity.id)

                cachedCity = dataSource.getCity(cityId)
                emit(NetworkDataState.Success(cachedCity))

            } catch (e: HttpException) {
                emit(NetworkDataState.Error(e))
            } catch (e: IOException) {
                emit(NetworkDataState.Error(e))
            } catch (e: UnknownHostException) {
                emit(NetworkDataState.Error(e))
            }


        } else {
            cachedCity = dataSource.getCity(cityId)
            emit(NetworkDataState.Success(cachedCity))
        }

    }

    suspend fun deleteCity(cityId: Long): Flow<NetworkDataState<Boolean>> = flow {
        emit(NetworkDataState.Loading)
        dataSource.deleteCity(cityId)
        dataSource.deleteForecast(cityId)
        emit(NetworkDataState.Success(true))
    }

    fun getForecast(
        cityId: Long?,
        isRefreshing: Boolean
    ): Flow<NetworkDataState<List<Forecast>>> = flow {
        emit(NetworkDataState.Loading)

        var cachedWeather = dataSource.getForecast(cityId!!)
        val cachedCity = dataSource.getCity(cityId)
        // If the forecast is not cached in the database or isRefreshing is true pull from the network, otherwise pull from database
        when {
            cachedWeather.isEmpty() -> {
                val networkForecast =
                    networkDataSource.getForecast(cachedCity.lat!!, cachedCity.lon!!)
                dataSource.insertForecast(networkForecast.data, cityId)
                cachedWeather = dataSource.getForecast(cityId)
                emit(NetworkDataState.Success(cachedWeather))
            }
            isRefreshing -> {
                try {

                    val networkForecast =
                        networkDataSource.getForecast(cachedCity.lat!!, cachedCity.lon!!)

                    dataSource.updateForecast(networkForecast.data, cachedWeather, cityId)

                    val cityFromNetwork =
                        networkDataSource.getCurrentWeather(cachedCity.lat!!, cachedCity.lon!!)

                    dataSource.updateCity(cityFromNetwork.data[0], cachedCity, cachedCity.id)
                    cachedWeather = dataSource.getForecast(cityId)
                    emit(NetworkDataState.Success(cachedWeather))

                } catch (e: HttpException) {
                    emit(NetworkDataState.Error(e))
                } catch (e: IOException) {
                    emit(NetworkDataState.Error(e))
                } catch (e: UnknownHostException) {
                    emit(NetworkDataState.Error(e))

                }


            }
            else -> {

                emit(NetworkDataState.Success(cachedWeather))

            }
        }
    }
}
