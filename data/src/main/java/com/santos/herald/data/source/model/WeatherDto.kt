package com.santos.herald.data.source.model


data class WeatherDto(
    val cnt: Int,
    val list: List<X>
)

data class X(
        val coord: Coord,
        val sys: Sys,
        val weather: List<Weather>,
        val main: Main,
        val visibility: Int?,
        val wind: Wind,
        val clouds: Clouds,
        val dt: Int?,
        val id: Int?,
        val name: String?
)

data class Main(
    val temp: Double?,
    val pressure: Int?,
    val humidity: Int?,
    val temp_min: Double?,
    val temp_max: Double?
)

data class Wind(
    val speed: Double?,
    val deg: Double?
)

data class Weather(
    val id: Int?,
    val main: String?,
    val description: String?,
    val icon: String?
)

data class Coord(
    val lon: Double?,
    val lat: Double?
)

data class Sys(
    val type: Int?,
    val id: Int?,
    val message: Double?,
    val country: String?,
    val sunrise: Int?,
    val sunset: Int?
)

data class Clouds(
    val all: Int?
)