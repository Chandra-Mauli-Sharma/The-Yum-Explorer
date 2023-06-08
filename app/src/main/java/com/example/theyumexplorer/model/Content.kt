package com.example.theyumexplorer.model

import com.google.firebase.firestore.DocumentId

data class Content(
    @DocumentId
    val id: String,
    val uid: String,
    val contentType: String?,
    val contentUrl: String?,
    val title: String,
    val description: String,
    val contentLocationType: ContentLocationType,
    val contentLocation: ContentLocation
)

enum class ContentLocationType {
    HOME,
    OUTSIDE
}

data class ContentLocation(
    val latitude: Double,
    val longitude: Double
)
