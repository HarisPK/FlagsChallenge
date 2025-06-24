package com.haris.flagschallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haris.flagschallenge.repository.FlagsRepository
import com.haris.flagschallenge.ui.theme.FlagsChallengeTheme
import com.haris.flagschallenge.ui_components.FlagsChallengeApp
import com.haris.flagschallenge.view_models.FlagsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlagsChallengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val repository = FlagsRepository()
                    val viewModel: FlagsViewModel = viewModel {
                        FlagsViewModel(repository)
                    }
                    FlagsChallengeApp(viewModel)
                }
            }
        }
    }
}