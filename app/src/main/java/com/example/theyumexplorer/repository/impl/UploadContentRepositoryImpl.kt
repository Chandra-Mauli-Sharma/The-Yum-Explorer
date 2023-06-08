package com.example.theyumexplorer.repository.impl

import android.content.Context
import android.net.Uri
import androidx.work.Data
import androidx.work.ListenableWorker.Result
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.theyumexplorer.model.Content
import com.example.theyumexplorer.repository.UploadContentRepository
import com.example.theyumexplorer.util.TheYumCollections
import com.example.theyumexplorer.worker.UploadWorker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UploadContentRepositoryImpl @Inject constructor(
    val context: Context,
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore,
    private val workManager: WorkManager
) : UploadContentRepository {
    override suspend fun UploadContent(content: Content, currentImage: Uri) {
        var c = db.collection(TheYumCollections.CONTENT.name).add(content).await()

        val request =
            OneTimeWorkRequestBuilder<UploadWorker>().setInputData(
                Data.Builder().putString("upload-image", currentImage.toString())
                    .putString("content-id", c.id).build()
            ).build()
        workManager.enqueue(request)
    }

    override suspend fun UploadImageUri(uri: Uri, contentId: String): Result {
        val task = storage.reference.putFile(uri).await()

        db.collection(TheYumCollections.CONTENT.name).document(contentId)
            .update("contentUrl", task.uploadSessionUri.toString())
        return Result.success()
    }
}