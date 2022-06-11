package com.john.wathermvvm.repository.usecases.forecast

import com.john.wathermvvm.model.Forecast
import com.john.wathermvvm.repository.network.NetworkDataState
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {

  fun getForecast(cityId: Long?, isRefreshing: Boolean): Flow<NetworkDataState<List<Forecast>>>
}
