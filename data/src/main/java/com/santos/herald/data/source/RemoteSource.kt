package com.santos.herald.data.source

import com.santos.herald.data.source.model.WeatherDto
import com.santos.herald.data.source.model.X
import io.reactivex.Single

interface RemoteSource {
    fun getWeatherByIds(ids: String): Single<WeatherDto>
    fun getWeatherById(id: String): Single<X>
    fun getWeatherByCoordinates(lat: String, lon: String): Single<X>
}
