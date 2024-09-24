package com.elliemoritz.testsequenia

import android.app.Application
import com.elliemoritz.testsequenia.di.dataModule
import com.elliemoritz.testsequenia.di.domainModule
import com.elliemoritz.testsequenia.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApplication)
            androidLogger()
            modules(dataModule, domainModule, viewModelModule)
        }
    }
}