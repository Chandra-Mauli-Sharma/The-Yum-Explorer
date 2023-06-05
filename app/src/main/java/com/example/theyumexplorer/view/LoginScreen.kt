package com.example.theyumexplorer.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.dp
import com.example.theyumexplorer.viewmodel.LoginViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.theyumexplorer.R
import com.example.theyumexplorer.TheYumExplorerApp
import com.example.theyumexplorer.navigation.TheYumExplorerScreen
import kotlinx.coroutines.runBlocking

@Composable
fun LoginScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Scaffold {
        Column(
            modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo), contentDescription = "Logo",
                modifier
                    .background(color = Color(0xFFFF6B6B), shape = CircleShape)
                    .padding(20.dp)
                    .size(50.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = "Enter Email") })
                Box(modifier = modifier.height(10.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = "Enter Password") })
                Box(modifier = modifier.height(10.dp))
                Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(10.dp)) {
                    Text(text = "Login")
                }
                Box(modifier = modifier.height(60.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Don't have Account?")
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Sign Up")
                    }
                }
                TextButton(onClick = {
                    runBlocking {
                        viewModel.createAnonymousSession()
                    }
                    navController.navigate(TheYumExplorerScreen.MAIN_SCREEN.name)
                }) {
                    Text(text = "Skip >>")
                }
            }


        }
    }
}