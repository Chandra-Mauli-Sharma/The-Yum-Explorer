package com.example.theyumexplorer.view.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.theyumexplorer.viewmodel.RecipeViewModel
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    modifier: Modifier, navController: NavController, viewModel: RecipeViewModel = hiltViewModel()
) {
    val recipeList by viewModel.recipeListResponse.collectAsState()
    val recipe by viewModel.recipe.collectAsState()
    val query by viewModel.query.collectAsState()
    val configuration = LocalConfiguration.current
    var openDetail by remember { mutableStateOf(false) }
    Box(
        modifier = modifier,
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = query,
                onValueChange = viewModel::onQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                trailingIcon = {
                    IconButton(onClick = { viewModel.getRecipeList() }) {
                        Icon(
                            imageVector = Icons.Default.Search, contentDescription = null
                        )
                    }
                },
                placeholder = { Text(text = "Search Recipe") })
            if (recipeList.isNullOrEmpty()) {
                CircularProgressIndicator()
            }
            LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
                items(recipeList ?: listOf()) {
                    val rnd = Random
                    val color: Int = android.graphics.Color.argb(
                        255,
                        rnd.nextInt(256),
                        rnd.nextInt(256),
                        rnd.nextInt(256)
                    )
                    Card(
                        onClick = {
                            runBlocking {
                                viewModel.onRecipeChanged(it)
                            }
                            openDetail = true
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = Color(color),
                            contentColor = MaterialTheme.colorScheme.contentColorFor(Color(color))
                        ),
                        modifier = Modifier
                            .height(
                                (it.images.thumbnail.height
                                    .toDouble()
                                    .times((configuration.screenWidthDp.toDouble() / (2.0 * it.images.thumbnail.width.toDouble())))).toInt().dp
                            )
                            .padding(5.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = rememberAsyncImagePainter(model = it.images.thumbnail.url),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.lighting(Color.DarkGray, Color.Black)
                            )
                            Text(
                                text = it.label,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = openDetail,
        ) {
            Column(
                Modifier
                    .background(Color.White)
                    .fillMaxSize()
            ) {
                Log.d("Heyd", recipe?.images?.thumbnail?.url.toString())
                Text(text = recipe?.label!!)
                Image(
                    painter = rememberAsyncImagePainter(model = recipe?.images?.thumbnail?.url),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.lighting(Color.DarkGray, Color.Black)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeScreenPreview() {
    RecipeScreen(modifier = Modifier, navController = rememberNavController())
}