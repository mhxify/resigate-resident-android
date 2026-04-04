package com.secure.resident.report.presentation.view

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.core.presentation.component.AppButton
import com.secure.resident.core.presentation.component.BackTopBar
import com.secure.resident.core.presentation.component.ImagePickerView
import com.secure.resident.core.presentation.component.MainOutlinedTextField
import com.secure.resident.core.presentation.helper.toByteArray
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.report.data.model.SendReportRequest
import com.secure.resident.report.presentation.viewmodel.SendReportViewModel

@Composable
fun SendReportScreen(
    navController: NavController ,
    sendReportViewModel: SendReportViewModel = hiltViewModel()
) {
    var reportContent by remember { mutableStateOf("") }
    var picUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val userId = remember { AuthPrefs.getUser(context).userId }
    val token = remember { AuthPrefs.getToken(context) }
    val sendReportState by sendReportViewModel.sendReportState.collectAsState()

    val isLoading = sendReportState is ResultState.Loading

    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            picUri = uri
        }
    }


    LaunchedEffect(sendReportState) {
        when(val state = sendReportState) {
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
                topBarName = "Send Report"
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

            //Image Picker
            ImagePickerView(
                imageUri = picUri ,
                onClick = {
                    pickMedia.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            )


            MainOutlinedTextField(
                singleLine = false ,
                imeAction = ImeAction.Done,
                placeholder = "Enter your report content here",
                value = reportContent ,
                onValueChange = {
                    reportContent = it
                }
            )

            AppButton(
                title = "Send now!" ,
                enabled = !isLoading,
                onClick = {
                    when {
                        reportContent.isBlank() -> {
                            Toast.makeText(context , "Please enter report content" , Toast.LENGTH_LONG).show()
                            return@AppButton
                        }
                        picUri == null -> {
                            Toast.makeText(context , "Please select report picture" , Toast.LENGTH_LONG).show()
                            return@AppButton
                        }
                    }

                    if (!token.isNullOrBlank() && !userId.isNullOrBlank()) {
                        val imageBytes = picUri?.toByteArray(context)
                        imageBytes?.let {
                            sendReportViewModel.sendReport(
                                token ,
                                request = SendReportRequest(
                                    userId = userId ,
                                    content = reportContent ,
                                    image = imageBytes
                                )
                            )
                        }
                    }
                }
            )
        }
    }
}