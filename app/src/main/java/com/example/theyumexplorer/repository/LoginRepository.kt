package com.example.theyumexplorer.repository

import com.example.theyumexplorer.model.User


interface LoginRepository {
    suspend fun LoginAnonymous(): User
    suspend fun SigninGoogleAccount(idtoken: String): User
    suspend fun SignInWithEmailAndPassword(email: String, password: String): User?
}