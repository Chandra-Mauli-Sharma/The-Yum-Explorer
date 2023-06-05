package com.example.theyumexplorer.di

import com.example.theyumexplorer.repository.LoginRepository
import com.example.theyumexplorer.repository.UploadContentRepository
import com.example.theyumexplorer.repository.impl.LoginRepositoryImpl
import com.example.theyumexplorer.repository.impl.UploadContentRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class, SingletonComponent::class)
abstract class UploadContentModule {
    @Binds
    abstract fun provideUploadContentRepository(impl: UploadContentRepositoryImpl): UploadContentRepository
}