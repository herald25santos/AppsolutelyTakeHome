package com.santos.herald.data.mapper

import com.santos.herald.data.mapper.base.Mapper
import com.santos.herald.data.source.model.Main
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainMapper
@Inject
constructor() : Mapper<com.santos.herald.domain.model.Main, Main>() {

    override fun map(from: Main): com.santos.herald.domain.model.Main {

        val temp = from.temp ?: invalidDouble
        val pressure = from.pressure ?: invalidInt
        val humidity = from.humidity ?: invalidInt
        val temp_min = from.temp_min ?: invalidDouble
        val temp_max = from.temp_max ?: invalidDouble

        return com.santos.herald.domain.model.Main(temp, pressure, humidity, temp_min, temp_max)
    }

    override fun reverse(to: com.santos.herald.domain.model.Main): Main {
        return Main(to.temp, to.pressure, to.humidity, to.temp_min, to.temp_max)
    }


}