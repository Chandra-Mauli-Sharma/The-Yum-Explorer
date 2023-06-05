package com.example.theyumexplorer.view

import android.net.Uri
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.Person3
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.theyumexplorer.R
import com.example.theyumexplorer.navigation.TheYumExplorerScreen

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExplorerScreen(modifier: Modifier, navController: NavController) {
    val pagerState = rememberPagerState(
        initialPage = 0, initialPageOffsetFraction = 0f
    ) {
        return@rememberPagerState 10
    }

    Column(modifier) {
        Text(
            modifier = Modifier
                .padding(20.dp)
                .background(Color.Transparent),
            text = "Explore Foods",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Black)
        )
        HorizontalPager(
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
            // Fetching the Local Context
            val mContext = LocalContext.current
            Card(
                Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
            ) {
                val mExoPlayer = remember(mContext) {
                    ExoPlayer.Builder(mContext).setMediaSourceFactory(DefaultMediaSourceFactory(mContext).setLiveTargetOffsetMs(5000)).build().apply {
                        val mediaItem =
                            MediaItem.Builder()
                                .setUri(Uri.parse("https://cloudappwrite.io/v1/storage/buckets/6478a1b79d7d3adc4e5b/files/6478ad85790013c455d1/view?project=6477036f1de27ad8b0ea&mode=admin"))
                                .setLiveConfiguration(
                                    MediaItem.LiveConfiguration.Builder().build()
                                )
                                .build()
                        setMediaItem(mediaItem)
                        prepare()
                        play()
                    }
                }

                AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
                    PlayerView(context).apply {
                        player = mExoPlayer
                        resizeMode= AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    }
                })
            }
        }
    }

}