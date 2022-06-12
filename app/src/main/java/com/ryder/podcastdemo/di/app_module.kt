package com.ryder.podcastdemo.di

import androidx.room.Room
import com.ryder.podcastdemo.data.local.EpisodesDatabase
import com.ryder.podcastdemo.data.remote.EpisodesService
import com.ryder.podcastdemo.data.repository.EpisodesRepository
import com.ryder.podcastdemo.ui.detail.EpisodeDetailViewModel
import com.ryder.podcastdemo.ui.list.EpisodesViewModel
import com.ryder.podcastdemo.util.BASE_URL
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val localDataSourceModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            EpisodesDatabase::class.java,
            "episodes.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<EpisodesDatabase>().episodesDao() }
}

val remoteDataSourceModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(EpisodesService::class.java) }
}

val repositoryModule = module {
    single {
        EpisodesRepository(get(), get())
    }
}

val episodesModule = module {
    viewModel { EpisodesViewModel(get()) }
}

val episodeDetailModule = module {
    viewModel { EpisodeDetailViewModel(get()) }
}