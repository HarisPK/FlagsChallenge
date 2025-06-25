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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haris.flagschallenge.models.Country

@Composable
fun AnswerOption(
    country: Country,
    isSelected: Boolean,
    isCorrect: Boolean?,
    isWrong: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        isCorrect == true -> Color(0xFF4CAF50) // Green for correct
        isWrong -> Color(0xFFF44336) // Red for wrong selection
        isSelected -> Color(0xFFE65100) // Orange for selected
        else -> Color.White
    }

    val textColor = when {
        isCorrect == true || isWrong || isSelected -> Color.White
        else -> Color.Black
    }

    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(backgroundColor, RoundedCornerShape(4.dp))
                .border(
                    1.dp,
                    if (backgroundColor == Color.White) Color.Gray else backgroundColor,
                    RoundedCornerShape(4.dp)
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = country.country_name,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        // Show result text
        if (isCorrect == true) {
            Text(
                text = "Correct",
                color = Color(0xFF4CAF50),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )
        } else if (isWrong) {
            Text(
                text = "Wrong",
                color = Color(0xFFF44336),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}