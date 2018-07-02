package com.santos.herald.data.mapper

import com.santos.herald.data.mapper.base.Mapper
import com.santos.herald.data.source.model.Weather
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherMapper
@Inject
constructor() : Mapper<com.santos.herald.domain.model.Weather, Weather>() {

    override fun map(from: Weather): com.santos.herald.domain.model.Weather {
        val id = from.id ?: invalidInt
        val main = from.main ?: emptyString
        val description = from.description ?: emptyString
        val icon = from.icon ?: emptyString

        return com.santos.herald.domain.model.Weather(id, main, description, icon)
    }

    override fun reverse(to: com.santos.herald.domain.model.Weather): Weather {
        return Weather(to.id, to.main, to.description, to.icon)
    }


}