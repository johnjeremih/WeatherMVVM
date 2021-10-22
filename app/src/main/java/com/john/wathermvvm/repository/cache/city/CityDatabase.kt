package com.john.wathermvvm.repository.cache.city

import androidx.room.Database
import androidx.room.RoomDatabase
import com.john.wathermvvm.model.City

@Database(entities = [City::class ], version = 1,exportSchema = false)
abstract class CityDatabase : RoomDatabase() {

    abstract fun weatherDao(): CityDao

    companion object{
        const val DATABASE_NAME: String = "city_db"
    }
}