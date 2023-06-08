package com.example.theyumexplorer.repository

import android.net.Uri
import androidx.work.ListenableWorker.Result
import com.example.theyumexplorer.model.Content

interface UploadContentRepository {
    suspend fun UploadContent(content: Content, currentImage: Uri)
    suspend fun UploadImageUri(uri: Uri, contentId: String): Result
}