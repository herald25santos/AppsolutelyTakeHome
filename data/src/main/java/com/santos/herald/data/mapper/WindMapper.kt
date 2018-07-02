package com.santos.herald.data.mapper

import com.santos.herald.data.mapper.base.Mapper
import com.santos.herald.data.source.model.Wind
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WindMapper
@Inject
constructor() : Mapper<com.santos.herald.domain.model.Wind, Wind>() {

    override fun map(from: Wind): com.santos.herald.domain.model.Wind {

        val speed = from.speed ?: invalidDouble
        val deg = from.deg ?: invalidDouble

        return com.santos.herald.domain.model.Wind(speed, deg)
    }

    override fun reverse(to: com.santos.herald.domain.model.Wind): Wind {
        return Wind(to.speed, to.deg)
    }


}