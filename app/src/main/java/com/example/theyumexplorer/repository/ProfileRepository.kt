package com.example.theyumexplorer.repository

import com.example.theyumexplorer.model.User


interface ProfileRepository {
    suspend fun getUserDetails(): User

}