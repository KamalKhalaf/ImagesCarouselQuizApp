package com.example.quizapp.common.di

import com.example.quizapp.domain.source.DataRepositorySource
import com.example.quizapp.domain.source.DataRepositorySourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Created by: Kamal.Farghali
 * @Date: 04/11/2023 : 3:52 AM
 */

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideDataRepository() : DataRepositorySource {
        return DataRepositorySourceImpl()
    }
}
