package com.secure.resident.reserveFacility.presentation.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.secure.resident.core.presentation.component.PrimaryText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView(
    onDatePicked: (String) -> Unit = {},
    state: DatePickerState = rememberDatePickerState(),
    onDismiss: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            tonalElevation = 6.dp,
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrimaryText(
                    "Please select a reservation date"
                )

                DatePicker(
                    state = state,
                    colors = DatePickerDefaults.colors(
                        // Selected day (circle background)
                        selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                        selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,

                        // Today (border highlight)
                        todayDateBorderColor = MaterialTheme.colorScheme.primary,

                        // Default days
                        dayContentColor = MaterialTheme.colorScheme.onSurface,

                        // Disabled / unavailable days
                        disabledDayContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),

                        // Header (month/year text)
                        titleContentColor = MaterialTheme.colorScheme.primary,
                        headlineContentColor = MaterialTheme.colorScheme.primary,

                        // Week days (Mon, Tue...)
                        weekdayContentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.onPrimary

                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            state.selectedDateMillis?.let { millis ->
                                val formattedDate = SimpleDateFormat(
                                    "yyyy-MM-dd",
                                    Locale.getDefault()
                                ).format(Date(millis))

                                onDatePicked(formattedDate)
                            }
                            onDismiss()
                        }
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerView(
    onTimePicked: (String) -> Unit = {},
    state: TimePickerState = rememberTimePickerState(),
    onDismiss: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            tonalElevation = 6.dp,
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TimePicker(
                    state = state ,
                    colors = TimePickerDefaults.colors(
                        periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary ,
                        periodSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimary ,
                        periodSelectorUnselectedContentColor = MaterialTheme.colorScheme.primary ,
                        periodSelectorUnselectedContainerColor = MaterialTheme.colorScheme.onPrimary ,
                        timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary ,
                        timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimary ,
                        timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.primary ,
                        timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.onPrimary ,
                        selectorColor = MaterialTheme.colorScheme.primary ,
                        clockDialColor = MaterialTheme.colorScheme.onPrimary
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            val formattedTime = String.format(
                                Locale.getDefault(),
                                "%02d:%02d:%02d",
                                state.hour,
                                state.minute,
                                0
                            )

                            onTimePicked(formattedTime)
                            onDismiss()
                        }
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}