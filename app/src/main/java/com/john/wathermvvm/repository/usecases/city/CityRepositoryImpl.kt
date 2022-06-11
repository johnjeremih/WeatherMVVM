package com.john.wathermvvm.repository.usecases.city

import com.john.wathermvvm.model.City
import com.john.wathermvvm.repository.cache.CityDataSource
import com.john.wathermvvm.repository.cache.ForecastDataSource
import com.john.wathermvvm.repository.network.NetworkData
import com.john.wathermvvm.repository.network.NetworkDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class CityRepositoryImpl
constructor(
    private val cityDataSource: CityDataSource,
    private val forecastDataSource: ForecastDataSource,
    private val networkDataSource: NetworkData
) : CityRepository {

  override fun getCities(lat: Double?, lon: Double?): Flow<NetworkDataState<List<City>>> = flow {
    emit(NetworkDataState.Loading)

    // If Latitude or Longitude are null get the data from database
    if (lat == null || lon == null) {
      val cachedWeather = cityDataSource.getCities()
      emit(NetworkDataState.Success(cachedWeather))
    } else {

      try {
        val cityFromNetwork = networkDataSource.getCurrentWeather(lat, lon)
        cityDataSource.insertCity(cityFromNetwork.data[0])
        val cachedList = cityDataSource.getCities()
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
    var cachedCity = cityDataSource.getCity(cityId)

    // If isRefreshing is true pull from the network, otherwise pull from database
    if (isRefreshing) {

      try {

        val cityFromNetwork =
            networkDataSource.getCurrentWeather(cachedCity.lat!!, cachedCity.lon!!)
        cityDataSource.updateCity(cityFromNetwork.data[0], cachedCity, cachedCity.id)

        cachedCity = cityDataSource.getCity(cityId)
        emit(NetworkDataState.Success(cachedCity))
      } catch (e: HttpException) {
        emit(NetworkDataState.Error(e))
      } catch (e: IOException) {
        emit(NetworkDataState.Error(e))
      } catch (e: UnknownHostException) {
        emit(NetworkDataState.Error(e))
      }
    } else {
      cachedCity = cityDataSource.getCity(cityId)
      emit(NetworkDataState.Success(cachedCity))
    }
  }

  override fun deleteCity(cityId: Long): Flow<NetworkDataState<Boolean>> = flow {
    emit(NetworkDataState.Loading)
    cityDataSource.deleteCity(cityId)
    forecastDataSource.deleteForecast(cityId)
    emit(NetworkDataState.Success(true))
  }
}
