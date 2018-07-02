package com.santos.herald.appsolutelytakehome

import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho
import com.santos.herald.appsolutelytakehome.di.component.ApplicationComponent
import com.santos.herald.appsolutelytakehome.di.component.DaggerApplicationComponent
import com.santos.herald.appsolutelytakehome.di.module.*
import com.santos.herald.appsolutelytakehome.platform.logging.ErrorReportingTree
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

open class App : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        applicationComponent = initializeAppComponent()
        initialize()
    }

    open fun initializeAppComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .dataModule(DataModule())
                .networkModule(NetworkModule())
                .mapperModule(MapperModule())
                .interactorModule(InteractorModule())
                .build()
    }

    private fun initialize() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ErrorReportingTree())
        }
    }

    fun appComponent(): ApplicationComponent {
        return applicationComponent
    }

    companion object {
        lateinit var instance: App
    }
}