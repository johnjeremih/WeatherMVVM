package com.john.weathermvvm.model

import androidx.room.Entity

@Entity(tableName = "Weather")
data class Weather(
    var main: String = "",
    var description: String = "",
    var icon: String = "",
)
