package com.santos.herald.appsolutelytakehome.di.component

import android.content.Context
import com.petrulak.cleankotlin.domain.executor.SchedulerProvider
import com.santos.herald.appsolutelytakehome.di.module.*
import com.santos.herald.appsolutelytakehome.platform.bus.data.DataBus
import com.santos.herald.appsolutelytakehome.platform.bus.event.EventBus
import com.santos.herald.appsolutelytakehome.platform.navigation.Navigator
import com.santos.herald.appsolutelytakehome.ui.base.BaseActivity
import com.santos.herald.appsolutelytakehome.ui.base.BaseFragment
import com.santos.herald.appsolutelytakehome.utils.SystemUtils
import com.santos.herald.domain.interactor.definition.GetWeatherRemotelyUseCase
import com.santos.herald.domain.interactor.definition.GetWeatherUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        DataModule::class,
        MapperModule::class,
        NetworkModule::class,
        InteractorModule::class,
        ApplicationModule::class))
interface ApplicationComponent {

    fun inject(item: BaseActivity)
    fun inject(item: BaseFragment)

    /* exposing to other components [ViewComponent] */
    fun context(): Context

    fun scheduler(): SchedulerProvider
    fun navigator(): Navigator
    fun eventBus(): EventBus
    fun dataBus(): DataBus
    fun systemUtils(): SystemUtils
    fun getWeatherUseCase(): GetWeatherUseCase
    fun getWeatherRemotelyUseCase(): GetWeatherRemotelyUseCase
}
