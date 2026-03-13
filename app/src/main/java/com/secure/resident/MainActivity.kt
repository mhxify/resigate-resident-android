package com.secure.resident

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.secure.resident.core.navigation.AppNavigation
import com.secure.resident.ui.theme.Secure_gated_residentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Secure_gated_residentTheme {
                AppNavigation()
            }
        }
    }
}
