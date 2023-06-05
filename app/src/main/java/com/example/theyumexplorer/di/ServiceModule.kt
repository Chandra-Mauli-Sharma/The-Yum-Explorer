package com.example.theyumexplorer.di

import android.content.Context
import com.example.theyumexplorer.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import io.appwrite.Client
import io.appwrite.services.Account
import io.appwrite.services.Storage

@Module
@InstallIn(ViewModelComponent::class, SingletonComponent::class)
object ServiceModule {
    @Provides
    fun appWriteClient(context: Context) = Client(context)
        .setEndpoint("https://cloud.appwrite.io/v1")
        .setProject("6477036f1de27ad8b0ea")

    @Provides
    fun providesStorage(client: Client) = Storage(client)

    @Provides
    fun providesAccount(client: Client) = Account(client)
}