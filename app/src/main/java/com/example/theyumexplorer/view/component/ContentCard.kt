package com.example.theyumexplorer.view.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.theyumexplorer.model.Content
import com.example.theyumexplorer.model.ContentLocationType
import com.example.theyumexplorer.util.TheYumContent
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExplorerContentCard(content: Content, contentContextMenuEnabled: () -> Unit) {
    var showDescription by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = showDescription, block = {
        if (showDescription) {
            delay(2000)
            showDescription = false
        }
    })
    val haptics = LocalHapticFeedback.current
    Card(
        Modifier
            .padding(20.dp)
            .fillMaxSize()
            .combinedClickable(
                onClick = {
                    contentContextMenuEnabled()
                },
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    contentContextMenuEnabled()
                }
            ),
    ) {
        Box(contentAlignment = Alignment.BottomStart) {
            Image(
                painter = rememberAsyncImagePainter(model = content.contentUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.lighting(Color.DarkGray, Color.Black)
            )
            Column(modifier = Modifier.padding(10.dp)) {

                Column {
                    TextButton(onClick = { showDescription = true }) {
                        Text(
                            text = content.title,
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Black
                            )
                        )
                    }
                    AnimatedVisibility(
                        visible = showDescription,
                        modifier = Modifier.padding(start = 15.dp)
                    ) {
                        Text(
                            text = content.description,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Light,
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileContentCard(content: Content) {
    Card(
        Modifier
            .padding(10.dp)
            .height(200.dp)
            .width(150.dp)
    ) {
        Box(contentAlignment = Alignment.BottomStart) {
            Image(
                painter = rememberAsyncImagePainter(model = content.contentUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.lighting(Color.DarkGray, Color.Black)
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = content.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Black
                    )
                )

                Text(
                    text = content.description,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileContentCardPreview() {
    ProfileContentCard(
        content = Content(
            "fg",
            "acf",
            TheYumContent.Video,
            "fdc",
            "Food",
            "Good Food vjhfvdjhfjdhg njbg nk bnj",
            ContentLocationType.HOME,
            null
        )
    )
}