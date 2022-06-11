package com.john.wathermvvm.util.mapper

interface EntityMapper<T, R, Q> {

  fun buildModel(entity: T, cachedEntity: R, id: Q?): R
}
