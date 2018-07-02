package com.santos.herald.appsolutelytakehome.di.module

import com.santos.herald.appsolutelytakehome.di.scope.ViewScope
import com.santos.herald.appsolutelytakehome.ui.weatherdetail.WeatherDetailContract
import com.santos.herald.appsolutelytakehome.ui.weatherdetail.WeatherDetailPresenter
import com.santos.herald.appsolutelytakehome.ui.weatherlist.WeatherListContract
import com.santos.herald.appsolutelytakehome.ui.weatherlist.WeatherListPresenter
import com.santos.herald.domain.interactor.definition.GetWeatherRemotelyUseCase
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @ViewScope
    @Provides
    internal fun weatherListPresenter(remotelyUseCaseGet: GetWeatherRemotelyUseCase): WeatherListContract.Presenter {
        return WeatherListPresenter(remotelyUseCaseGet)
    }

    @ViewScope
    @Provides
    internal fun weatherDetailPresenter(remotelyUseCaseGet: GetWeatherRemotelyUseCase): WeatherDetailContract.Presenter {
        return WeatherDetailPresenter(remotelyUseCaseGet)
    }
}
