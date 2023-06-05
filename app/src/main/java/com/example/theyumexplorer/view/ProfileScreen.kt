package com.example.theyumexplorer.view

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.TextSnippet
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.theyumexplorer.R
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(modifier: Modifier, navController: NavController) {
    val horizontalPagerState = rememberPagerState(
        initialPage = 0,
    ) {
        return@rememberPagerState 3
    }

    val coroutine = rememberCoroutineScope()
    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .placeholder(
                    visible = true,
                    shape = CircleShape,
                    highlight = PlaceholderHighlight.fade(),
                )
                .size(200.dp)
        )
        Text(text = "User Name", style = MaterialTheme.typography.headlineLarge)
        Text(text = "@user_name", style = MaterialTheme.typography.bodyLarge)

        TabRow(
            selectedTabIndex = horizontalPagerState.currentPage, indicator = { tabPositions ->
                tabPositions.forEach { _ ->
                    val transition = updateTransition(horizontalPagerState.currentPage, label = "")
                    val indicatorStart by transition.animateDp(
                        transitionSpec = {
                            if (initialState < targetState) {
                                spring(dampingRatio = 0f, stiffness = 50f)
                            } else {
                                spring(dampingRatio = 0f, stiffness = 1000f)
                            }
                        }, label = ""
                    ) {
                        tabPositions[it].left
                    }

                    val indicatorEnd by transition.animateDp(
                        transitionSpec = {
                            if (initialState < targetState) {
                                spring(dampingRatio = 0f, stiffness = 1000f)
                            } else {
                                spring(dampingRatio = 0f, stiffness = 50f)
                            }
                        }, label = ""
                    ) {
                        tabPositions[it].right
                    }

                    Box(
                        Modifier
                            .offset(x = indicatorStart)
                            .wrapContentSize(align = Alignment.BottomStart)
                            .width(indicatorEnd - indicatorStart)
                            .padding(5.dp)
                            .fillMaxSize()
                            .background(
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                RoundedCornerShape(50)
                            )
                            .zIndex(1f)
                    )
                }
            }, divider = {}) {
            listOf(
                Icons.Default.VideoLibrary,
                Icons.Default.Image,
                Icons.Default.TextSnippet
            ).forEachIndexed { index, imageVector ->
                Tab(
                    selected = horizontalPagerState.currentPage == index,
                    onClick = {
                        coroutine.launch {
                            horizontalPagerState.animateScrollToPage(
                                index
                            )
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = imageVector,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.zIndex(6f)
                )
            }
        }
        HorizontalPager(state = horizontalPagerState, modifier = Modifier.fillMaxSize(), userScrollEnabled = false) {
            LazyVerticalGrid(columns = GridCells.Fixed(2),Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly){
                items(5){
                    Card(Modifier.size(50.dp).padding(10.dp).placeholder(true)) {

                    }
                }
            }
        }
    }
}