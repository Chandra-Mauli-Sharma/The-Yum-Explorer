package com.example.theyumexplorer.repository

import com.example.theyumexplorer.model.Content
import com.example.theyumexplorer.model.User


interface ProfileRepository {
    suspend fun getUserDetails(): User?
    suspend fun fetchContentList(user: User): List<Content>
    suspend fun isLoggedIn(): Boolean
}