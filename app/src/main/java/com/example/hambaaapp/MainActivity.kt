package com.example.hambaaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hambaaapp.ui.screens.HambaaAppRoot
import com.example.hambaaapp.ui.theme.HambaaAppTheme
import com.example.hambaaapp.viewmodel.HambaaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: HambaaViewModel = viewModel()
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()
            HambaaAppTheme(darkTheme = isDarkTheme) {
                HambaaAppRoot(viewModel = viewModel)
            }
        }
    }
}
