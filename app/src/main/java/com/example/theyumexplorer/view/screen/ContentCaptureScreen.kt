package com.example.theyumexplorer.view.screen

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import androidx.navigation.NavController
import com.example.theyumexplorer.util.TheYumContent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.util.Locale
import java.util.concurrent.Executor

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContentCaptureScreen(
    modifier: Modifier,
    contentType: String,
    navController: NavController,
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    var currentCapturedImage by remember {
        mutableStateOf<Uri?>(null)
    }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val micPermissionState = rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)
    var imageCapture by remember {
        mutableStateOf<ImageCapture?>(null)
    }
    var executor by remember {
        mutableStateOf<Executor?>(null)
    }
    val coroutine = rememberCoroutineScope()
    if (!cameraPermissionState.status.isGranted) {
        LaunchedEffect(key1 = Unit, block = {
            cameraPermissionState.launchPermissionRequest()
        })
    } else {
        Scaffold {
            Column(
                modifier
                    .padding(it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(visible = currentCapturedImage != null) {
                    if (currentCapturedImage != null)
                        Box(
                            modifier = modifier
                                .padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                bitmap = BitmapFactory.decodeFile(currentCapturedImage?.path)
                                    .asImageBitmap(), contentDescription = null
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                IconButton(
                                    onClick = { currentCapturedImage = null },
                                    colors = IconButtonDefaults.filledIconButtonColors(
                                        containerColor = Color.White,
                                        contentColor = contentColorFor(
                                            backgroundColor = Color.White
                                        )
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        navController.previousBackStackEntry?.savedStateHandle?.set(
                                            "upload-image-uri",
                                            currentCapturedImage.toString()
                                        )
                                        navController.popBackStack()
                                    },
                                    colors = IconButtonDefaults.filledIconButtonColors(
                                        containerColor = Color.White,
                                        contentColor = contentColorFor(
                                            backgroundColor = Color.White
                                        )
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Done,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                }
                AndroidView(
                    factory = { ctx ->
                        val previewView = PreviewView(ctx)
                        executor = ContextCompat.getMainExecutor(ctx)
                        cameraProviderFuture.addListener({
                            val cameraProvider = cameraProviderFuture.get()
                            val preview = Preview.Builder().build().also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }

                            val cameraSelector = CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build()

                            if (contentType == TheYumContent.Image.name) {
                                imageCapture = ImageCapture.Builder().build()
                                cameraProvider.unbindAll()
                                cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    cameraSelector,
                                    imageCapture,
                                    preview
                                )
                            } else {
                                val recorder = Recorder.Builder()
                                    .setExecutor(executor!!)
                                    .build()
                                val videoCapture = VideoCapture.withOutput(recorder)

                                val name = "CameraX-recording-" +
                                        SimpleDateFormat("ddMMyyyy", Locale.US)
                                            .format(System.currentTimeMillis()) + ".mp4"
                                val contentValues = ContentValues().apply {
                                    put(MediaStore.Video.Media.DISPLAY_NAME, name)
                                }
                                val mediaStoreOutput = MediaStoreOutputOptions.Builder(
                                    context.contentResolver,
                                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                                )
                                    .setContentValues(contentValues)
                                    .build()

                                val recordingListener = Consumer<VideoRecordEvent> { event ->
                                    when (event) {
                                        is VideoRecordEvent.Start -> {
                                            val msg = "Capture Started"
                                            Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                                                .show()

                                        }

                                        is VideoRecordEvent.Finalize -> {
                                            val msg = if (!event.hasError()) {

                                                "Video capture succeeded: ${event.outputResults.outputUri}"

                                            } else {


                                                "Video capture ends with error: ${event.error}"
                                            }
                                            Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    }
                                }

                                if (micPermissionState.status.isGranted && ActivityCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.RECORD_AUDIO
                                    ) == PackageManager.PERMISSION_GRANTED
                                ) {
                                    val recording = videoCapture.output
                                        .prepareRecording(context, mediaStoreOutput)
                                        .withAudioEnabled()
                                        .start(executor!!, recordingListener)
                                } else {
                                    micPermissionState.launchPermissionRequest()
                                }

                                cameraProvider.unbindAll()
                                cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    cameraSelector,
                                    videoCapture,
                                    preview
                                )
                            }

                        }, executor!!)
                        previewView
                    },
                    update = {

                    },
                    modifier = modifier
                        .padding(20.dp)
                        .height(LocalConfiguration.current.screenHeightDp.times(0.85).dp)
                        .clip(RoundedCornerShape(20.dp)),
                )
                IconButton(
                    onClick = {
                        if (contentType == TheYumContent.Image.name) {
                            val outputFileOptions =
                                ImageCapture.OutputFileOptions.Builder(File(context.externalCacheDir!!.path + File.separator + System.currentTimeMillis() + ".png"))
                                    .build()
                            imageCapture?.takePicture(outputFileOptions, executor!!,
                                object : ImageCapture.OnImageSavedCallback {
                                    override fun onError(error: ImageCaptureException) {
                                        Log.d("Hey", error.toString())
                                    }

                                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                        currentCapturedImage = outputFileResults.savedUri
                                    }
                                })
                        }
                    },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primaryContainer)
                    ), modifier = modifier
                        .size(60.dp)
                        .clip(CircleShape)
                ) {
                    Icon(imageVector = Icons.Default.Camera, contentDescription = null)
                }
            }
        }
    }
}