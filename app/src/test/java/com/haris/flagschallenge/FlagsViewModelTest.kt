package com.haris.flagschallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.haris.flagschallenge.models.Country
import com.haris.flagschallenge.models.Question
import com.haris.flagschallenge.repository.FlagsRepository
import com.haris.flagschallenge.sealed_classes.GameState
import com.haris.flagschallenge.view_models.FlagsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.ArgumentMatchers.anyString
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeout

@OptIn(ExperimentalCoroutinesApi::class)
class FlagsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: FlagsRepository

    private lateinit var viewModel: FlagsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        val mockQuestions = listOf(
            Question(
                answer_id = 160,
                countries = listOf(
                    Country("New Zealand", 160),
                    Country("Chile", 45),
                    Country("Bosnia", 29),
                    Country("Mauritania", 142)
                ),
                country_code = "NZ"
            )
        )

        `when`(repository.getQuestions()).thenReturn(flow { emit(mockQuestions) })
        `when`(repository.getFlagUrl(anyString())).thenReturn("https://test-flag-url.com")

        viewModel = FlagsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Initial`() {
        assert(viewModel.gameState.value is GameState.Initial)
    }

    @Test
    fun `scheduleChallenge should set scheduled time`() = runTest {
        val targetTime = System.currentTimeMillis() + 30000 // 30 seconds from now

        viewModel.scheduleChallenge(targetTime)

        assert(viewModel.scheduledTime.value == targetTime)
    }

    @Test
    fun `selectAnswer should update selected answer in game state`() = runTest {
        // Start a challenge first
        viewModel.scheduleChallenge(System.currentTimeMillis() + 1000)
        advanceTimeBy(1000)

        // Wait for GameState.InProgress
        val gameState = withTimeout(1000) {
            viewModel.gameState
                .first { it is GameState.InProgress } as GameState.InProgress
        }

        // Select an answer
        viewModel.selectAnswer(160)

        val updatedState = viewModel.gameState.value as? GameState.InProgress
        assert(updatedState?.selectedAnswer == 160)
    }

    @Test
    fun `resetGame should return to initial state`() = runTest {
        // Start and then reset
        viewModel.scheduleChallenge(System.currentTimeMillis() + 1000)
        viewModel.resetGame()

        assert(viewModel.gameState.value is GameState.Initial)
        assert(viewModel.scheduledTime.value == null)
    }
}