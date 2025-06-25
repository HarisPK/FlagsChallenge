package com.haris.flagschallenge.sealed_classes

sealed class GameState {
    object Initial : GameState()
    data class Countdown(val timeRemaining: Int) : GameState()
    data class InProgress(
        val currentQuestionIndex: Int,
        val timeRemaining: Int,
        val selectedAnswer: Int?,
        val showResult: Boolean
    ) : GameState()
    data class QuestionInterval(val nextQuestionIndex: Int, val timeRemaining: Int) : GameState()
    data class GameOver(val score: Int, val totalQuestions: Int) : GameState()
}