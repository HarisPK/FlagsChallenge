package com.haris.flagschallenge.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haris.flagschallenge.models.Question
import com.haris.flagschallenge.repository.FlagsRepository
import com.haris.flagschallenge.sealed_classes.GameState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class FlagsViewModel(private val repository: FlagsRepository) : ViewModel() {

    private val _gameState = MutableStateFlow<GameState>(GameState.Initial)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private val _scheduledTime = MutableStateFlow<Long?>(null)
    val scheduledTime: StateFlow<Long?> = _scheduledTime.asStateFlow()

    private var questions: List<Question> = emptyList()
    private var currentScore = 0
    private var timerJob: Job? = null

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            repository.getQuestions().collect { questionList ->
                questions = questionList
            }
        }
    }

    fun scheduleChallenge(hours: Int, minutes: Int, seconds: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, minutes)
            set(Calendar.SECOND, seconds)
            set(Calendar.MILLISECOND, 0)

            // If the time is in the past, schedule for tomorrow
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        _scheduledTime.value = calendar.timeInMillis
        startCountdownTimer(calendar.timeInMillis)
    }

    private fun startCountdownTimer(targetTime: Long) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                val currentTime = System.currentTimeMillis()
                val timeRemaining = ((targetTime - currentTime) / 1000).toInt()

                when {
                    timeRemaining <= 0 -> {
                        startChallenge()
                        break
                    }
                    timeRemaining <= 20 -> {
                        _gameState.value = GameState.Countdown(timeRemaining)
                    }
                }
                delay(1000)
            }
        }
    }

    private fun startChallenge() {
        currentScore = 0
        _gameState.value = GameState.InProgress(
            currentQuestionIndex = 0,
            timeRemaining = 30,
            selectedAnswer = null,
            showResult = false
        )
        startQuestionTimer()
    }

    private fun startQuestionTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            val currentState = _gameState.value as? GameState.InProgress ?: return@launch
            var timeRemaining = 30

            while (timeRemaining > 0 && !currentState.showResult) {
                _gameState.value = currentState.copy(timeRemaining = timeRemaining)
                delay(1000)
                timeRemaining--
            }

            // Time's up, show result if not already showing
            if (!currentState.showResult) {
                showQuestionResult()
            }
        }
    }

    fun selectAnswer(answerId: Int) {
        val currentState = _gameState.value as? GameState.InProgress ?: return
        if (currentState.showResult) return

        _gameState.value = currentState.copy(selectedAnswer = answerId)

        // Auto-show result after selection
        viewModelScope.launch {
            delay(1000)
            showQuestionResult()
        }
    }

    private fun showQuestionResult() {
        val currentState = _gameState.value as? GameState.InProgress ?: return
        val currentQuestion = questions[currentState.currentQuestionIndex]

        // Check if answer is correct
        if (currentState.selectedAnswer == currentQuestion.answer_id) {
            currentScore++
        }

        _gameState.value = currentState.copy(showResult = true)

        // Show result for 2 seconds, then proceed
        viewModelScope.launch {
            delay(2000)
            proceedToNextQuestion(currentState.currentQuestionIndex)
        }
    }

    private fun proceedToNextQuestion(currentIndex: Int) {
        val nextIndex = currentIndex + 1

        if (nextIndex >= questions.size) {
            // Game over
            _gameState.value = GameState.GameOver(currentScore, questions.size)
            return
        }

        // Start interval timer
        _gameState.value = GameState.QuestionInterval(nextIndex, 10)
        startIntervalTimer(nextIndex)
    }

    private fun startIntervalTimer(nextIndex: Int) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            var timeRemaining = 10

            while (timeRemaining > 0) {
                _gameState.value = GameState.QuestionInterval(nextIndex, timeRemaining)
                delay(1000)
                timeRemaining--
            }

            // Start next question
            _gameState.value = GameState.InProgress(
                currentQuestionIndex = nextIndex,
                timeRemaining = 30,
                selectedAnswer = null,
                showResult = false
            )
            startQuestionTimer()
        }
    }

    fun resetGame() {
        timerJob?.cancel()
        currentScore = 0
        _gameState.value = GameState.Initial
        _scheduledTime.value = null
    }

    fun getQuestion(index: Int): Question? {
        return questions.getOrNull(index)
    }

    fun getFlagUrl(countryCode: String): String {
        return repository.getFlagUrl(countryCode)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}