package com.towich.vktest.di

import android.app.Application
import com.towich.vktest.data.repository.MainRepository
import com.towich.vktest.data.repository.MainRepositoryImpl
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
    fun provideMainRepository(
        app: Application
    ): MainRepository {
        return MainRepositoryImpl(
            appContext = app
        )
    }
}