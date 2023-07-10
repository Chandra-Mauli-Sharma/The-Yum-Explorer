package com.example.theyumexplorer.repository.impl

import android.content.Context
import com.example.theyumexplorer.model.Content
import com.example.theyumexplorer.model.User
import com.example.theyumexplorer.repository.ExplorerRepository
import com.example.theyumexplorer.util.TheYumCollections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExplorerRepositoryImpl @Inject constructor(
    val context: Context, private val auth: FirebaseAuth, private val db: FirebaseFirestore
) : ExplorerRepository {
    override suspend fun GetContent() =
        db.collection(TheYumCollections.CONTENT.name).get().await().toObjects(Content::class.java)
            .shuffled()

    override suspend fun GetUser(): User? =
        db.collection(TheYumCollections.USER.name).document(auth.currentUser?.uid!!).get().await()
            .toObject<User>()
}