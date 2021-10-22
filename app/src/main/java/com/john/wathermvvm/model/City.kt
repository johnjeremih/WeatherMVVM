package com.john.wathermvvm.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CityTable", indices = [Index(value = ["cityName"], unique = true)])
data class City(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long?,

    @ColumnInfo(name = "countryName")
    @SerializedName("country_code")
    var countryName: String?,

    @ColumnInfo(name = "cityName")
    @SerializedName("city_name")
    var cityName: String?,

    @ColumnInfo(name = "tempInF")
    @SerializedName("rh")
    var tempInF: String?,

    @ColumnInfo(name = "tempInC")
    @SerializedName("temp")
    var tempInC: String?,

    @ColumnInfo(name = "precipitation")
    @SerializedName("precip")
    var precipitation: Double?,

    @ColumnInfo(name = "lon")
    var lon: Double?,

    @ColumnInfo(name = "lat")
    var lat: Double?,

    @ColumnInfo(name = "cityId")
    var cityId: Long?,

    @Embedded
    var weather: Weather?,

    @ColumnInfo(name = "lastUpdate")
    @SerializedName("ob_time")
    var lastUpdate: String?,

    @ColumnInfo(name = "timeStamp")
    @SerializedName("timestamp_local")
    var timeStamp: String?,

    @ColumnInfo(name = "dateTime")
    @SerializedName("datetime")
    var dateTime: String?,

    @ColumnInfo(name = "highTemp")
    @SerializedName("high_temp")
    var highTemp: String?,

    @ColumnInfo(name = "lowTemp")
    @SerializedName("low_temp")
    var lowTemp: String?,

    ) {
    fun getTempString(): String {
        return (tempInC?.substringBefore(".") ?: "") + "°F"
    }

    fun getLowTempString(): String {
        return (lowTemp?.substringBefore(".") ?: "") + "°F"
    }

    fun getHighTempString(): String {
        return (highTemp?.substringBefore(".") ?: "") + "°F"
    }
}


data class WeatherDataResponse(
    @SerializedName("data")
    val `data`: ArrayList<City>,
)

