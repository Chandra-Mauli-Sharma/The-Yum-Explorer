package com.example.theyumexplorer.worker

import android.content.Context
import android.net.Uri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.theyumexplorer.repository.UploadContentRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted workerParameters: WorkerParameters,
    private val ioDispatcher: CoroutineDispatcher,
    val repository: UploadContentRepository
) : CoroutineWorker(ctx, workerParameters) {
    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        return@withContext try {
            repository.UploadImageUri(Uri.parse(inputData.getString("upload-image")!!))
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.failure()
        } catch (e: IOException) {
            e.printStackTrace()
            Result.failure()
        }
    }
}