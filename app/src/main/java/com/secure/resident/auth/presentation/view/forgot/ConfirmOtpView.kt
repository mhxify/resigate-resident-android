package com.secure.resident.auth.presentation.view.forgot

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.secure.resident.R
import com.secure.resident.auth.presentation.view.forgot.component.OtpInputField
import com.secure.resident.auth.presentation.view.forgot.navigation.ForgotAction
import com.secure.resident.core.presentation.component.AppButton
import com.secure.resident.core.presentation.component.PrimaryText

@Composable
fun ConfirmOtpView(
    navController: NavController
) {
    val otpInput = remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.resident_app_logo) ,
            contentDescription = null
        )

        PrimaryText(
            text = "Confirm your OTP!" ,
            needBold = true ,
            textAlign = TextAlign.Center ,
            modifier = Modifier.fillMaxWidth()
        )

        OtpInputField(
            otp = otpInput,
            count = 6,
            otpBoxModifier = Modifier
                .align(Alignment.CenterHorizontally),
            otpTextType = KeyboardType.Number
        )

        AppButton(
            title = "Confirm Otp" ,
            onClick = {
                ForgotAction.navigationToResetPassword(navController)
            }
        )


    }
}