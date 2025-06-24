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
import com.haris.flagschallenge.models.Country

@Composable
fun AnswerOption(
    country: Country,
    isSelected: Boolean,
    isCorrect: Boolean,
    showResult: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        showResult && isCorrect -> Color.Green.copy(alpha = 0.3f)
        showResult && isSelected && !isCorrect -> Color.Red.copy(alpha = 0.3f)
        isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        else -> MaterialTheme.colorScheme.surface
    }

    val borderColor = when {
        showResult && isCorrect -> Color.Green
        showResult && isSelected && !isCorrect -> Color.Red
        isSelected -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.outline
    }

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .border(2.dp, borderColor, RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Text(
                text = country.country_name,
                modifier = Modifier.padding(16.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Result text
        if (showResult) {
            when {
                isCorrect -> {
                    Text(
                        text = "Correct",
                        color = Color.Green,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
                isSelected && !isCorrect -> {
                    Text(
                        text = "Wrong",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }
        }
    }
}