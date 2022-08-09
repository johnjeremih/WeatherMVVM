package com.john.wathermvvm.data.repositories.forecast

import com.john.wathermvvm.data.local.datasource.city.LocalCityDataSource
import com.john.wathermvvm.data.local.datasource.forecast.LocalForecastDataSource
import com.john.wathermvvm.data.remote.NetworkDataState
import com.john.wathermvvm.data.remote.RemoteDataSource
import com.john.wathermvvm.model.Forecast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class ForecastRepositoryImpl(
    private val localCityDataSource: LocalCityDataSource,
    private val localForecastDataSource: LocalForecastDataSource,
    private val remoteDataSourceSource: RemoteDataSource
) : ForecastRepository {
    override fun getForecast(
        cityId: Long?,
        isRefreshing: Boolean
    ): Flow<NetworkDataState<List<Forecast>>> = flow {
        emit(NetworkDataState.Loading)

        var cachedWeather = localForecastDataSource.getForecast(cityId!!)
        val cachedCity = localCityDataSource.getCity(cityId)
        // If the forecast is not cached in the database or isRefreshing is true pull from the
        // network, otherwise pull from database
        when {
            cachedWeather.isEmpty() -> {
                val networkForecast = remoteDataSourceSource.getForecast(cachedCity.lat!!, cachedCity.lon!!)
                localForecastDataSource.insertForecast(networkForecast.data, cityId)
                cachedWeather = localForecastDataSource.getForecast(cityId)
                emit(NetworkDataState.Success(cachedWeather))
            }
            isRefreshing -> {
                try {

                    val networkForecast = remoteDataSourceSource.getForecast(cachedCity.lat!!, cachedCity.lon!!)

                    localForecastDataSource.updateForecast(networkForecast.data, cachedWeather, cityId)

                    val cityFromNetwork =
                        remoteDataSourceSource.getCurrentWeather(cachedCity.lat!!, cachedCity.lon!!)

                    localCityDataSource.updateCity(cityFromNetwork.data[0], cachedCity, cachedCity.id)
                    cachedWeather = localForecastDataSource.getForecast(cityId)
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
