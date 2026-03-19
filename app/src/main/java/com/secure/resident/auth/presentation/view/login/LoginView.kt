package com.secure.resident.auth.presentation.view.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.secure.resident.R
import com.secure.resident.auth.navigation.AuthAction
import com.secure.resident.auth.presentation.viewmodel.login.LoginViewModel
import com.secure.resident.core.presentation.component.AppButton
import com.secure.resident.core.presentation.component.MainOutlinedTextField
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.core.presentation.state.ResultState

@Composable
fun LoginView(
    navController: NavController ,
    loginViewModel : LoginViewModel = hiltViewModel()
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val loginState by loginViewModel.loginState.collectAsState()

    val isLoading = loginState is ResultState.Loading

    LaunchedEffect(loginState) {
        when(val state = loginState) {
            is ResultState.Idle -> {}

            is ResultState.Success -> {
                Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show()
                println(state.data)
                //AuthAction.navigationToMainFlow(navController)
                loginViewModel.resetLoginState()
            }

            is ResultState.Error -> {
                val errorMessage = state.message
                Toast.makeText(
                    context,
                    errorMessage,
                    Toast.LENGTH_LONG
                ).show()
                loginViewModel.resetLoginState()
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
            text = "Welcome Back !" ,
            needBold = true ,
            textAlign = TextAlign.Center ,
            modifier = Modifier.fillMaxWidth()
        )
        MainOutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            placeholder = "Enter your email",
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )

        MainOutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "Enter your password",
            isPassword = true,
            imeAction = ImeAction.Done
        )

        AppButton(
            enabled = !isLoading,
            onClick = {
                when {
                    email.isBlank() && password.isBlank() -> {
                        Toast.makeText(context , "Please enter your information" , Toast.LENGTH_LONG).show()
                        return@AppButton
                    }

                    password.isBlank() -> {
                        Toast.makeText(context , "Please enter your password" , Toast.LENGTH_LONG).show()
                        return@AppButton
                    }

                    email.isBlank() -> {
                        Toast.makeText(context , "Please enter your email" , Toast.LENGTH_LONG).show()
                        return@AppButton
                    }
                }

                loginViewModel.login(email , password)
            }
        )
        PrimaryText(
            text = "Forgot Password" ,
            needBold = true,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    AuthAction.navigationToForgotPassFlow(navController)
                }
        )

    }
}