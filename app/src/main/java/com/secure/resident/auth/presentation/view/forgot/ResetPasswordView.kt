package com.secure.resident.auth.presentation.view.forgot

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.secure.resident.R
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.auth.presentation.view.forgot.navigation.ForgotAction
import com.secure.resident.auth.presentation.viewmodel.forgot.resetPassword.ResetPasswordViewModel
import com.secure.resident.core.presentation.component.AppButton
import com.secure.resident.core.presentation.component.MainOutlinedTextField
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.core.presentation.state.ResultState

@Composable
fun ResetPasswordView(
    navController: NavController ,
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val resetPasswordState by resetPasswordViewModel.resetPasswordState.collectAsState()
    val context = LocalContext.current
    val email by remember { mutableStateOf(AuthPrefs.getEmail(context)) }
    val isLoading = resetPasswordState is ResultState.Loading

    LaunchedEffect(resetPasswordState) {
        when(val state = resetPasswordState) {
            is ResultState.Idle -> {}

            is ResultState.Success -> {
                ForgotAction.popBackToLogin(navController)
                resetPasswordViewModel.resetPasswordState()
            }

            is ResultState.Error -> {
                val errorMessage = state.message
                Toast.makeText(
                    context,
                    errorMessage,
                    Toast.LENGTH_LONG
                ).show()
                resetPasswordViewModel.resetPasswordState()
            }

            else -> null
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.resident_app_logo) ,
            contentDescription = null
        )

        PrimaryText(
            text = "Reset your password !" ,
            needBold = true ,
            textAlign = TextAlign.Center ,
            modifier = Modifier.fillMaxWidth()
        )

        MainOutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "Enter your new password",
            isPassword = true,
            imeAction = ImeAction.Next
        )

        MainOutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm Password",
            placeholder = "Confirm your new password",
            isPassword = true,
            imeAction = ImeAction.Done
        )

        AppButton(
            title = "Reset Password" ,
            enabled = !isLoading,
            onClick = {
                when {
                    password.isBlank() -> {
                        Toast.makeText(
                            context,
                            "Password cannot be empty",
                            Toast.LENGTH_LONG
                        ).show()
                        return@AppButton
                    }

                    password.length < 8 -> {
                        Toast.makeText(
                            context,
                            "Password must be at least 8 characters",
                            Toast.LENGTH_LONG
                        ).show()
                        return@AppButton
                    }

                    confirmPassword.isBlank() -> {
                        Toast.makeText(
                            context,
                            "Please confirm your password",
                            Toast.LENGTH_LONG
                        ).show()
                        return@AppButton
                    }

                    password != confirmPassword -> {
                        Toast.makeText(
                            context,
                            "Passwords do not match",
                            Toast.LENGTH_LONG
                        ).show()
                        return@AppButton
                    }
                }
                email?.let { email ->
                    resetPasswordViewModel.resetPassword(email , password)
                }
            }
        )

    }
}