package com.santos.herald.data.source.remote

import com.santos.herald.data.source.model.WeatherDto
import com.santos.herald.data.source.model.X
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiClient {

    @GET("data/2.5/group")
    fun getWeatherByIds(@Query("id") ids: String): Single<WeatherDto>

    @GET("data/2.5/weather")
    fun getWeatherById(@Query("id") id: String): Single<X>

    @GET("data/2.5/weather")
    fun getWeatherByCoordinates(@Query("lat") lat: Float, @Query("lon") lon: Float): Single<X>




}