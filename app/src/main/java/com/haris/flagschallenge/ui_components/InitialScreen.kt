package com.haris.flagschallenge.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialScreen(
    onScheduleChallenge: (Int, Int, Int) -> Unit
) {
    var hourFirst by remember { mutableStateOf("0") }
    var hourSecond by remember { mutableStateOf("0") }
    var minuteFirst by remember { mutableStateOf("0") }
    var minuteSecond by remember { mutableStateOf("0") }
    var secondFirst by remember { mutableStateOf("0") }
    var secondSecond by remember { mutableStateOf("0") }

    // FocusRequesters for each field
    val focusRequesters = List(6) { remember { FocusRequester() } }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    Color(0xFFE65100),
                    RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {}

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFF0F0F0),
                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                )
                .padding(32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "FLAGS CHALLENGE",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "SCHEDULE",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Hour
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Hour", fontSize = 14.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            TimeDigitField(
                                digit = hourFirst,
                                onDigitChange = {
                                    hourFirst = it
                                    if (it.isNotEmpty()) focusRequesters[1].requestFocus()
                                },
                                modifier = Modifier.focusRequester(focusRequesters[0])
                            )
                            TimeDigitField(
                                digit = hourSecond,
                                onDigitChange = {
                                    hourSecond = it
                                    if (it.isNotEmpty()) focusRequesters[2].requestFocus()
                                },
                                modifier = Modifier.focusRequester(focusRequesters[1])
                            )
                        }
                    }

                    Text(":", fontSize = 24.sp, fontWeight = FontWeight.Bold)

                    // Minute
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Minute", fontSize = 14.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            TimeDigitField(
                                digit = minuteFirst,
                                onDigitChange = {
                                    minuteFirst = it
                                    if (it.isNotEmpty()) focusRequesters[3].requestFocus()
                                },
                                modifier = Modifier.focusRequester(focusRequesters[2])
                            )
                            TimeDigitField(
                                digit = minuteSecond,
                                onDigitChange = {
                                    minuteSecond = it
                                    if (it.isNotEmpty()) focusRequesters[4].requestFocus()
                                },
                                modifier = Modifier.focusRequester(focusRequesters[3])
                            )
                        }
                    }

                    Text(":", fontSize = 24.sp, fontWeight = FontWeight.Bold)

                    // Second
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Second", fontSize = 14.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            TimeDigitField(
                                digit = secondFirst,
                                onDigitChange = {
                                    secondFirst = it
                                    if (it.isNotEmpty()) focusRequesters[5].requestFocus()
                                },
                                modifier = Modifier.focusRequester(focusRequesters[4])
                            )
                            TimeDigitField(
                                digit = secondSecond,
                                onDigitChange = {
                                    secondSecond = it
                                    // Last field, clear focus if needed
                                    if (it.isNotEmpty()) focusManager.clearFocus()
                                },
                                modifier = Modifier.focusRequester(focusRequesters[5])
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        val hour = (hourFirst + hourSecond).toIntOrNull()?.coerceIn(0, 23) ?: 0
                        val minute = (minuteFirst + minuteSecond).toIntOrNull()?.coerceIn(0, 59) ?: 0
                        val second = (secondFirst + secondSecond).toIntOrNull()?.coerceIn(0, 59) ?: 0
                        onScheduleChallenge(hour, minute, second)
                    },
                    modifier = Modifier
                        .width(120.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE65100)
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        "Save",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDigitField(
    digit: String,
    onDigitChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = digit,
        onValueChange = { input ->
            // Always replace with the last digit typed, regardless of cursor position
            val filtered = input.filter { c -> c.isDigit() }
            val lastDigit = filtered.takeLast(1)
            onDigitChange(lastDigit)
        },
        modifier = modifier
            .width(42.dp)
            .height(56.dp),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}