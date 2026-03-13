package com.secure.resident.onboarding.presentation.helper

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.secure.resident.R

@Composable
fun onBoardingPictureProvider(
    index : Int
) : Painter{
    return when(index) {
        0 -> {
            painterResource(R.drawable.onboarding_first)
        }

        1 -> {
            painterResource(R.drawable.onboarding_second)
        }

        2 -> {
            painterResource(R.drawable.onboarding_third)
        }

        else -> painterResource(R.drawable.onboarding_first)
    }
}

data class OnBoardingDescription(
    val title : String ,
    val description : String
)

fun onBoardingDataProvider(
    index: Int
) : OnBoardingDescription {
    return when(index) {
        0 -> {
            OnBoardingDescription(
                title = "Request Guest Access Easily",
                description = "Create and manage guest requests in a few simple steps."
            )
        }

        1 -> {
            OnBoardingDescription(
                title = "Fast and Secure Entry",
                description = "Use approved access passes to make entry smooth and secure."
            )
        }

        2 -> {
            OnBoardingDescription(
                title = "Stay Connected with Your Community",
                description = "Track requests, receive updates, and manage access anytime."
            )
        }

        else -> {
            OnBoardingDescription(
                title = "",
                description =""
            )
        }
    }
}