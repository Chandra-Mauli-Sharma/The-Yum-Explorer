package com.example.theyumexplorer.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class, SingletonComponent::class)
object ServiceModule {

    @Provides
    fun providesStorage() = FirebaseStorage.getInstance()

    @Provides
    fun providesAuth() = FirebaseAuth.getInstance()

    @Provides
    fun providesDatabase() = Firebase.firestore
}