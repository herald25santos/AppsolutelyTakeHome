package com.santos.herald.appsolutelytakehome.di.module

import android.content.Context
import com.petrulak.cleankotlin.domain.executor.SchedulerProvider
import com.petrulak.cleankotlin.domain.executor.SchedulerProviderImpl
import com.santos.herald.appsolutelytakehome.platform.bus.data.DataBus
import com.santos.herald.appsolutelytakehome.platform.bus.event.EventBus
import com.santos.herald.appsolutelytakehome.platform.navigation.Navigator
import com.santos.herald.appsolutelytakehome.utils.SystemUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

    @Singleton
    @Provides
    internal fun context(): Context {
        return context
    }

    @Singleton
    @Provides
    internal fun eventBus(): EventBus {
        return EventBus()
    }

    @Singleton
    @Provides
    internal fun dataBus(): DataBus {
        return DataBus()
    }

    @Singleton
    @Provides
    internal fun schedulerProvider(): SchedulerProvider {
        return SchedulerProviderImpl()
    }

    @Singleton
    @Provides
    internal fun navigator(): Navigator {
        return Navigator()
    }

    @Singleton
    @Provides
    internal fun systemUtils(context: Context): SystemUtils {
        return SystemUtils(context)
    }

}
