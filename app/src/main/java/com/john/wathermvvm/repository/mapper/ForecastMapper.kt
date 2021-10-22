package com.john.wathermvvm.repository.mapper

import com.john.wathermvvm.model.City
import javax.inject.Inject

class ForecastMapper
@Inject constructor() : EntityMapper<City, City> {

    override fun mapFrom(entity: City, cityId: Long): City {
        return City(
            id = entity.cityId,
            countryName = entity.countryName,
            cityName = entity.cityName,
            tempInF = entity.tempInF,
            tempInC = entity.tempInC,
            precipitation = entity.precipitation,
            lon = entity.lon,
            lat = entity.lat,
            cityId = cityId,
            weather = entity.weather,
            lastUpdate = entity.lastUpdate,
            timeStamp = entity.timeStamp,
            highTemp = entity.highTemp,
            lowTemp = entity.lowTemp,
            dateTime = entity.dateTime

        )
    }

}