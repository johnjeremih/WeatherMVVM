package com.john.wathermvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.john.wathermvvm.model.City
import com.john.wathermvvm.model.Forecast
import com.john.wathermvvm.repository.Repository
import com.john.wathermvvm.repository.network.NetworkDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private var repository: Repository
) : ViewModel() {
    private val _cityNetworkState: MutableLiveData<NetworkDataState<City>> = MutableLiveData()
    private val _forecastNetworkState: MutableLiveData<NetworkDataState<List<Forecast>>> = MutableLiveData()

    fun getCity(cityId: Long?,isRefreshing: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                if (cityId != null) {
                    repository.getCity(cityId = cityId,isRefreshing)
                        .onEach {
                            _cityNetworkState.value = it
                        }
                        .launchIn(viewModelScope)

                    repository.getForecast(cityId, isRefreshing)
                        .onEach {
                            _forecastNetworkState.value = it
                        }

                        .launchIn(viewModelScope)
                }

            } catch (e: Exception) {
                Log.e("DetailViewModel", "getCity: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }

        }


    }


    val cityNetworkState: LiveData<NetworkDataState<City>>
        get() = _cityNetworkState

    val forecastNetworkState: LiveData<NetworkDataState<List<Forecast>>>
        get() = _forecastNetworkState

}
