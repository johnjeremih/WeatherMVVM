package com.john.wathermvvm.data.remote

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UrlMainProvider @Inject constructor() : UrlProvider {
    override val baseUrl: String
        get() = "https://weatherbit-v1-mashape.p.rapidapi.com/"
}
