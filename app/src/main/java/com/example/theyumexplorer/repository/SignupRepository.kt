package com.example.theyumexplorer.repository

import com.example.theyumexplorer.model.User


interface SignupRepository {
    suspend fun GetUser(): User

    suspend fun SaveUserToDb(user: User)
    suspend fun CreateGoogleAccount(idtoken: String): User
    suspend fun CreateUserWithEmailAndPassword(email: String, password: String, name: String): User
}