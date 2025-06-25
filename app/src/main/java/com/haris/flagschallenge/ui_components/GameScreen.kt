package com.haris.flagschallenge.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.haris.flagschallenge.sealed_classes.GameState
import com.haris.flagschallenge.view_models.FlagsViewModel
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

@Composable
fun GameScreen(
    gameState: GameState.InProgress,
    viewModel: FlagsViewModel
) {
    val question = viewModel.getQuestion(gameState.currentQuestionIndex)

    if (question == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {
        GameHeader("00:${gameState.timeRemaining.toString().padStart(2, '0')}")

        Spacer(modifier = Modifier.height(6.dp).background(color = Color(0xFFD0CACA)))

        // Main content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFF0F0F0),
                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Flag image on the left
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = viewModel.getFlagUrl(question.country_code),
                        contentDescription = "Flag",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Fit
                    )
                }

                // 2x2 grid of answer options on the right
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .weight(1f)
                        .height(128.dp), // Adjust height as needed
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    userScrollEnabled = false
                ) {
                    items(question.countries) { country ->
                        AnswerOption(
                            country = country,
                            isSelected = gameState.selectedAnswer == country.id,
                            isCorrect = if (gameState.showResult) country.id == question.answer_id else null,
                            isWrong = gameState.showResult && gameState.selectedAnswer == country.id && country.id != question.answer_id,
                            onClick = {
                                if (!gameState.showResult) {
                                    viewModel.selectAnswer(country.id)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}