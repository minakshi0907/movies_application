package com.example.moviesapplication.base

import android.app.Application
import android.content.Context
import com.example.moviesapplication.di.appModules
import com.example.moviesapplication.di.networkModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MoviesApp : Application() {

    companion object {
        lateinit var context : Context
        private set
    }
    private val allModules = listOf(
        appModules,
        networkModules
    )

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        startKoin {
            androidLogger()
            androidContext(this@MoviesApp)
            modules(allModules)
        }
    }
}