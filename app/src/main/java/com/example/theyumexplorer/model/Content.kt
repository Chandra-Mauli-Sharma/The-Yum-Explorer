package com.example.theyumexplorer.model

import com.example.theyumexplorer.util.TheYumContent
import com.google.firebase.firestore.DocumentId

data class Content(
    @DocumentId
    val id: String? = null,
    val uid: String = "",
    val contentType: TheYumContent = TheYumContent.Video,
    val contentUrl: String? = null,
    val title: String = "",
    val description: String = "",
    val contentLocationType: ContentLocationType = ContentLocationType.HOME,
    val contentLocation: ContentLocation? = null
)

enum class ContentLocationType {
    HOME,
    OUTSIDE
}

data class ContentLocation(
    val latitude: Double,
    val longitude: Double
)
