package com.secure.resident.auth.presentation.view.forgot

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.secure.resident.R
import com.secure.resident.auth.presentation.view.forgot.navigation.ForgotAction
import com.secure.resident.core.presentation.component.AppButton
import com.secure.resident.core.presentation.component.MainOutlinedTextField
import com.secure.resident.core.presentation.component.PrimaryText

@Composable
fun EnterEmailView(
    navController: NavController
) {
    var email by remember { mutableStateOf("") }

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
            text = "Enter your email" ,
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
            imeAction = ImeAction.Done
        )

        AppButton(
            title = "Next" ,
            onClick = {
                ForgotAction.navigationToConfirmOtp(navController)
            }
        )

        PrimaryText(
            text = "Did you remember your password ? Back to Login" ,
            needBold = true,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    ForgotAction.popBackToLogin(navController)
                }
        )


    }
}