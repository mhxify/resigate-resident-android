package com.secure.resident.auth.presentation.view.forgot.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.secure.resident.ui.theme.MainColor

@Composable
fun OtpInputField(
    otp: MutableState<String>,
    count: Int = 4,
    otpBoxModifier: Modifier = Modifier,
    otpTextType: KeyboardType = KeyboardType.Number,
    textColor: Color = Color.Black,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr){
        val otpFieldsValues = remember {
            (0 until count).mapIndexed { index, i ->
                mutableStateOf(
                    OtpField(
                        text = otp.value.getOrNull(i)?.toString() ?: "",
                        focusRequester = FocusRequester()
                    )
                )
            }
        }

        LaunchedEffect(key1 = otp.value) {
            for (i in otpFieldsValues.indices) {
                otpFieldsValues[i].value =
                    otpFieldsValues[i].value.copy(otp.value.getOrNull(i)?.toString() ?: "")
            }
            if (otp.value.isBlank()) {
                otpFieldsValues[0].value.focusRequester?.requestFocus()
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(count) { index ->
                OtpBox(
                    modifier = otpBoxModifier
                        .size(56.dp) ,
                    otpValue = otpFieldsValues[index].value,
                    textType = otpTextType,
                    textColor = textColor,
                    isLastItem = index == count - 1,
                    onValueChange = { newValue ->
                        handleOtpInputChange(index, count, newValue, otpFieldsValues, otp)
                    },
                    onFocusSet = { focusRequester ->
                        otpFieldsValues[index].value =
                            otpFieldsValues[index].value.copy(focusRequester = focusRequester)
                    },
                    onNext = {
                        focusNextBox(index, count, otpFieldsValues)
                    },
                )
            }
        }
    }
}

private data class OtpField(
    val text: String,
    val focusRequester: FocusRequester? = null
)

private fun handleOtpInputChange(
    index: Int,
    count: Int,
    newValue: String,
    otpFieldsValues: List<MutableState<OtpField>>,
    otp: MutableState<String>
) {
    if (newValue.length <= 1) {
        otpFieldsValues[index].value = otpFieldsValues[index].value.copy(text = newValue)
    } else if (newValue.isNotEmpty()) {
        newValue.forEachIndexed { i, char ->
            if (index + i < count) {
                otpFieldsValues[index + i].value =
                    otpFieldsValues[index + i].value.copy(text = char.toString())
            }
        }
    }

    var currentOtp = ""
    otpFieldsValues.forEach {
        currentOtp += it.value.text
    }

    try {
        if (newValue.isEmpty() && index > 0) {
            otpFieldsValues.getOrNull(index - 1)?.value?.focusRequester?.requestFocus()
        } else if (index < count - 1 && newValue.isNotEmpty()) {
            otpFieldsValues.getOrNull(index + 1)?.value?.focusRequester?.requestFocus()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    otp.value = currentOtp
}

private fun focusNextBox(
    index: Int,
    count: Int,
    otpFieldsValues: List<MutableState<OtpField>>
) {
    if (index + 1 < count) {
        try {
            otpFieldsValues[index + 1].value.focusRequester?.requestFocus()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@SuppressLint("RememberInComposition")
@Composable
private fun OtpBox(
    modifier: Modifier,
    otpValue: OtpField,
    textType: KeyboardType = KeyboardType.Number,
    textColor: Color = Color.Black,
    isLastItem: Boolean,
    onValueChange: (String) -> Unit,
    onFocusSet: (FocusRequester) -> Unit,
    onNext: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val focusRequest = otpValue.focusRequester ?: FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.White, CircleShape)
            .border(1.dp, MainColor, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        BasicTextField(
            value = TextFieldValue(otpValue.text, TextRange(maxOf(0, otpValue.text.length))),
            onValueChange = {
                if (it.text != otpValue.text) {
                    onValueChange(it.text)
                }
            },
            modifier = Modifier
                .focusRequester(focusRequest)
                .onGloballyPositioned {
                    onFocusSet(focusRequest)
                },
            textStyle = androidx.compose.ui.text.TextStyle(
                textAlign = TextAlign.Center,
                color = textColor
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = textType,
                imeAction = if (isLastItem) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onNext()
                },
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            visualTransformation = getVisualTransformation(textType),
        )
    }
}

@Composable
private fun getVisualTransformation(textType: KeyboardType): VisualTransformation {
    return if (textType == KeyboardType.NumberPassword || textType == KeyboardType.Password) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }
}