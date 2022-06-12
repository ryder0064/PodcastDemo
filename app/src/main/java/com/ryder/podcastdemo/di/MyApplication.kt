package com.ryder.podcastdemo.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            androidFileProperties()
            modules(
                listOf(
                    localDataSourceModule,
                    remoteDataSourceModule,
                    repositoryModule,
                    episodesModule,
                    episodeDetailModule
                )
            )
        }
    }
}
