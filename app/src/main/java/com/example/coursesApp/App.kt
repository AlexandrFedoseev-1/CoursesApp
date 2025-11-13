package com.example.coursesApp

import android.app.Application
import com.example.coursesApp.di.appModule
import com.example.coursesApp.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(dataModule, appModule)
        }
    }
}