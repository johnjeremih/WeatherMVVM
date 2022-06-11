package com.john.wathermvvm.data.repositories.city

import com.john.wathermvvm.model.City
import com.john.wathermvvm.data.local.datasource.city.LocalCityDataSource
import com.john.wathermvvm.data.local.datasource.forecast.LocalForecastDataSource
import com.john.wathermvvm.data.remote.RemoteDataSource
import com.john.wathermvvm.data.remote.NetworkDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class CityRepositoryImpl
constructor(
    private val localCityDataSource: LocalCityDataSource,
    private val localForecastDataSource: LocalForecastDataSource,
    private val remoteDataSourceSource: RemoteDataSource
) : CityRepository {

  override fun getCities(lat: Double?, lon: Double?): Flow<NetworkDataState<List<City>>> = flow {
    emit(NetworkDataState.Loading)

    // If Latitude or Longitude are null get the data from database
    if (lat == null || lon == null) {
      val cachedWeather = localCityDataSource.getCities()
      emit(NetworkDataState.Success(cachedWeather))
    } else {

      try {
        val cityFromNetwork = remoteDataSourceSource.getCurrentWeather(lat, lon)
        localCityDataSource.insertCity(cityFromNetwork.data[0])
        val cachedList = localCityDataSource.getCities()
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

  override fun getCity(cityId: Long, isRefreshing: Boolean): Flow<NetworkDataState<City>> = flow {
    emit(NetworkDataState.Loading)
    var cachedCity = localCityDataSource.getCity(cityId)

    // If isRefreshing is true pull from the network, otherwise pull from database
    if (isRefreshing) {

      try {

        val cityFromNetwork =
            remoteDataSourceSource.getCurrentWeather(cachedCity.lat!!, cachedCity.lon!!)
        localCityDataSource.updateCity(cityFromNetwork.data[0], cachedCity, cachedCity.id)

        cachedCity = localCityDataSource.getCity(cityId)
        emit(NetworkDataState.Success(cachedCity))
      } catch (e: HttpException) {
        emit(NetworkDataState.Error(e))
      } catch (e: IOException) {
        emit(NetworkDataState.Error(e))
      } catch (e: UnknownHostException) {
        emit(NetworkDataState.Error(e))
      }
    } else {
      cachedCity = localCityDataSource.getCity(cityId)
      emit(NetworkDataState.Success(cachedCity))
    }
  }

  override fun deleteCity(cityId: Long): Flow<NetworkDataState<Boolean>> = flow {
    emit(NetworkDataState.Loading)
    localCityDataSource.deleteCity(cityId)
    localForecastDataSource.deleteForecast(cityId)
    emit(NetworkDataState.Success(true))
  }
}
