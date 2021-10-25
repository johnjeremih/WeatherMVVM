package com.john.wathermvvm.repository.mapper

import com.john.wathermvvm.model.City
import javax.inject.Inject

class UpdateCityMapper
@Inject constructor() : EntityMapper<City, City,Long> {
    override fun buildModel(entity: City, mainModel: City, value: Long): City {

        return City(
            id = mainModel.cityId,
            countryName = entity.countryName,
            cityName = entity.cityName,
            tempInF = entity.tempInF,
            tempInC = entity.tempInC,
            precipitation = entity.precipitation,
            lon = entity.lon,
            lat = entity.lat,
            cityId = value,
            weather = entity.weather,
            lastUpdate = entity.lastUpdate,
            timeStamp = entity.timeStamp,
            highTemp = entity.highTemp,
            lowTemp = entity.lowTemp,
            dateTime = entity.dateTime

        )    }


}