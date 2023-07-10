package com.example.theyumexplorer.repository.impl

import android.content.Context
import com.example.theyumexplorer.extension.toTheYumUser
import com.example.theyumexplorer.model.User
import com.example.theyumexplorer.repository.SignupRepository
import com.example.theyumexplorer.util.TheYumCollections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(
    val context: Context,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : SignupRepository {
    override suspend fun CreateUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String
    ): User {
        val user = auth.createUserWithEmailAndPassword(email, password).await().user
        user?.updateProfile(userProfileChangeRequest { displayName = name })?.await()
        auth.updateCurrentUser(user!!).await()
        SaveUserToDb(user.toTheYumUser())
        return user.toTheYumUser()
    }

    override suspend fun SaveUserToDb(user: User) {
        db.collection(TheYumCollections.USER.name).document(user.uid!!).set(user).await()
    }

    override suspend fun CreateGoogleAccount(idtoken: String): User {
        val user =
            auth.signInWithCredential(GoogleAuthProvider.getCredential(idtoken, null))
                .await().user.toTheYumUser()
        SaveUserToDb(user)
        return user
    }


    override suspend fun GetUser() = auth.currentUser.toTheYumUser()
}