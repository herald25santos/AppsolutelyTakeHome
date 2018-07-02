package com.santos.herald.data.source.remote


import com.santos.herald.data.source.RemoteSource
import com.santos.herald.data.source.model.WeatherDto
import com.santos.herald.data.source.model.X
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRemoteSource
@Inject
constructor(retrofit: Retrofit) : RemoteSource {
    private val client: WeatherApiClient = retrofit.create(WeatherApiClient::class.java)

    override fun getWeatherByIds(ids: String): Single<WeatherDto> {
        return client.getWeatherByIds(ids)
    }

    override fun getWeatherById(id: String): Single<X> {
        return client.getWeatherById(id)
    }

    override fun getWeatherByCoordinates(lat: String, lon: String): Single<X> {
        return client.getWeatherByCoordinates(lat.toFloat(), lon.toFloat())
    }

}
