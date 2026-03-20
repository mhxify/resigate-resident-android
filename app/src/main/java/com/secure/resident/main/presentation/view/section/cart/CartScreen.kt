package com.secure.resident.main.presentation.view.section.cart

import android.graphics.Color
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.secure.resident.R
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.main.presentation.helper.generateQrBitmap

@Preview
@Composable
fun CardScreen() {
    val context = LocalContext.current
    val user by remember { mutableStateOf(AuthPrefs.getUser(context)) }

    println(user)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        FlipCard(
            modifier = Modifier.padding(16.dp),
            front = {
                FrontCardView(
                    imageUrl = "${baseUrl.removeSuffix("/")}${user.imageUrl}",
                    userFullName = user.fullname ?: "",
                    userRole = user.role ?: ""
                )
            },
            back = {
                BackCardView(
                    userFullName = user.fullname ?: "",
                    userRole = user.role ?: ""
                )
            }
        )
    }
}

@Composable
private fun FlipCard(
    modifier: Modifier = Modifier,
    front: @Composable () -> Unit,
    back: @Composable () -> Unit
) {
    var isFlipped by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 700),
        label = "flip_rotation"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(460.dp)
                .clickable { isFlipped = !isFlipped }
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (rotation <= 90f) {
                    front()
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                rotationY = 180f
                            }
                    ) {
                        back()
                    }
                }
            }
        }
    }
}


@Composable
private fun FrontCardView(
    imageUrl: String = "",
    userFullName: String = "Mohamed Ali Benouarzeg",
    userRole: String = "Resident"
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "User image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = CircleShape
                    )
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = userFullName,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = userRole,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Tap to show QR",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun BackCardView(
    userFullName: String = "Mohamed Ali Benouarzeg",
    userRole: String = "Resident"
) {
    val primaryColor = MaterialTheme.colorScheme.primary.toArgb()

    val qrContent = remember(userFullName, userRole) {
        buildUserQrContent(userFullName, userRole)
    }

    val qrBitmap = remember(qrContent, primaryColor) {
        generateQrBitmap(
            content = qrContent,
            foregroundColor = primaryColor,
            backgroundColor = Color.WHITE
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.resident_app_logo),
                contentDescription = "Resident logo",
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Resident Access QR",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(18.dp))

            Image(
                bitmap = qrBitmap.asImageBitmap(),
                contentDescription = "User QR Code",
                modifier = Modifier.size(180.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Show this code to the security guard",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tap to go back",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

private fun buildUserQrContent(
    userFullName: String,
    userRole: String
): String {
    return """
        {
            "personFullName": "$userFullName",
            "userRole": "$userRole"
        }
    """.trimIndent()
}