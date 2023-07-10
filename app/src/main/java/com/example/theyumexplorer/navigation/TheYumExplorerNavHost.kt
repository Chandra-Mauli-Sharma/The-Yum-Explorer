package com.example.theyumexplorer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.theyumexplorer.view.screen.ContentCaptureScreen
import com.example.theyumexplorer.view.screen.ExplorerScreen
import com.example.theyumexplorer.view.screen.LoginScreen
import com.example.theyumexplorer.view.screen.MainScreen
import com.example.theyumexplorer.view.screen.ProfileScreen
import com.example.theyumexplorer.view.screen.RecipeScreen
import com.example.theyumexplorer.view.screen.SignupScreen
import com.example.theyumexplorer.view.screen.UploadContentScreen

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
                ExplorerScreen(modifier = modifier, navHostController, {})
            }
            composable(TheYumExplorerScreen.PROFILE_SCREEN.name) {
                ProfileScreen(modifier = modifier, navHostController)
            }
            composable(TheYumExplorerScreen.UPLOAD_CONTENT_SCREEN.name) {
                UploadContentScreen(modifier = modifier, navHostController)
            }
            composable(TheYumExplorerScreen.RECIPE_SCREEN.name) {
                RecipeScreen(modifier = modifier, navHostController)
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