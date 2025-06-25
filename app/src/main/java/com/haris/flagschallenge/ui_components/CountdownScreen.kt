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
fun CountdownScreen(countdownTime: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        GameHeader("00:${countdownTime.toString().padStart(2, '0')}")
        Spacer(modifier = Modifier.height(6.dp).background(color = Color(0xFFD0CACA)))

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
                    text = "CHALLENGE",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "WILL START IN",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "00:${countdownTime.toString().padStart(2, '0')}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }
    }
}
