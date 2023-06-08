package com.example.theyumexplorer.di

import com.example.theyumexplorer.repository.SignupRepository
import com.example.theyumexplorer.repository.impl.SignupRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class, SingletonComponent::class)
abstract class SignupModule {
    @Binds
    abstract fun provideSignupRepository(impl: SignupRepositoryImpl): SignupRepository
}