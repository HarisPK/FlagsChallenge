package com.haris.flagschallenge.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameHeader(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                Color(0xFFE65100),
                RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {}
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.Black, RoundedCornerShape(4.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = "FLAGS CHALLENGE",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
    }
}