package com.john.weathermvvm.repository.service

import com.john.weathermvvm.model.City
import com.john.weathermvvm.model.DataResponse
import com.john.weathermvvm.model.Forecast
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {


    @GET("current")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") lang: String
    ): DataResponse<City>

    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") lang: String
    ): DataResponse<Forecast>
}

