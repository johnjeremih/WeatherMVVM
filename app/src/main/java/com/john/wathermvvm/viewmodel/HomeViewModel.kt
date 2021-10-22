package com.john.wathermvvm.viewmodel

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.john.wathermvvm.model.City
import com.john.wathermvvm.repository.Repository
import com.john.wathermvvm.repository.network.NetworkDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private var repository: Repository
) : ViewModel() {

    private var address = MutableLiveData<Address?>()
    private val _networkState: MutableLiveData<NetworkDataState<List<City>>> =
        MutableLiveData()
    private val _deleteState: MutableLiveData<NetworkDataState<String>> =
        MutableLiveData()

    private fun getCityCurrentWeather(a: Address?) {
        address.postValue(a)
        viewModelScope.launch {
            try {

                repository.getCities(a?.latitude, a?.longitude)
                    .onEach {
                        _networkState.value = it
                    }
                    .launchIn(viewModelScope)


            } catch (e: Exception) {
                Log.e("HomeViewModel", "getCities: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }

        }

    }

    fun setCity(address: String?, geocoder: Geocoder) {
        viewModelScope.launch {
            try {
                getCityInfo(address, geocoder)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "get Weather: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }

        }
    }

    private fun getCityInfo(address: String?, geocoder: Geocoder) {
        var addressList: List<Address> = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                try {
                    if (address != null) {
                        addressList = geocoder.getFromLocationName(address, 1)
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                }

                try {
                    getCityCurrentWeather(addressList[0])
                } catch (e: Exception) {
                    Log.e("HomeFragment", e.message!!)

                }

            }
        }

    }

    fun getCurrentList() {
        getCityCurrentWeather(null)
    }

    fun deleteUser(userId: Long) {
        viewModelScope.launch {
            try {

                repository.deleteCity(userId)
                    .onEach {
                        _deleteState.value = it
                    }
                    .launchIn(viewModelScope)


            } catch (e: Exception) {
                Log.e("HomeViewModel", "getCities: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }

        }
    }

    val networkState: LiveData<NetworkDataState<List<City>>>
        get() = _networkState

    val deleteState: LiveData<NetworkDataState<String>>
        get() = _deleteState

}