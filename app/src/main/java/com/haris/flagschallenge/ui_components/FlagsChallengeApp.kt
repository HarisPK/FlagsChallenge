package com.haris.flagschallenge.ui_components

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.haris.flagschallenge.sealed_classes.GameState
import com.haris.flagschallenge.view_models.FlagsViewModel

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
                onScheduleChallenge = { timeInMillis ->
                    viewModel.scheduleChallenge(timeInMillis)
                }
            )
        }
        is GameState.Countdown -> {
            CountdownScreen(
                countdownTime = viewModel.countdownTime.collectAsState().value
            )
        }
        is GameState.InProgress -> {
            GameScreen(
                gameState = gameState as GameState.InProgress,
                viewModel = viewModel
            )
        }
        is GameState.QuestionInterval -> {
            val intervalState = gameState as GameState.QuestionInterval
            IntervalScreen(
                nextQuestionIndex = intervalState.nextQuestionIndex,
                timeRemaining = intervalState.timeRemaining
            )
        }
        is GameState.GameOver -> {
            val gameOverState = gameState as GameState.GameOver
            GameOverScreen(
                score = gameOverState.score,
                totalQuestions = gameOverState.totalQuestions,
                onRestart = { viewModel.resetGame() }
            )
        }
    }
}