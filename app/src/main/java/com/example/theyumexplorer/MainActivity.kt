package com.example.theyumexplorer

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.theyumexplorer.navigation.TheYumExplorerNavHost
import com.example.theyumexplorer.ui.theme.TheYumExplorerTheme
import com.example.theyumexplorer.view.ExplorerScreen
import com.example.theyumexplorer.view.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import io.appwrite.Client
import io.appwrite.models.User
import io.appwrite.services.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivity = this
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TheYumExplorerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   TheYumExplorerNavHost(modifier = Modifier, navHostController = rememberNavController())
                }
            }
        }
    }

    companion object {
        var mainActivity: MainActivity? = null

        fun getInstance(): MainActivity? = mainActivity
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TheYumExplorerTheme {
        Greeting("Android")
    }
}