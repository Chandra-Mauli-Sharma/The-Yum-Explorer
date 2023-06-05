package com.example.theyumexplorer.repository

import io.appwrite.models.Session


interface LoginRepository {
    suspend fun CreateAccount()
    suspend fun LoginAnonymous(): Session
}