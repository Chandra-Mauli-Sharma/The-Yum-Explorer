package com.example.theyumexplorer.repository.impl

import android.content.Context
import com.example.theyumexplorer.model.Content
import com.example.theyumexplorer.model.User
import com.example.theyumexplorer.repository.ProfileRepository
import com.example.theyumexplorer.util.TheYumCollections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    val context: Context,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val user: FirebaseUser?
) : ProfileRepository {
    override suspend fun getUserDetails(): User? =
        db.collection(TheYumCollections.USER.name).document(user?.uid!!).get().await()
            .toObject<User>()


    override suspend fun fetchContentList(user: User): List<Content> {
        return db.collection(TheYumCollections.CONTENT.name).get().await()
            .toObjects(Content::class.java).filter {
                user.contentList.contains(it.id)
            }
    }

    override suspend fun isLoggedIn(): Boolean = user != null
}