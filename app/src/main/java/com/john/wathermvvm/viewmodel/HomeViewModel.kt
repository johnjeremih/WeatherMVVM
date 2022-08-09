package com.john.wathermvvm.viewmodel

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.john.wathermvvm.data.remote.NetworkDataState
import com.john.wathermvvm.data.repositories.city.CityRepository
import com.john.wathermvvm.model.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private var cityRepository: CityRepository) : ViewModel() {

    private var address = MutableLiveData<Address?>()
    private val _networkState: MutableLiveData<NetworkDataState<List<City>>> = MutableLiveData()
    private val _deleteState: MutableLiveData<NetworkDataState<Boolean>> = MutableLiveData()

    private fun getCityCurrentWeather(a: Address?) {
        address.postValue(a)
        CoroutineScope(Dispatchers.IO).launch {
            try {

                cityRepository
                    .getCities(a?.latitude, a?.longitude)
                    .onEach { _networkState.value = it }
                    .launchIn(viewModelScope)
            } catch (e: IOException) {
                Log.e("HomeViewModel", "getCities: Exception: $e, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    fun setCity(address: String?, geocoder: Geocoder) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                getCityInfo(address, geocoder)
            } catch (e: IOException) {
                Log.e("HomeViewModel", "get Weather: Exception: $e, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private fun getCityInfo(address: String?, geocoder: Geocoder) {
        var addressList: List<Address> = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (address != null) {
                    addressList = getAddress(geocoder, address)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                getCityCurrentWeather(addressList[0])
            } catch (e: IOException) {
                Log.e("HomeFragment", e.message!!)
            }
        }
    }

    private fun getAddress(geocoder: Geocoder, address: String): List<Address> {
        return geocoder.getFromLocationName(address, 1)
    }

    fun getCurrentList() {
        getCityCurrentWeather(null)
        _deleteState.value = NetworkDataState.Success(false)
    }

    fun deleteUser(userId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                cityRepository
                    .deleteCity(userId)
                    .onEach { _deleteState.value = it }
                    .launchIn(viewModelScope)
            } catch (e: IOException) {
                Log.e("HomeViewModel", "getCities: Exception: $e, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    val networkState: LiveData<NetworkDataState<List<City>>>
        get() = _networkState

    val deleteState: LiveData<NetworkDataState<Boolean>>
        get() = _deleteState
}
