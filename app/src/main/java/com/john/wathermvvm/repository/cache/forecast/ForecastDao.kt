package com.john.wathermvvm.repository.cache.forecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.john.wathermvvm.model.City

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: City): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateForecast(forecast: City)

    @Query("SELECT * FROM CityTable WHERE cityId = :cityId")
    suspend fun getForecast(cityId: Long): List<City>

    @Query("DELETE FROM CityTable WHERE cityId = :cityId")
    suspend fun deleteForecast(cityId: Long)

    @Query("DELETE FROM CityTable")
    suspend fun clearAllForecast()

}