package com.example.theyumexplorer.repository

import com.example.theyumexplorer.model.Content
import com.example.theyumexplorer.model.User

interface ExplorerRepository {
    suspend fun GetContent(): List<Content>

    suspend fun GetUser(): User?
}