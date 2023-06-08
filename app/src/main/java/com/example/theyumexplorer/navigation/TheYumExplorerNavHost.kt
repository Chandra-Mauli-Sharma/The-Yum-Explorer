package com.example.theyumexplorer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.theyumexplorer.util.readBool
import com.example.theyumexplorer.view.ContentCaptureScreen
import com.example.theyumexplorer.view.ExplorerScreen
import com.example.theyumexplorer.view.LoginScreen
import com.example.theyumexplorer.view.MainScreen
import com.example.theyumexplorer.view.ProfileScreen
import com.example.theyumexplorer.view.SignupScreen
import com.example.theyumexplorer.view.UploadContentScreen

@Composable
fun TheYumExplorerNavHost(
    modifier: Modifier,
    navHostController: NavHostController,
    isLogged: Boolean
) {

    NavHost(
        navController = navHostController,
        startDestination = if (isLogged) TheYumExplorerScreen.MAIN_SCREEN.name else TheYumExplorerScreen.LOGIN_SCREEN.name,
        builder = {
            composable(TheYumExplorerScreen.LOGIN_SCREEN.name) {
                LoginScreen(modifier = modifier, navHostController)
            }
            composable(TheYumExplorerScreen.MAIN_SCREEN.name) {
                MainScreen(modifier = modifier, navHostController)
            }
            composable(TheYumExplorerScreen.EXPLORER_SCREEN.name) {
                ExplorerScreen(modifier = modifier, navHostController)
            }
            composable(TheYumExplorerScreen.PROFILE_SCREEN.name) {
                ProfileScreen(modifier = modifier, navHostController)
            }
            composable(TheYumExplorerScreen.UPLOAD_CONTENT_SCREEN.name) {
                UploadContentScreen(modifier = modifier, navHostController)
            }
            composable("${TheYumExplorerScreen.IMAGE_CAPTURE_SCREEN.name}/{contentType}") {
                ContentCaptureScreen(
                    modifier = modifier,
                    it.arguments?.getString("contentType")!!,
                    navHostController
                )
            }
            composable(TheYumExplorerScreen.SIGNUP_SCREEN.name) {
                SignupScreen(modifier = modifier, navController = navHostController)
            }
        })
}