package com.haris.flagschallenge.ui_components

import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.haris.flagschallenge.sealed_classes.GameState
import com.haris.flagschallenge.view_models.FlagsViewModel
import androidx.compose.ui.platform.LocalLifecycleOwner

@Composable
fun FlagsChallengeApp(viewModel: FlagsViewModel) {
    val gameState by viewModel.gameState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    // Handle app lifecycle
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    // App moved to background - timer continues in ViewModel
                }
                Lifecycle.Event.ON_RESUME -> {
                    // App resumed - current state is maintained
                }
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when (gameState) {
        is GameState.Initial -> {
            InitialScreen(
                onScheduleChallenge = { hours, minutes, seconds ->
                    viewModel.scheduleChallenge(hours, minutes, seconds)
                }
            )
        }
        is GameState.Countdown -> {
            CountdownScreen(
                countdownTime = (gameState as GameState.Countdown).timeRemaining
            )
        }
        is GameState.InProgress -> {
            GameScreen(
                gameState = gameState as GameState.InProgress,
                viewModel = viewModel
            )
        }
        is GameState.QuestionInterval -> {
            IntervalScreen(
                nextQuestionIndex = (gameState as GameState.QuestionInterval).nextQuestionIndex,
                timeRemaining = (gameState as GameState.QuestionInterval).timeRemaining
            )
        }
        is GameState.GameOver -> {
            GameOverScreen(
                score = (gameState as GameState.GameOver).score,
                totalQuestions = (gameState as GameState.GameOver).totalQuestions,
                onRestart = { viewModel.resetGame() }
            )
        }
    }
}