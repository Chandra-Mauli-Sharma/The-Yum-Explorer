package com.example.theyumexplorer.view.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.theyumexplorer.R
import com.example.theyumexplorer.model.User
import com.example.theyumexplorer.navigation.TheYumExplorerScreen
import com.example.theyumexplorer.viewmodel.SignupViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.runBlocking

@Composable
fun SignupScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    val token = stringResource(R.string.default_web_client_id)
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                runBlocking {
                    viewModel.CreateGoogleAccount(account.idToken!!)
                }
                navController.navigate(TheYumExplorerScreen.MAIN_SCREEN.name)
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
    val context = LocalContext.current

    val password by viewModel.password.collectAsState()
    val repassword by viewModel.repassword.collectAsState()
    Scaffold {
        Column(
            modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                "Sign Up",
                style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Black),
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
            Text(
                "Create account on \nThe Yum Explorer",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraLight),
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = modifier.height(60.dp))
            Button(onClick = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = null,
                    modifier.size(20.dp)
                )
                Spacer(modifier = modifier.width(10.dp))
                Text(text = "Create with Google")
            }
            Spacer(modifier = modifier.height(25.dp))
            OutlinedTextField(
                value = user?.name ?: "",
                onValueChange = { viewModel.OnUserChanged(user?.copy(name = it) ?: User()) },
                label = { Text(text = "Enter Name") },
                modifier = modifier
                    .padding(horizontal = 40.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = user?.email ?: "",
                onValueChange = { viewModel.OnUserChanged(user?.copy(email = it) ?: User()) },
                label = { Text(text = "Enter Email") },
                modifier = modifier
                    .padding(horizontal = 40.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = viewModel::OnPasswordChanged,
                label = { Text(text = "Enter Password") },
                modifier = modifier
                    .padding(horizontal = 40.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = repassword,
                onValueChange = viewModel::OnRePasswordChanged,
                label = { Text(text = "ReEnter Password") },
                modifier = modifier
                    .padding(horizontal = 40.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = modifier.height(15.dp))
            Button(onClick = {
                runBlocking { viewModel.CreateAccountWithEmailAndPassword() }
                navController.navigate(TheYumExplorerScreen.MAIN_SCREEN.name)
            }) {
                Text(text = "Sign Up")
            }
        }
    }
}