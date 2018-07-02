package com.santos.herald.data.repository


import com.santos.herald.data.mapper.base.Mapper
import com.santos.herald.data.source.RemoteSource
import com.santos.herald.data.source.model.X
import com.santos.herald.domain.model.WeatherModel
import com.santos.herald.domain.repository.WeatherRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl
@Inject
constructor(
        private val remoteSource: RemoteSource,
        private val weatherDtoMapper: Mapper<WeatherModel, X>
) : WeatherRepository {

    override fun getWeatherByIdsRemotely(ids: String): Single<List<WeatherModel>> {
        return remoteSource
                .getWeatherByIds(ids)
                .map { weatherDtoMapper.map(it.list) }
    }

    override fun getWeatherByIdRemotely(id: String): Single<WeatherModel> {
        return remoteSource
                .getWeatherById(id)
                .map { weatherDtoMapper.map(it) }
    }

    override fun getWeatherByCoordinates(lat: String, lon: String): Single<List<WeatherModel>> {
        return remoteSource
                .getWeatherByCoordinates(lat, lon)
                .map { listOf(weatherDtoMapper.map(it)) }
    }

    override fun getWeatherRemotely(ids: String, lat: String, lon: String): Single<List<WeatherModel>> {
        val remoteByIds = getWeatherByIdsRemotely(ids).toFlowable()
        val remoteByLocation = getWeatherByCoordinates(lat, lon).toFlowable()
        return Flowable.merge(remoteByIds, remoteByLocation)
                .toList()
                .map {
                    val l = ArrayList<WeatherModel>()
                    for (list in it) {
                        l.addAll(list)
                    }
                    l
                }
    }

}
