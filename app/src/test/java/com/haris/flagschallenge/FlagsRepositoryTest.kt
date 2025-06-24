package com.haris.flagschallenge

import com.haris.flagschallenge.repository.FlagsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FlagsRepositoryTest {

    private lateinit var repository: FlagsRepository

    @Before
    fun setup() {
        repository = FlagsRepository()
    }

    @Test
    fun `getQuestions should return list of questions`() = runTest {
        val questions = repository.getQuestions().first()

        assert(questions.isNotEmpty())
        assert(questions.size == 15)
        assert(questions.first().countries.size == 4)
    }

    @Test
    fun `getFlagUrl should return correct flag URL format`() {
        val url = repository.getFlagUrl("US")

        assert(url == "https://flagcdn.com/w320/us.png")
    }

    @Test
    fun `getFlagUrl should handle lowercase conversion`() {
        val url = repository.getFlagUrl("NZ")

        assert(url == "https://flagcdn.com/w320/nz.png")
    }
}