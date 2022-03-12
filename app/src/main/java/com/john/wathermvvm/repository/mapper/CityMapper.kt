package com.john.wathermvvm.repository.mapper

import com.john.wathermvvm.model.City
import javax.inject.Inject

class CityMapper
@Inject constructor() : EntityMapper<City, City,Long> {

    override fun buildModel(entity: City, cachedEntity: City, id: Long?): City {
        return City(
            countryName = entity.countryName,
            cityName = entity.cityName,
            tempInF = entity.tempInF,
            tempInC = entity.tempInC,
            precipitation = entity.precipitation,
            lon = entity.lon,
            lat = entity.lat,
            id = id,
            weather = entity.weather,
            lastUpdate = entity.lastUpdate,
            timeStamp = entity.timeStamp,
            dateTime = entity.dateTime

        )
    }


}