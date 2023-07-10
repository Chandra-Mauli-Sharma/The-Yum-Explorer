package com.example.theyumexplorer.view.screen

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.theyumexplorer.navigation.TheYumExplorerScreen
import com.example.theyumexplorer.util.TheYumContent
import com.example.theyumexplorer.view.component.ExplorerContentCard
import com.example.theyumexplorer.viewmodel.ExplorerViewModel
import kotlinx.coroutines.launch

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExplorerScreen(
    modifier: Modifier,
    navController: NavController,
    route: (TheYumExplorerScreen) -> Unit,
    viewModel: ExplorerViewModel = hiltViewModel()
) {

    val contentList by viewModel.contentList.collectAsState()
    val user by viewModel.user.collectAsState()

    // Fetching the Local Context
    val mContext = LocalContext.current

    val coroutine = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = 0, initialPageOffsetFraction = 0f
    ) {
        return@rememberPagerState contentList.size
    }

    val modalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it == SheetValue.Expanded },
    )
    val bottomSheetState = rememberBottomSheetScaffoldState(bottomSheetState = modalSheetState)

    BottomSheetScaffold(sheetContent = {
        var openComment by remember { mutableStateOf(false) }
        Box(Modifier.fillMaxHeight()) {
            Column {
                ListItem(
                    headlineContent = { Text(text = "Description") },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null
                        )
                    },
                )
                ListItem(
                    headlineContent = { Text(text = "Comments") },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Comment,
                            contentDescription = null
                        )
                    },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.clickable { openComment = true }
                )
            }
            this@BottomSheetScaffold.AnimatedVisibility(
                visible = openComment,
                enter = slideInHorizontally(),
                exit = slideOutHorizontally(),
            ) {
                Surface {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(500) {
                            Text(it.toString())
                        }
                    }
                }
            }
        }
    }, sheetDragHandle = {
        Column {
            BottomSheetDefaults.DragHandle()
            Text(
                text = "Hey",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }, scaffoldState = bottomSheetState, sheetPeekHeight = 0.dp) {

        Column(modifier) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier
                        .padding(20.dp)
                        .background(Color.Transparent),
                    text = "Explore Foods",
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Black)
                )
                Image(painter = rememberAsyncImagePainter(model = user?.photoUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { route(TheYumExplorerScreen.PROFILE_SCREEN) })
            }
            if (contentList.isNotEmpty()) HorizontalPager(
                state = pagerState,
                flingBehavior = PagerDefaults.flingBehavior(
                    state = pagerState,
                    lowVelocityAnimationSpec = SpringSpec(
                        Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessHigh,
                        visibilityThreshold = 10f
                    ),
                    snapAnimationSpec = SpringSpec(
                        Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessHigh,
                        visibilityThreshold = 10f
                    ),
                ),
            ) {
                contentList[it].also {
                    when (it.contentType) {
                        TheYumContent.Video -> Card(
                            Modifier
                                .padding(20.dp)
                                .fillMaxSize(),
                        ) {
                            val mExoPlayer = remember(mContext) {
                                ExoPlayer.Builder(mContext).setMediaSourceFactory(
                                    DefaultMediaSourceFactory(mContext).setLiveTargetOffsetMs(
                                        5000
                                    )
                                ).build().apply {
                                    val mediaItem = MediaItem.Builder()
                                        .setUri(Uri.parse("https://cloudappwrite.io/v1/storage/buckets/6478a1b79d7d3adc4e5b/files/6478ad85790013c455d1/view?project=6477036f1de27ad8b0ea&mode=admin"))
                                        .setLiveConfiguration(
                                            MediaItem.LiveConfiguration.Builder().build()
                                        ).build()
                                    setMediaItem(mediaItem)
                                    prepare()
                                    play()
                                }
                            }

                            AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
                                PlayerView(context).apply {
                                    player = mExoPlayer
                                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                                }
                            })
                        }

                        TheYumContent.Image -> ExplorerContentCard(
                            content = it
                        ) {
                            coroutine.launch {
                                bottomSheetState.bottomSheetState.show()
                            }
                        }

                        else -> Card(
                            Modifier
                                .padding(20.dp)
                                .fillMaxSize(),
                        ) {}
                    }
                }
            }
        }
    }

}