package com.example.theyumexplorer.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    var uid: String? = "",
    val name: String? = "",
    val email: String? = "",
    val phone: String? = "",
    val photoUrl: String? = "",
    val contentList: List<String> = listOf()
)