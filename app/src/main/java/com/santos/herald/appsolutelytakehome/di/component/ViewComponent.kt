package com.santos.herald.appsolutelytakehome.di.component

import com.santos.herald.appsolutelytakehome.di.module.PresenterModule
import com.santos.herald.appsolutelytakehome.di.scope.ViewScope
import com.santos.herald.appsolutelytakehome.ui.weatherdetail.WeatherDetailActivity
import com.santos.herald.appsolutelytakehome.ui.weatherlist.WeatherListActivity
import dagger.Component

@ViewScope
@Component(dependencies = arrayOf(
        ApplicationComponent::class),
        modules = arrayOf(PresenterModule::class))
interface ViewComponent {

    fun inject(activity: WeatherListActivity)
    fun inject(activity: WeatherDetailActivity)

}
