package com.john.wathermvvm.repository.usecases.city

import com.john.wathermvvm.model.City
import com.john.wathermvvm.repository.network.NetworkDataState
import kotlinx.coroutines.flow.Flow

interface CityRepository {

  fun getCities(lat: Double?, lon: Double?): Flow<NetworkDataState<List<City>>>

  fun getCity(cityId: Long, isRefreshing: Boolean): Flow<NetworkDataState<City>>

  fun deleteCity(cityId: Long): Flow<NetworkDataState<Boolean>>
}
