package com.haris.flagschallenge.ui_components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
@Composable
fun GameOverScreen(
    score: Int,
    totalQuestions: Int,
    onRestart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Timer box (showing 00:00)
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.Black, RoundedCornerShape(4.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "00:00",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFE65100),
                    RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "FLAGS CHALLENGE",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // Main content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFF0F0F0),
                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                )
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "GAME OVER",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "SCORE: $score/$totalQuestions",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onRestart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE65100)
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        "Play Again",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
