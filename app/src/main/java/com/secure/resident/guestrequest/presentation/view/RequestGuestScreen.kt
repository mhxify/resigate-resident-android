package com.secure.resident.guestrequest.presentation.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.core.presentation.component.AppButton
import com.secure.resident.core.presentation.component.BackTopBar
import com.secure.resident.core.presentation.component.DataBoxView
import com.secure.resident.core.presentation.component.DatePickerView
import com.secure.resident.core.presentation.component.MainOutlinedTextField
import com.secure.resident.core.presentation.component.TimePickerView
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.guestrequest.data.model.GuestRequest
import com.secure.resident.guestrequest.presentation.viewmodel.RequestGuestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestGuestScreen(
    navController: NavController ,
    requestGuestViewModel : RequestGuestViewModel = hiltViewModel()
) {

    var guestEmail by remember { mutableStateOf("") }
    var guestFullName by remember { mutableStateOf("") }
    var reservationDate by remember { mutableStateOf("") }
    var openDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()


    val context = LocalContext.current
    val userId = remember { AuthPrefs.getUser(context).userId }
    val token = remember { AuthPrefs.getToken(context) }

    val timePickerState = rememberTimePickerState()

    var openStartTimePicker by remember { mutableStateOf(false) }
    var openEndTimePicker by remember { mutableStateOf(false) }

    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

    val requestGuestState by requestGuestViewModel.requestGuestState.collectAsState()

    val isLoading = requestGuestState is ResultState.Loading

    val validationMessage by requestGuestViewModel.validationMessage.collectAsState()

    LaunchedEffect(validationMessage) {
        validationMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            requestGuestViewModel.clearValidationMessage()
        }
    }


    LaunchedEffect(requestGuestState) {
        when(val state = requestGuestState) {
            is ResultState.Success -> {
                navController.popBackStack()
            }
            is ResultState.Error -> {
                val errorMessage = state.message

                Toast.makeText(
                    context ,
                    errorMessage ,
                    Toast.LENGTH_LONG
                ).show()
            }

            else -> null
        }
    }


    Scaffold(
        topBar = {
            BackTopBar(
                onBackClick = {
                    navController.popBackStack()
                },
                topBarName = "Request Guest"
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(16.dp)
                .padding(innerPadding) ,
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            MainOutlinedTextField(
                value = guestFullName ,
                onValueChange = {
                    guestFullName = it
                } ,
                keyboardType = KeyboardType.Text ,
                label = "Guest Full Name" ,
                placeholder = "Please enter the full name of the guest"
            )

            MainOutlinedTextField(
                value = guestEmail ,
                onValueChange = {
                    guestEmail = it
                } ,
                keyboardType = KeyboardType.Email ,
                label = "Guest Email" ,
                placeholder = "Please enter the email of the guest"
            )

            DataBoxView(
                values = reservationDate ,
                placeHolder = "Please pick guest request date" ,
                onClick = {
                    openDatePicker = true
                }
            )

            DataBoxView(
                values = startTime ,
                placeHolder = "Please pick start time" ,
                onClick = {
                    openStartTimePicker = true
                }
            )

            DataBoxView(
                values = endTime ,
                placeHolder = "Please pick end time" ,
                onClick = {
                    openEndTimePicker = true
                }
            )



            AppButton(
                title = "Send Request" ,
                enabled = !isLoading,
                onClick = {

                    val isValid = requestGuestViewModel.validationGuestRequestForm(
                        guestEmail = guestEmail,
                        startTime = startTime,
                        endTime = endTime ,
                        fullName = guestFullName ,
                        guestDate = reservationDate
                    )

                    if (!isValid) return@AppButton


                    if (!token.isNullOrBlank() && !userId.isNullOrBlank()) {
                        requestGuestViewModel.requestGuest(
                            token ,
                            request = GuestRequest(
                                userId = userId,
                                guestEmail = guestEmail,
                                startTime = startTime,
                                endTime = endTime,
                                guestDate = reservationDate,
                                guestFullName = guestFullName,
                            )
                        )
                    }
                }
            )
        }
    }

    if (openDatePicker) {
        DatePickerView(
            state = datePickerState,
            onDatePicked = { pickedDate ->
                reservationDate = pickedDate
            },
            onDismiss = {
                openDatePicker = false
            }
        )
    }


    if(openStartTimePicker) {
        TimePickerView(
            state = timePickerState ,
            onTimePicked = { pickedTime ->
                startTime = pickedTime
            } ,
            onDismiss = {
                openStartTimePicker = false
            }
        )
    }

    if(openEndTimePicker) {
        TimePickerView(
            state = timePickerState ,
            onTimePicked = { pickedTime ->
                endTime = pickedTime
            } ,
            onDismiss = {
                openEndTimePicker = false
            }
        )
    }
}