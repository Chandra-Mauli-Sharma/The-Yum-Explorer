package com.example.theyumexplorer.di

import android.content.Context
import androidx.work.WorkManager
import com.example.theyumexplorer.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideContext(): Context = MainActivity.getInstance() as MainActivity

    @Provides
    fun provideWorkManager(context: Context) = WorkManager.getInstance(context)
}