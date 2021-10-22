package com.john.wathermvvm.repository.mapper

interface EntityMapper<Entity, MainModel> {

    fun mapFrom(entity: Entity, cityId: Long): MainModel

}