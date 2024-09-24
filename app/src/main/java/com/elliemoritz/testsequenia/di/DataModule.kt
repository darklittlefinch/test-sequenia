package com.elliemoritz.testsequenia.di

import com.elliemoritz.testsequenia.data.mapper.MoviesMapper
import com.elliemoritz.testsequenia.data.network.ApiService
import com.elliemoritz.testsequenia.data.repository.MoviesRepositoryImpl
import com.elliemoritz.testsequenia.domain.MoviesRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://s3-eu-west-1.amazonaws.com/sequeniatesttask/"

val dataModule = module {
    single { provideService() }
    single { provideMapper() }
    single<MoviesRepository> { MoviesRepositoryImpl(get(), get()) }
}

private fun provideService(): ApiService {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(ApiService::class.java)
}

private fun provideMapper(): MoviesMapper {
    return MoviesMapper()
}