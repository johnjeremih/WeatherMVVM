package com.john.wathermvvm.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Weather")
data class Weather(
    var main: String = "",
    var description: String = "",
    var icon: String = "",
) : Parcelable
