package com.example.theyumexplorer.view.screen

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.TextSnippet
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.theyumexplorer.navigation.TheYumExplorerScreen
import com.example.theyumexplorer.util.TheYumContent
import com.example.theyumexplorer.view.component.ProfileContentCard
import com.example.theyumexplorer.viewmodel.ProfileViewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val horizontalPagerState = rememberPagerState(
        initialPage = 0,
    ) {
        return@rememberPagerState 3
    }
    val user by viewModel.user.collectAsState()
    val contentList by viewModel.contentList.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val coroutine = rememberCoroutineScope()

    if (!isLoggedIn)
        Column(
            modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("You are not logged in!!")
            Button(onClick = {
                navController.navigate(TheYumExplorerScreen.LOGIN_SCREEN.name)
            }) {
                Text(text = "Login ")
            }
        }
    else if (user != null)
        Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .padding(20.dp)
                ) {
                    Image(
                        painter = if (!user?.photoUrl.isNullOrBlank()) rememberAsyncImagePainter(
                            model = user?.photoUrl
                        ) else rememberAsyncImagePainter(
                            model = ""
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .placeholder(
                                visible = user?.photoUrl.isNullOrBlank(),
                                shape = CircleShape,
                                highlight = PlaceholderHighlight.fade(),
                            )
                            .clip(CircleShape)
                            .size(100.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = user?.name!!,
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "@${user?.uid?.substring(0, 5)}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            TabRow(
                selectedTabIndex = horizontalPagerState.currentPage, indicator = { tabPositions ->
                    tabPositions.forEach { _ ->
                        val transition =
                            updateTransition(horizontalPagerState.currentPage, label = "")
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
            HorizontalPager(
                state = horizontalPagerState,
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = false
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    items(contentList.filter { it.contentType == TheYumContent.values()[horizontalPagerState.currentPage] }) {
                        ProfileContentCard(content = it)
                    }
                }
            }
        }
}