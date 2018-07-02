package com.santos.herald.domain.model



data class WeatherModel(
    var coord: Coord,
    var sys: Sys,
    var weather: List<Weather>,
    var main: Main,
    var visibility: Int,
    var wind: Wind,
    var clouds: Clouds,
    var dt: Int,
    var id: Int,
    var name: String
)

data class Main(
    var temp: Double,
    var pressure: Int,
    var humidity: Int,
    var temp_min: Double,
    var temp_max: Double
)

data class Weather(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)

data class Coord(
    var lon: Double,
    var lat: Double
)

data class Sys(
    var type: Int,
    var id: Int,
    var message: Double,
    var country: String,
    var sunrise: Int,
    var sunset: Int
)

data class Clouds(
    var all: Int
)

data class Wind(
    var speed: Double,
    var deg: Double
)