package com.example.theyumexplorer.repository.impl

import android.content.Context
import com.example.theyumexplorer.extension.toTheYumUser
import com.example.theyumexplorer.model.User
import com.example.theyumexplorer.repository.LoginRepository
import com.example.theyumexplorer.util.TheYumCollections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    val context: Context,
    val auth: FirebaseAuth,
    val db: FirebaseFirestore
) : LoginRepository {
    override suspend fun SignInWithEmailAndPassword(email: String, password: String): User? {
        val userList = db.collection(TheYumCollections.USER.name).get().await().query.whereEqualTo(
            "email",
            email
        ).get().await().toObjects<User>()
        return if (userList.isNotEmpty())
            auth.signInWithEmailAndPassword(email, password)
                .await().user.toTheYumUser()
        else null
    }

    override suspend fun SigninGoogleAccount(idtoken: String) =
        auth.signInWithCredential(GoogleAuthProvider.getCredential(idtoken, null))
            .await().user.toTheYumUser()

    override suspend fun LoginAnonymous() = auth.signInAnonymously().await().user.toTheYumUser()
}
