package com.john.wathermvvm.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "City", indices = [Index(value = ["cityName"], unique = true)])
data class City(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long?,
    @ColumnInfo(name = "countryName") @SerializedName("country_code") var countryName: String?,
    @ColumnInfo(name = "cityName") @SerializedName("city_name") var cityName: String?,
    @ColumnInfo(name = "tempInF") @SerializedName("rh") var tempInF: String?,
    @ColumnInfo(name = "tempInC") @SerializedName("temp") var tempInC: String?,
    @ColumnInfo(name = "precipitation") @SerializedName("precip") var precipitation: Double?,
    @ColumnInfo(name = "lon") var lon: Double?,
    @ColumnInfo(name = "lat") var lat: Double?,
    @Embedded var weather: Weather?,
    @ColumnInfo(name = "lastUpdate") @SerializedName("ob_time") var lastUpdate: String?,
    @ColumnInfo(name = "timeStamp") @SerializedName("timestamp_local") var timeStamp: String?,
    @ColumnInfo(name = "dateTime") @SerializedName("datetime") var dateTime: String?,
) {
    fun getTempString(): String {
        return (tempInC?.substringBefore(".") ?: "") + "°F"
    }
}
