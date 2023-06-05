package com.example.theyumexplorer.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.Person3
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.example.theyumexplorer.navigation.TheYumExplorerScreen

@Composable
fun MainScreen(modifier: Modifier, navController: NavController) {
    var route by remember {
        mutableStateOf<TheYumExplorerScreen>(TheYumExplorerScreen.EXPLORER_SCREEN)
    }
    Scaffold(bottomBar = {
        NavigationBar {
            NavigationBarItem(
                selected = route == TheYumExplorerScreen.EXPLORER_SCREEN,
                onClick = { route = TheYumExplorerScreen.EXPLORER_SCREEN },
                icon = { Icon(Icons.Rounded.LocalFireDepartment, contentDescription = null) })
            NavigationBarItem(
                selected = false,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Rounded.MenuBook, contentDescription = null) })
            NavigationBarItem(
                selected = false,
                onClick = { /*TODO*/ },
                icon = {
                    FloatingActionButton(onClick = { navController.navigate(TheYumExplorerScreen.UPLOAD_CONTENT_SCREEN.name) }) {
                        Icon(Icons.Rounded.Add, contentDescription = null)
                    }
                })
            NavigationBarItem(
                selected = false,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Rounded.Map, contentDescription = null) })
            NavigationBarItem(
                selected = route == TheYumExplorerScreen.PROFILE_SCREEN,
                onClick = {
                    route = TheYumExplorerScreen.PROFILE_SCREEN

                },
                icon = { Icon(Icons.Rounded.Person3, contentDescription = null) },
            )
        }
    }) {
        when (route) {
            TheYumExplorerScreen.EXPLORER_SCREEN -> ExplorerScreen(
                modifier = modifier.padding(it),
                navController
            )

            else -> ProfileScreen(modifier.padding(it), navController)
        }
    }
}