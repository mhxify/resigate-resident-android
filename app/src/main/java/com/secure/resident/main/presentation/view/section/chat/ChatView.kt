package com.secure.resident.main.presentation.view.section.chat

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.core.presentation.component.AppButton
import com.secure.resident.core.presentation.component.CircularIndicator
import com.secure.resident.core.presentation.component.MainOutlinedTextField
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.main.data.local.ChatPrefs
import com.secure.resident.main.navigation.MainAction
import com.secure.resident.main.navigation.MainRoute
import com.secure.resident.main.presentation.viewmodel.chat.getUserGroup.GetUserGroupViewModel

@Composable
fun ChatScreen(
    navController: NavController ,
    getUserGroupViewModel: GetUserGroupViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val userId = remember { AuthPrefs.getUser(context).userId }
    val token = remember { AuthPrefs.getToken(context) }
    var query by remember { mutableStateOf("") }
    val userGroupState by getUserGroupViewModel.getGroupsState.collectAsState()

    LaunchedEffect(userId, token) {
        if (!userId.isNullOrBlank() && !token.isNullOrBlank()) {
            getUserGroupViewModel.getUserGroups(userId, token)
        }
    }

    val errorMessage = (userGroupState as? ResultState.Error)?.message

    LaunchedEffect(errorMessage) {
        if (!errorMessage.isNullOrBlank()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            getUserGroupViewModel.resetGroupState()
        }
    }

    when (val state = userGroupState) {
        is ResultState.Loading -> {
            CircularIndicator()
        }

        is ResultState.Success -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp) ,
                modifier = Modifier.fillMaxSize()
            ) {

                AppButton(
                    title = "Create New Chat" ,
                    onClick = {
                        navController.navigate(MainRoute.CREATE_GROUP_INFO)
                    }
                )

                MainOutlinedTextField(
                    singleLine = true,
                    imeAction = ImeAction.Done,
                    placeholder = "Search for a member",
                    value = query,
                    onValueChange = {
                        query = it
                    }
                )



                PrimaryText(
                    "Messages" ,
                    needBold = true
                )

                if (state.data.isNotEmpty()) {



                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            items = state.data.filter { groupMessage ->
                                groupMessage.groupName?.contains(query , ignoreCase = true) ?:false
                            },
                            key = { group -> group.groupId?:"" }
                        ) { group ->
                            val fullImageUrl =
                                "${baseUrl.removeSuffix("/")}${group.imageUrl}"

                            ChatView(
                                imageUrl = fullImageUrl,
                                groupName = group.groupName ?:"",
                                onClick = {

                                    ChatPrefs.setGroupInfo(
                                        groupId = group.groupId ?:"",
                                        groupName = group.groupName ?:"",
                                        groupImage = fullImageUrl ,
                                        groupCreatedAt = group.createdAt ?:"",
                                        context = context
                                    )

                                    MainAction.navigationToGroupChat(navController)

                                }
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        PrimaryText(
                            text = "You have no groups at the moment",
                            needBold = true
                        )
                    }
                }
            }
        }
        else -> Unit
    }
}

@Composable
private fun ChatView(
    imageUrl : String ,
    groupName : String ,
    onClick : ()-> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
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