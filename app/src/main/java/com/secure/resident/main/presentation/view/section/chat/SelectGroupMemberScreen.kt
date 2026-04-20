package com.secure.resident.main.presentation.view.section.chat

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.auth.navigation.AuthAction
import com.secure.resident.auth.presentation.viewmodel.getSystemUsers.GetSystemUsersViewModel
import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.core.presentation.component.AppButton
import com.secure.resident.core.presentation.component.BackTopBar
import com.secure.resident.core.presentation.component.CircularIndicator
import com.secure.resident.core.presentation.component.MainOutlinedTextField
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.core.presentation.helper.toByteArray
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.main.data.local.ChatPrefs
import com.secure.resident.main.data.model.createGroup.CreateGroupMessageRequest
import com.secure.resident.main.presentation.viewmodel.chat.createGroup.CreateGroupViewModel

@Composable
fun SelectGroupMemberScreen(
    navController: NavController,
    createGroupViewModel: CreateGroupViewModel = hiltViewModel(),
    getSystemUsersViewModel: GetSystemUsersViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    val context = LocalContext.current

    val userId = remember { AuthPrefs.getUser(context).userId }
    val token = remember { AuthPrefs.getToken(context) }

    val groupName = remember { ChatPrefs.getGroupName(context) }
    val imageUri = remember { ChatPrefs.getGroupImageUri(context) }

    val selectedUserIds = remember { mutableStateListOf<String>() }

    val createGroupState by createGroupViewModel.createGroupState.collectAsState()
    val getSystemUsersState by getSystemUsersViewModel.systemUser.collectAsState()

    // If your users are not loaded automatically in VM init,
    // call the loading function here.
    LaunchedEffect(Unit) {
        if (!token.isNullOrBlank()) {
            getSystemUsersViewModel.getSystemUsers(token)
        }
    }

    LaunchedEffect(createGroupState) {
        when(val state = createGroupState ) {
            is ResultState.Success -> {
                AuthAction.navigationToMainFlow(navController)
                createGroupViewModel.clearCreateGroupState()
            }

            is ResultState.Error -> {
                val errorMessage = state.message
                Toast.makeText(
                    context,
                    errorMessage,
                    Toast.LENGTH_LONG
                ).show()
                createGroupViewModel.clearCreateGroupState()
            }

            else -> null
        }
    }

    Scaffold(
        topBar = {
            BackTopBar(
                onBackClick = {
                    navController.popBackStack()
                },
                topBarName = "Complete Create Group"
            )
        } ,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                AppButton(
                    title = "Create",
                    enabled = createGroupState !is ResultState.Loading && selectedUserIds.isNotEmpty(),
                    onClick = {
                        if (selectedUserIds.isEmpty()){
                            Toast.makeText(context , "Please select group member" , Toast.LENGTH_LONG).show()
                            return@AppButton
                        }

                        if (!token.isNullOrBlank() && !userId.isNullOrBlank() && !groupName.isNullOrBlank()){
                            if (imageUri != null){
                                val imageBytes = imageUri.toByteArray(context)
                                createGroupViewModel.createGroup(
                                    token ,
                                    request = CreateGroupMessageRequest(
                                        userId = userId ,
                                        groupName = groupName ,
                                        memberIds = selectedUserIds ,
                                        image = imageBytes
                                    )
                                )
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            PrimaryText(
                text = "Select Group Members:",
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
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

            // Small selected count box
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PrimaryText(
                    text = "Selected members: ${selectedUserIds.size}",
                    needBold = true
                )
            }

            when (val state = getSystemUsersState) {
                is ResultState.Loading -> {
                    CircularIndicator()
                }

                is ResultState.Success -> {
                    val users = state.data

                    // Filter users by query
                    val filteredUsers = users.filter { user ->
                        val fullName = user.fullname.orEmpty()
                        fullName.contains(query.trim(), ignoreCase = true)
                    }

                    if (filteredUsers.isEmpty()) {
                        PrimaryText(
                            text = "No members found"
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(filteredUsers) { user ->
                                val userId = user.userId ?: return@items
                                val isSelected = selectedUserIds.contains(userId)

                                UserView(
                                    imageUrl = user.imageUrl ?: "",
                                    userFullName = user.fullname ?: "",
                                    isSelected = isSelected,
                                    onClick = {
                                        if (isSelected) {
                                            selectedUserIds.remove(userId)
                                        } else {
                                            selectedUserIds.add(userId)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                is ResultState.Error -> {
                    PrimaryText(
                        text = state.message
                    )
                }

                else -> Unit
            }
        }
    }
}

@Composable
fun UserView(
    imageUrl: String,
    userFullName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val fullImageUrl = "${baseUrl.removeSuffix("/")}${imageUrl}"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                if (isSelected) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.10f)
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
                },
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = fullImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(58.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary
                )
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            PrimaryText(
                text = userFullName,
                needBold = true
            )

            PrimaryText(
                text = if (isSelected) "Selected" else "Tap to select"
            )
        }

        BoxSelectionIndicator(
            isSelected = isSelected
        )
    }
}

@Composable
fun BoxSelectionIndicator(
    isSelected: Boolean
) {
    Row(
        modifier = Modifier
            .size(22.dp)
            .clip(CircleShape)
            .background(
                if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )
            .border(
                width = 1.dp,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline
                },
                shape = CircleShape
            )
    ) {}
}