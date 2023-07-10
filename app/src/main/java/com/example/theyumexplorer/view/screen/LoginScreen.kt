package com.example.theyumexplorer.view.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.theyumexplorer.R
import com.example.theyumexplorer.navigation.TheYumExplorerScreen
import com.example.theyumexplorer.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.runBlocking

@Composable
fun LoginScreen(
    modifier: Modifier, navController: NavController, viewModel: LoginViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    val token = stringResource(R.string.default_web_client_id)
    val password by viewModel.password.collectAsState()
    val email by viewModel.email.collectAsState()
    var onLoginClicked by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                runBlocking { viewModel.CreateGoogleAccount(account.idToken!!) }
                navController.navigate(TheYumExplorerScreen.MAIN_SCREEN.name)
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
    Scaffold {
        Column(
            modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier
                    .background(color = Color(0xFFFF6B6B), shape = CircleShape)
                    .padding(20.dp)
                    .size(50.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token).requestEmail().build()
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = null,
                        modifier.size(20.dp)
                    )
                    Spacer(modifier = modifier.width(10.dp))
                    Text(text = "SignUp with Google")
                }
                Spacer(modifier = modifier.height(20.dp))
                Text(
                    text = "OR",
                    modifier
                        .background(MaterialTheme.colorScheme.primaryContainer, shape = CircleShape)
                        .padding(10.dp)
                )
                Spacer(modifier = modifier.height(20.dp))
                if ((user == null) && onLoginClicked) Text(
                    text = "Please Sign Up before Login", color = MaterialTheme.colorScheme.error
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = viewModel::OnEmailChanged,
                    label = { Text(text = "Enter Email") },
                    modifier = modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = modifier.height(10.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = viewModel::OnPasswordChanged,
                    label = { Text(text = "Enter Password") },
                    modifier = modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = modifier.height(10.dp))
                Button(onClick = {
                    runBlocking {
                        viewModel.SignInWithEmailAndPassword()
                    }
                    onLoginClicked = true
//                    if (user != null)
//                        navController.navigate(TheYumExplorerScreen.MAIN_SCREEN.name)
                }, shape = RoundedCornerShape(10.dp)) {
                    Text(text = "Login")
                }
                Spacer(modifier = modifier.height(60.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Don't have Account?")
                    TextButton(onClick = { navController.navigate(TheYumExplorerScreen.SIGNUP_SCREEN.name) }) {
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