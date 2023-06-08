package com.example.theyumexplorer.repository.impl

import android.content.Context
import com.example.theyumexplorer.extension.toTheYumUser
import com.example.theyumexplorer.repository.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    val context: Context,
    private val auth: FirebaseAuth
) : ProfileRepository {
    override suspend fun getUserDetails() = auth.currentUser.toTheYumUser()

}