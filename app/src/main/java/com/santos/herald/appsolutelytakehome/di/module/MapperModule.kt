package com.santos.herald.appsolutelytakehome.di.module

import com.santos.herald.data.mapper.*
import com.santos.herald.data.mapper.base.Mapper
import com.santos.herald.data.source.model.X
import com.santos.herald.domain.model.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperModule {


    @Singleton
    @Provides
    internal fun weatherDtoMapper(mapper: WeatherDtoMapper): Mapper<WeatherModel, X> {
        return mapper
    }

    @Singleton
    @Provides
    internal fun coordinatesMapper(mapper: CoordinatesMapper): Mapper<Coord, com.santos.herald.data.source.model.Coord> {
        return mapper
    }

    @Singleton
    @Provides
    internal fun sysMapper(mapper: SysMapper): Mapper<Sys, com.santos.herald.data.source.model.Sys> {
        return mapper
    }

    @Singleton
    @Provides
    internal fun cloudMapper(mapper: CloudMapper): Mapper<Clouds, com.santos.herald.data.source.model.Clouds> {
        return mapper
    }

    @Singleton
    @Provides
    internal fun mainMapper(mapper: MainMapper): Mapper<Main, com.santos.herald.data.source.model.Main> {
        return mapper
    }

    @Singleton
    @Provides
    internal fun weatherMapper(mapper: WeatherMapper): Mapper<Weather, com.santos.herald.data.source.model.Weather> {
        return mapper
    }

    @Singleton
    @Provides
    internal fun windMapper(mapper: WindMapper): Mapper<Wind, com.santos.herald.data.source.model.Wind> {
        return mapper
    }

}
