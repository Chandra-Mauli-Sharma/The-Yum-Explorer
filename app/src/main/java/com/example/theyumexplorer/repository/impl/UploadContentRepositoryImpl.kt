package com.example.theyumexplorer.repository.impl

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.work.Data
import androidx.work.ListenableWorker.Result
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.theyumexplorer.repository.UploadContentRepository
import com.example.theyumexplorer.worker.UploadWorker
import io.appwrite.Client
import io.appwrite.models.InputFile
import io.appwrite.services.Account
import io.appwrite.services.Storage
import java.io.File
import javax.inject.Inject

class UploadContentRepositoryImpl @Inject constructor(
    val context: Context,
    val client: Client,
    private val storage: Storage,
    private val account: Account,
    private val workManager: WorkManager
) : UploadContentRepository {
    override suspend fun UploadImage(image: File) {
        val request =
            OneTimeWorkRequestBuilder<UploadWorker>().setInputData(
                Data.Builder().putString("upload-image", image.toUri().toString()).build()
            ).build()
        workManager.enqueue(request)
    }

    override suspend fun UploadImageUri(uri: Uri): Result {
        storage.createFile(account.get().id, File(uri.path!!).name, InputFile.fromPath(uri.path!!))
        return Result.success()
    }
}