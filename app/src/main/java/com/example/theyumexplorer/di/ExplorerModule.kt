package com.example.theyumexplorer.di

import com.example.theyumexplorer.repository.ExplorerRepository
import com.example.theyumexplorer.repository.LoginRepository
import com.example.theyumexplorer.repository.impl.ExplorerRepositoryImpl
import com.example.theyumexplorer.repository.impl.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class, SingletonComponent::class)
abstract class ExplorerModule {
    @Binds
    abstract fun provideExplorerRepository(impl: ExplorerRepositoryImpl): ExplorerRepository
}