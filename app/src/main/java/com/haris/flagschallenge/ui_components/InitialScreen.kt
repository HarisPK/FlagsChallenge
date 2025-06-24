package com.haris.flagschallenge.ui_components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialScreen(
    onScheduleChallenge: (Long) -> Unit
) {
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Flags Challenge",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Set Challenge Time",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hour picker
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Hour", fontSize = 14.sp)
                OutlinedTextField(
                    value = selectedHour.toString().padStart(2, '0'),
                    onValueChange = { value ->
                        value.toIntOrNull()?.let { hour ->
                            if (hour in 0..23) selectedHour = hour
                        }
                    },
                    modifier = Modifier.width(80.dp)
                )
            }

            Text(":", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            // Minute picker
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Minute", fontSize = 14.sp)
                OutlinedTextField(
                    value = selectedMinute.toString().padStart(2, '0'),
                    onValueChange = { value ->
                        value.toIntOrNull()?.let { minute ->
                            if (minute in 0..59) selectedMinute = minute
                        }
                    },
                    modifier = Modifier.width(80.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, selectedHour)
                    set(Calendar.MINUTE, selectedMinute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)

                    // If the time is in the past, schedule for tomorrow
                    if (timeInMillis < System.currentTimeMillis()) {
                        add(Calendar.DAY_OF_YEAR, 1)
                    }
                }
                onScheduleChallenge(calendar.timeInMillis)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Save", fontSize = 18.sp)
        }
    }
}