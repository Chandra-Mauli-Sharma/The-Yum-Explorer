package com.example.theyumexplorer.di

import com.example.theyumexplorer.repository.ProfileRepository
import com.example.theyumexplorer.repository.impl.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class, SingletonComponent::class)
abstract class ProfileModule {
    @Binds
    abstract fun provideProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository
}