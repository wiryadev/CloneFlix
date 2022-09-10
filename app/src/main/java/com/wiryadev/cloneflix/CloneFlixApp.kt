package com.wiryadev.cloneflix

import android.app.Application
import com.wiryadev.cloneflix.di.AppModules
import com.wiryadev.login.di.LoginModule
import com.wiryadev.shared.di.SharedModules
import com.wiryadev.splashscreen.di.SplashscreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CloneFlixApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CloneFlixApp)
            modules(
                AppModules.getModules()
                        + SharedModules.getModules()
                        + SplashscreenModule.getModules()
                        + LoginModule.getModules()
            )
        }
    }
}