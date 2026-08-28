package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.data.local.UserSessionManager
import com.example.ui.LoginScreen
import com.example.ui.navigation.MainAppNavigation
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.SindhiAssistantViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: SindhiAssistantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sessionManager = UserSessionManager(this)

        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var isLoggedIn by androidx.compose.runtime.remember {
                        androidx.compose.runtime.mutableStateOf(sessionManager.isLoggedIn())
                    }

                    if (!isLoggedIn) {
                        LoginScreen(sessionManager = sessionManager) {
                            isLoggedIn = true
                        }
                    } else {
                        MainAppNavigation(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
