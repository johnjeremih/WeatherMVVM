package com.john.wathermvvm.data.repositories.city
import com.john.wathermvvm.data.remote.NetworkDataState
import com.john.wathermvvm.model.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {

  fun getCities(lat: Double?, lon: Double?): Flow<NetworkDataState<List<City>>>

  fun getCity(cityId: Long, isRefreshing: Boolean): Flow<NetworkDataState<City>>

  fun deleteCity(cityId: Long): Flow<NetworkDataState<Boolean>>
}
