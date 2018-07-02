package com.santos.herald.data.mapper

import com.santos.herald.data.mapper.base.Mapper
import com.santos.herald.data.source.model.Coord
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoordinatesMapper
@Inject
constructor() : Mapper<com.santos.herald.domain.model.Coord, Coord>() {

    override fun map(from: Coord): com.santos.herald.domain.model.Coord {

        val lon = from.lon ?: invalidDouble
        val lat = from.lat ?: invalidDouble

        return com.santos.herald.domain.model.Coord(lon, lat)
    }

    override fun reverse(to: com.santos.herald.domain.model.Coord): Coord {
        return Coord(to.lon, to.lat)
    }


}