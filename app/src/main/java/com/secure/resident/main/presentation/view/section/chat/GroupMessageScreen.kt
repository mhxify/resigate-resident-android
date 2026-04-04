package com.secure.resident.main.presentation.view.section.chat

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.core.presentation.component.CircularIndicator
import com.secure.resident.core.presentation.component.MainOutlinedTextField
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.core.presentation.helper.formatReservationDateTime
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.main.data.local.ChatPrefs
import com.secure.resident.main.data.model.message.SendMessageRequest
import com.secure.resident.main.domain.model.message.Message
import com.secure.resident.main.presentation.viewmodel.chat.getGroupMessage.GetGroupMessageViewModel
import com.secure.resident.main.presentation.viewmodel.chat.sendMessage.SendMessageViewModel

@Composable
fun GroupMessageScreen(
    navController: NavController,
    getGroupMessageViewModel: GetGroupMessageViewModel = hiltViewModel() ,
    sendMessageViewModel: SendMessageViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val groupId = remember { ChatPrefs.getGroupInfo(context).groupId }

    val groupName = remember { ChatPrefs.getGroupInfo(context).groupName }

    val groupImageUrl = remember { ChatPrefs.getGroupInfo(context).imageUrl }

    val currentUserId = remember { AuthPrefs.getUser(context).userId }

    val token = remember { AuthPrefs.getToken(context) }

    var message by remember { mutableStateOf("") }

    val groupMessageState by getGroupMessageViewModel.getGroupMessageState.collectAsState()

    val sendMessageState by sendMessageViewModel.sendMessageState.collectAsState()


    LaunchedEffect(groupId , token) {
        if (!token.isNullOrBlank() && !groupId.isNullOrBlank()) {
            getGroupMessageViewModel.getGroupMessages(groupId , token)
        }
    }

    LaunchedEffect(groupId) {
        if (!groupId.isNullOrBlank()) {
            getGroupMessageViewModel.observeGroupMessages(groupId)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            getGroupMessageViewModel.stopObservingGroupMessages()
        }
    }


    LaunchedEffect(sendMessageState) {
        when (val state = sendMessageState) {
            is ResultState.Success -> {
                message = ""

                getGroupMessageViewModel.appendLocalMessage(
                    Message(
                        messageId = state.data.messageId,
                        content = state.data.content,
                        createdAt = state.data.createdAt ?: "",
                        read = state.data.isRead,
                        userId = state.data.userId,
                        fullName = state.data.fullName
                    )
                )

                sendMessageViewModel.resetSendMessageState()
            }

            is ResultState.Error -> {
                Toast.makeText(
                    context,
                    state.message,
                    Toast.LENGTH_LONG
                ).show()

                sendMessageViewModel.resetSendMessageState()
            }

            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            ChatTopBar(
                imageUrl = groupImageUrl ?:"" ,
                groupName = groupName ?:"" ,
                onClick = {
                    navController.popBackStack()
                }
            )
        } ,
        bottomBar = {
            SendMessageBottomBar(
                value = message ,
                onValueChange = {
                    message = it
                }
            ) {
                if (
                    !token.isNullOrBlank() &&
                    !groupId.isNullOrBlank() &&
                    !currentUserId.isNullOrBlank() )
                {
                    sendMessageViewModel.sendMessage(
                        request = SendMessageRequest(
                            content = message ,
                            groupId = groupId ,
                            userId = currentUserId
                        ) ,
                        token = token
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when(val state = groupMessageState) {

                is ResultState.Loading -> {
                    CircularIndicator()
                }

                is ResultState.Success -> {
                    if (state.data.isNotEmpty()) {

                        val listState = rememberLazyListState()

                        LaunchedEffect(state.data.size) {
                            if (state.data.isNotEmpty()) {
                                listState.animateScrollToItem(0)
                            }
                        }

                        LazyColumn(
                            state = listState ,
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            reverseLayout = true
                        ) {
                            items(
                                items = state.data,
                                key = { message -> message.messageId }
                            ) { message ->
                                MessageView(
                                    userFullName = message.fullName ,
                                    isSender = message.userId == currentUserId,
                                    content = message.content,
                                    createdAt = message.createdAt ?:""
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            PrimaryText(
                                text = "No messages yet",
                                needBold = true
                            )
                        }
                    }
                }

                is ResultState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        PrimaryText(
                            text = "Failed to load messages",
                            needBold = true
                        )
                    }
                }

                else -> null
            }
        }
    }
}

@Preview
@Composable
private fun ChatTopBar(
    imageUrl : String ="Test",
    groupName : String = "Test",
    onClick : () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp ,
                start = 16.dp ,
                end = 16.dp ,
                bottom = 16.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "arrow back",
            tint = MaterialTheme.colorScheme.primary ,
            modifier = Modifier
                .clickable {
                    onClick()
                }
        )
        AsyncImage(
            model = imageUrl ,
            contentDescription = null ,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp ,
                    shape = CircleShape ,
                    color = MaterialTheme.colorScheme.primary
                )
        )

        PrimaryText(
            text = groupName ,
            needBold = true
        )
    }
}

@Composable
private fun MessageView(
    userFullName: String = "Mohamed Ali Benouarzeg",
    isSender: Boolean = true,
    content: String = "Hello Mhx!",
    createdAt: String = "2025-01-11"
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isSender) Alignment.End else Alignment.Start
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (isSender) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant
                )
                .padding(12.dp)
        ) {
            if (!isSender) {
                Text(
                    text = userFullName,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))
            }

            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSender) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = formatReservationDateTime(createdAt),
                style = MaterialTheme.typography.labelSmall,
                color = if (isSender) {
                    MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                },
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
private fun SendMessageBottomBar(
    value : String ,
    onValueChange : (String) -> Unit ,
    onClick : () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {

        MainOutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = "Enter your message",
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Send ,
            modifier = Modifier
                .weight(1f)
        )

        SendButton(
            value = value
        ) {
            onClick()
        }
    }
}

@Preview
@Composable
private fun SendButton(
    value: String = "",
    onClick: () -> Unit = {}
) {
    val isEnabled = value.isNotBlank()

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .background(
                if (isEnabled) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            )
            .clickable(enabled = isEnabled) {
                onClick()
            }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            tint = if (isEnabled) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f)
            },
            contentDescription = "send icon"
        )
    }
}