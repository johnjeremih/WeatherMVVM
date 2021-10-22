package com.john.wathermvvm.repository.cache.forecast

import androidx.room.*
import com.john.wathermvvm.model.City

@Database(entities = [City::class ], version = 1,exportSchema = false)
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

    companion object{
        const val DATABASE_NAME: String = "forecast_db"
    }
}
