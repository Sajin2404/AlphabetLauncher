package com.sajin.alphabetlauncher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.sajin.alphabetlauncher.ui.LauncherScreen
import com.sajin.alphabetlauncher.ui.ui.theme.AlphabetLauncherTheme

class MainActivity : ComponentActivity() {

    private val viewModel: LauncherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AlphabetLauncherTheme {
                LauncherScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}