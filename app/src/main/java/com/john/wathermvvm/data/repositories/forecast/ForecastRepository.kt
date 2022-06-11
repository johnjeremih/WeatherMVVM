package com.john.wathermvvm.data.repositories.forecast

import com.john.wathermvvm.model.Forecast
import com.john.wathermvvm.data.remote.NetworkDataState
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {

  fun getForecast(cityId: Long?, isRefreshing: Boolean): Flow<NetworkDataState<List<Forecast>>>
}