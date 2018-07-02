package com.santos.herald.data.mapper

import com.santos.herald.data.mapper.base.Mapper
import com.santos.herald.data.source.model.*
import com.santos.herald.domain.model.WeatherModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDtoMapper
@Inject
constructor(
        var coordinatesMapper: Mapper<com.santos.herald.domain.model.Coord, Coord>,
        var sysMapper: Mapper<com.santos.herald.domain.model.Sys, Sys>,
        var weatherMapper: Mapper<com.santos.herald.domain.model.Weather, Weather>,
        var mainMapper: Mapper<com.santos.herald.domain.model.Main, Main>,
        var windMapper: Mapper<com.santos.herald.domain.model.Wind, Wind>,
        var cloudsMapper: Mapper<com.santos.herald.domain.model.Clouds, Clouds>

) : Mapper<WeatherModel, X>() {

    override fun map(from: X): WeatherModel {
        val coord = coordinatesMapper.map(from.coord)
        val sys = sysMapper.map(from.sys)
        val weather = weatherMapper.map(from.weather)
        val main = mainMapper.map(from.main)
        val visibility = from.visibility ?: invalidInt
        val wind = windMapper.map(from.wind)
        val clouds = cloudsMapper.map(from.clouds)
        val dt = from.dt ?: invalidInt
        val id = from.id ?: invalidInt
        val name = from.name ?: emptyString

        return WeatherModel(coord, sys, weather, main, visibility, wind, clouds, dt, id, name)
    }

    override fun reverse(to: WeatherModel): X {
        return X(
                coordinatesMapper.reverse(to.coord),
                sysMapper.reverse(to.sys),
                weatherMapper.reverse(to.weather),
                mainMapper.reverse(to.main),
                to.visibility,
                windMapper.reverse(to.wind),
                cloudsMapper.reverse(to.clouds),
                to.dt,
                to.id,
                to.name
        )
    }


}