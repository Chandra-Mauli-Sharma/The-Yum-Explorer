package com.example.theyumexplorer.repository

import android.net.Uri
import androidx.work.ListenableWorker.Result
import java.io.File

interface UploadContentRepository {
    suspend fun UploadImage(image: File)
    suspend fun UploadImageUri(uri: Uri):Result
}