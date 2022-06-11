package com.john.wathermvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.john.wathermvvm.model.City
import com.john.wathermvvm.model.Forecast
import com.john.wathermvvm.data.remote.NetworkDataState
import com.john.wathermvvm.data.repositories.city.CityRepository
import com.john.wathermvvm.data.repositories.forecast.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private var cityRepository: CityRepository,
    private var forecastRepository: ForecastRepository
) : ViewModel() {
  private val _cityNetworkState: MutableLiveData<NetworkDataState<City>> = MutableLiveData()
  private val _forecastNetworkState: MutableLiveData<NetworkDataState<List<Forecast>>> =
      MutableLiveData()

  fun getCity(cityId: Long?, isRefreshing: Boolean) {
    CoroutineScope(Dispatchers.IO).launch {
      try {

        if (cityId != null) {
          cityRepository
              .getCity(cityId = cityId, isRefreshing)
              .onEach { _cityNetworkState.value = it }
              .launchIn(viewModelScope)

          forecastRepository
              .getForecast(cityId, isRefreshing)
              .onEach { _forecastNetworkState.value = it }
              .launchIn(viewModelScope)
        }
      } catch (e: IOException) {
        Log.e("DetailViewModel", "getCity: Exception: $e, ${e.cause}")
        e.printStackTrace()
      }
    }
  }

  val cityNetworkState: LiveData<NetworkDataState<City>>
    get() = _cityNetworkState

  val forecastNetworkState: LiveData<NetworkDataState<List<Forecast>>>
    get() = _forecastNetworkState
}
