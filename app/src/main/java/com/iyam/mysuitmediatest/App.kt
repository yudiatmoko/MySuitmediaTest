package com.iyam.mysuitmediatest

import android.app.Application
import com.iyam.mysuitmediatest.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(AppModules.modules)
        }
    }
}
