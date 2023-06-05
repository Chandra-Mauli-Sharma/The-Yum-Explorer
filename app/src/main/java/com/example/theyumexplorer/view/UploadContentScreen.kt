package com.example.theyumexplorer.view

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.BrowseGallery
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.TextSnippet
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.theyumexplorer.navigation.TheYumExplorerScreen
import com.example.theyumexplorer.util.deleteString
import com.example.theyumexplorer.util.readString
import com.example.theyumexplorer.viewmodel.UploadContentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadContentScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: UploadContentViewModel = hiltViewModel()
) {
    val contentType = remember {
        mutableStateMapOf<String, Boolean>("Video" to true, "Image" to false, "Blog" to false)
    }
    var fileChooser by remember {
        mutableStateOf(false)
    }
    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {

        }

    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    val currentImage by context.readString("upload-image-uri").collectAsState("")

    var showDeleteButton by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = showDeleteButton, block = {
        if (showDeleteButton) {
            delay(2000)
            showDeleteButton = false
        }
    })
    if (fileChooser)
        Dialog(onDismissRequest = { fileChooser = false }) {
            Surface(
                modifier
                    .wrapContentHeight()
                    .fillMaxWidth(), shape = MaterialTheme.shapes.large
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        onClick = {
                            navController.navigate(
                                "${TheYumExplorerScreen.IMAGE_CAPTURE_SCREEN.name}/${
                                    contentType.filterNot { !(it.value) }.toList().first().first
                                }"
                            )
                        },
                        modifier = modifier.padding(10.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = modifier.padding(20.dp)
                        ) {
                            Icon(Icons.Default.Camera, contentDescription = null)
                            Text(text = "Open Camera")
                        }
                    }
                    Card(
                        onClick = { galleryLauncher.launch("video/*") },
                        modifier = modifier.padding(10.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = modifier.padding(20.dp)
                        ) {
                            Icon(Icons.Default.BrowseGallery, contentDescription = null)
                            Text(text = "Open Gallery")
                        }
                    }
                }
            }
        }
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                "Upload Content",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }, actions = {
            TextButton(onClick = { /*TODO*/ }, enabled = title.isNotBlank()) {
                Text(text = "Upload")
            }
        })
    }) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(20.dp)
        ) {
            Text(
                text = "Content Type",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Box(modifier = modifier.padding(5.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                contentType.forEach {
                    FilterChip(
                        selected = it.value,
                        onClick = {
                            contentType.forEach { contentMap ->
                                contentType[contentMap.key] = contentMap.key == it.key
                            }
                        },
                        label = { Text(text = it.key) },
                        leadingIcon = {
                            Icon(
                                imageVector = when (it.key) {
                                    "Video" -> Icons.Default.VideoLibrary
                                    "Image" -> Icons.Default.Image
                                    else -> Icons.Default.TextSnippet
                                },
                                contentDescription = null
                            )
                        })
                }

            }
            Box(modifier = modifier.padding(5.dp))
            AnimatedVisibility(visible = contentType["Video"]?.or(contentType["Image"]!!)!!) {
                Column {
                    if (currentImage.isBlank())
                        Surface(
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                            shape = RoundedCornerShape(20.dp),
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .fillMaxHeight(0.1f),
                            onClick = {
                                fileChooser = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Upload,
                                contentDescription = null,
                                modifier = modifier.padding(20.dp)
                            )

                        }
                    else
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                bitmap = BitmapFactory.decodeFile(Uri.parse(currentImage).path)
                                    .asImageBitmap(),
                                contentDescription = null,
                                modifier = modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .fillMaxWidth()
                                    .clickable {
                                        showDeleteButton = true
                                    }
                            )
                            this@Column.AnimatedVisibility(
                                visible = showDeleteButton,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                IconButton(
                                    onClick = {
                                        coroutine.launch(Dispatchers.IO) {
                                            context.deleteString("upload-image-uri")
                                        }
                                    },
                                    colors = IconButtonDefaults.filledIconButtonColors(
                                        containerColor = Color.White,
                                        contentColor = contentColorFor(
                                            backgroundColor = Color.White
                                        )
                                    ),
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    Spacer(modifier = modifier.height(5.dp))
                    Text(
                        text = "Content Details",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        modifier = modifier.padding(vertical = 5.dp)
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = viewModel::onTitleChanged,
                        label = { Text(text = "Enter Title") },
                        placeholder = { Text(text = "Enter Title") },
                        modifier = modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = viewModel::onDescriptionChanged,
                        label = { Text(text = "Enter Description") },
                        placeholder = { Text(text = "Enter Description") },
                        supportingText = { Text(text = "${description.length}/500") },
                        minLines = 4,
                        isError = description.length > 500,
                        modifier = modifier.fillMaxSize()
                    )
                }
            }
        }
    }

}