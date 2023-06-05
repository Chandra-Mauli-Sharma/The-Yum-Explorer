package com.example.theyumexplorer.repository.impl

import android.content.Context
import com.example.theyumexplorer.repository.LoginRepository
import com.example.theyumexplorer.util.dataStore
import com.example.theyumexplorer.util.writeBool
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.models.Session
import io.appwrite.services.Account
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    val context: Context,
    val client: Client,
    val account: Account
) : LoginRepository {
    override suspend fun CreateAccount() {
        account.create(
            userId = ID.unique(),
            email = "email@example.com",
            password = "password"
        )
    }

    override suspend fun LoginAnonymous(): Session {
        val session=account.createAnonymousSession()
        context.writeBool("isLogged",true)
        return session
    }

}