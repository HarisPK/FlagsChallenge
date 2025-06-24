package com.haris.flagschallenge.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.haris.flagschallenge.sealed_classes.GameState
import com.haris.flagschallenge.view_models.FlagsViewModel

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
            .padding(16.dp)
    ) {
        // Top bar with question number and timer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Question ${gameState.currentQuestionIndex + 1}/15",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${gameState.timeRemaining}s",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (gameState.timeRemaining <= 10) Color.Red else MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Question title
        Text(
            text = "Guess the Country by the Flag",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Flag image
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            AsyncImage(
                model = viewModel.getFlagUrl(question.country_code),
                contentDescription = "Flag",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Answer options
        question.countries.forEach { country ->
            AnswerOption(
                country = country,
                isSelected = gameState.selectedAnswer == country.id,
                isCorrect = country.id == question.answer_id,
                showResult = gameState.showResult,
                onClick = {
                    if (!gameState.showResult) {
                        viewModel.selectAnswer(country.id)
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}