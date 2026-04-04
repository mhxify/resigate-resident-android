package com.secure.resident.reserveFacility.presentation.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.core.presentation.component.AppButton
import com.secure.resident.core.presentation.component.BackTopBar
import com.secure.resident.core.presentation.component.MainOutlinedTextField
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.reserveFacility.data.local.ReserveFacilityPrefs
import com.secure.resident.reserveFacility.data.model.ReserveFacilityRequest
import com.secure.resident.reserveFacility.navigation.ReserveFacilitiesAction
import com.secure.resident.reserveFacility.presentation.view.component.DataBoxView
import com.secure.resident.reserveFacility.presentation.view.component.DatePickerView
import com.secure.resident.reserveFacility.presentation.view.component.TimePickerView
import com.secure.resident.reserveFacility.presentation.viewmodel.postreservation.PostReservationFacilitiesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteReserveFacilitiesScreen(
    navController: NavController ,
    postReservationFacilitiesViewModel: PostReservationFacilitiesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val token = remember { AuthPrefs.getToken(context) }
    val userId = remember { AuthPrefs.getUser(context).userId }
    val facilityId = remember { ReserveFacilityPrefs.getFacilityId(context) }

    val postReservationState by postReservationFacilitiesViewModel.reservationState.collectAsState()

    val isLoading = remember { postReservationState is ResultState.Loading }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    var openDatePicker by remember { mutableStateOf(false) }
    var openStartTimePicker by remember { mutableStateOf(false) }
    var openEndTimePicker by remember { mutableStateOf(false) }

    var numberOfGuest by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var reservationDate by remember { mutableStateOf("") }

    LaunchedEffect(postReservationState) {
        when( val state = postReservationState ) {
            is ResultState.Success -> {
                ReserveFacilitiesAction.popBackToMain(navController)
                postReservationFacilitiesViewModel.resetReservationState()
            }

            is ResultState.Error -> {
                val errorMessage = state.message
                Toast.makeText(
                    context,
                    errorMessage,
                    Toast.LENGTH_LONG
                ).show()
                postReservationFacilitiesViewModel.resetReservationState()
            }

            else -> null
        }
    }

    val validationMessage by postReservationFacilitiesViewModel.validationMessage.collectAsState()

    LaunchedEffect(validationMessage) {
        validationMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            postReservationFacilitiesViewModel.clearValidationMessage()
        }
    }

    Scaffold(
        topBar = {
            BackTopBar(
                onBackClick = {
                    navController.popBackStack()
                } ,
                topBarName = "Complete Reservation Information"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) ,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DataBoxView(
                values = reservationDate ,
                placeHolder = "Please pick reservation date" ,
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

            MainOutlinedTextField(
                value = numberOfGuest ,
                onValueChange = {
                    numberOfGuest = it
                } ,
                keyboardType = KeyboardType.Number ,
                label = "Number of Guest" ,
                placeholder = "Please enter the number of guest"
            )

            AppButton(
                title = "Reserve now !" ,
                enabled = !isLoading ,
                onClick = {
                    val isValid = postReservationFacilitiesViewModel.validateReservationForm(
                        reservationDate = reservationDate,
                        startTime = startTime,
                        endTime = endTime,
                        numberOfGuest = numberOfGuest
                    )

                    if (!isValid) return@AppButton

                    if (!token.isNullOrBlank() && !userId.isNullOrBlank() && !facilityId.isNullOrBlank()) {
                        val request = ReserveFacilityRequest(
                            endTime = endTime ,
                            facilityId = facilityId ,
                            numberOfGuests = numberOfGuest.toInt() ,
                            reservationDate = reservationDate ,
                            startTime = startTime ,
                            userId = userId
                        )

                        postReservationFacilitiesViewModel.postReservation(token , request)
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