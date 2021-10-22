package com.john.wathermvvm.repository.network

import com.john.wathermvvm.model.WeatherDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {


    @GET("current")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") lang: String
    ): WeatherDataResponse

    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") lang: String
    ): WeatherDataResponse
}

