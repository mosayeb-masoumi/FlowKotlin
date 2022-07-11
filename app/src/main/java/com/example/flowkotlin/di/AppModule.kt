package com.example.flowkotlin.di

import com.example.cleanarchitecture_xml.data.remote.ApiService
import com.example.cleanarchitecture_xml.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return RetrofitClient().buildService(ApiService::class.java)
    }
}