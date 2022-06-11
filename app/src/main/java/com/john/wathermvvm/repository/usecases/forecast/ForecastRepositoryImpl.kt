package com.john.wathermvvm.repository.usecases.forecast

import com.john.wathermvvm.model.Forecast
import com.john.wathermvvm.repository.cache.CityDataSource
import com.john.wathermvvm.repository.cache.ForecastDataSource
import com.john.wathermvvm.repository.network.NetworkData
import com.john.wathermvvm.repository.network.NetworkDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class ForecastRepositoryImpl(
    private val cityDataSource: CityDataSource,
    private val forecastDataSource: ForecastDataSource,
    private val networkDataSource: NetworkData
) : ForecastRepository {
  override fun getForecast(
      cityId: Long?,
      isRefreshing: Boolean
  ): Flow<NetworkDataState<List<Forecast>>> = flow {
    emit(NetworkDataState.Loading)

    var cachedWeather = forecastDataSource.getForecast(cityId!!)
    val cachedCity = cityDataSource.getCity(cityId)
    // If the forecast is not cached in the database or isRefreshing is true pull from the
    // network, otherwise pull from database
    when {
      cachedWeather.isEmpty() -> {
        val networkForecast = networkDataSource.getForecast(cachedCity.lat!!, cachedCity.lon!!)
        forecastDataSource.insertForecast(networkForecast.data, cityId)
        cachedWeather = forecastDataSource.getForecast(cityId)
        emit(NetworkDataState.Success(cachedWeather))
      }
      isRefreshing -> {
        try {

          val networkForecast = networkDataSource.getForecast(cachedCity.lat!!, cachedCity.lon!!)

          forecastDataSource.updateForecast(networkForecast.data, cachedWeather, cityId)

          val cityFromNetwork =
              networkDataSource.getCurrentWeather(cachedCity.lat!!, cachedCity.lon!!)

          cityDataSource.updateCity(cityFromNetwork.data[0], cachedCity, cachedCity.id)
          cachedWeather = forecastDataSource.getForecast(cityId)
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
