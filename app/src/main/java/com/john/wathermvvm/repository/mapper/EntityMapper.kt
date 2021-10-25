package com.john.wathermvvm.repository.mapper

interface EntityMapper<NetworkEntity, CacheEntity,CityId> {

    fun buildModel(entity: NetworkEntity, mainModel: CacheEntity,value:CityId): CacheEntity

}