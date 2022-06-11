package com.john.wathermvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.john.wathermvvm.model.City
import com.john.wathermvvm.model.Forecast
import com.john.wathermvvm.data.local.cache.city.CityDao
import com.john.wathermvvm.data.local.cache.forecast.ForecastDao

@Database(entities = [City::class, Forecast::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

  abstract fun weatherDao(): CityDao
  abstract fun forecastDao(): ForecastDao

  companion object {
    const val DATABASE_NAME: String = "weather_db"
  }
}
