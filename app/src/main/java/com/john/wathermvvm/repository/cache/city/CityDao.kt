package com.john.wathermvvm.repository.cache.city

import androidx.room.*
import com.john.wathermvvm.model.City

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: City): Long

    @Update
    suspend fun update(city: City)

    @Query("SELECT * FROM CityTable ORDER BY cityName ASC")
    suspend fun getCities(): List<City>

    @Query("SELECT * FROM CityTable WHERE id = :cityId")
    suspend fun getCity(cityId: Long): City

    @Query("DELETE FROM CityTable WHERE id = :cityId")
    suspend fun deleteCity(cityId: Long)

    @Query("DELETE FROM CityTable")
    suspend fun clearALL()

}