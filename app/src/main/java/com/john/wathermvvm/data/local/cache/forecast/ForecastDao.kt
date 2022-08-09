package com.john.wathermvvm.data.local.cache.forecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.john.wathermvvm.model.Forecast

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: Forecast): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun updateForecast(forecast: Forecast)

    @Query("SELECT * FROM forecast WHERE cityId = :cityId")
    suspend fun getForecast(cityId: Long): List<Forecast>

    @Query("DELETE FROM forecast WHERE cityId = :cityId") suspend fun deleteForecast(cityId: Long)

    @Query("DELETE FROM forecast") suspend fun clearAllForecast()
}
