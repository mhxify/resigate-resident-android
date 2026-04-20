package com.secure.resident.drawer.incidents.presentation.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.core.presentation.component.AppButton
import com.secure.resident.core.presentation.component.BackTopBar
import com.secure.resident.core.presentation.component.MainOutlinedTextField
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.drawer.incidents.data.model.CreateIncidentRequest
import com.secure.resident.drawer.incidents.presentation.viewmodel.CreateIncidentViewModel

@Composable
fun CreateIncidentScreen(
    navController: NavController ,
    createIncidentViewModel: CreateIncidentViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val createIncidentState by createIncidentViewModel.createIncidentState.collectAsState()
    val token = remember { AuthPrefs.getToken(context) }
    val userId = remember { AuthPrefs.getUser(context).userId }

    var incidentDescription by remember { mutableStateOf("") }

    val isLoading = createIncidentState is ResultState.Loading


    LaunchedEffect(createIncidentState) {
        when(val state = createIncidentState) {
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
                topBarName = "Create Incident"
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

            PrimaryText(
                "Incident Description" ,
                textAlign = TextAlign.Start ,
                modifier = Modifier
                    .fillMaxWidth()
            )

            MainOutlinedTextField(
                singleLine = false ,
                imeAction = ImeAction.Done,
                placeholder = "Enter your incident content here",
                value = incidentDescription ,
                onValueChange = {
                    incidentDescription = it
                }
            )

            AppButton(
                title = "Share" ,
                enabled = !isLoading,
                onClick = {
                    when {
                        incidentDescription.isBlank() -> {
                            Toast.makeText(context , "Please enter incident report content" , Toast.LENGTH_LONG).show()
                            return@AppButton
                        }
                    }

                    if (!token.isNullOrBlank() && !userId.isNullOrBlank()) {

                        createIncidentViewModel.createIncident(
                            token ,
                            request = CreateIncidentRequest(
                                category = "Emergency" ,
                                incidentDescription = incidentDescription ,
                                userId = userId
                            )
                        )
                    }
                }
            )
        }
    }
}