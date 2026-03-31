package com.secure.resident.main.presentation.view.section.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.core.presentation.component.CircularIndicator
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.main.domain.model.post.Post
import com.secure.resident.main.presentation.view.section.home.PostView
import com.secure.resident.main.presentation.viewmodel.home.getUserPost.GetUserPostViewModel


@Composable
fun ProfileScreen(
    navController: NavController ,
    getUserPostViewModel: GetUserPostViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val user = remember { AuthPrefs.getUser(context) }
    val userId = remember { user.userId }
    val token = remember { AuthPrefs.getToken(context) }

    val postState by getUserPostViewModel.getUserPosts.collectAsState()

    LaunchedEffect(userId , token) {
        if (!userId.isNullOrBlank() && !token.isNullOrBlank()) {
            getUserPostViewModel.getUserPosts(userId , token)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize() ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        // Profile Header
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = "${baseUrl.removeSuffix("/")}/${user.imageUrl.orEmpty().removePrefix("/")}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary
                        )
                )

                PrimaryText(
                    text = user.fullname ?: "",
                    needBold = true
                )

                PrimaryText(
                    text = user.email ?: ""
                )

                EditProfile()
            }
        }

        // Section title
        item {
            Text(
                text = "My Posts",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        }

        when (val state = postState) {

            is ResultState.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularIndicator()
                    }
                }
            }

            is ResultState.Success -> {
                if (state.data.isEmpty()) {
                    item {
                        Text(
                            text = "No posts yet",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 24.dp)
                        )
                    }
                } else {
                    items(
                        items = state.data,
                        key = { post -> post.postId }
                    ) { post ->
                        PostView(
                            profilePicUrl = post.postOwnerPicture ,
                            userFullName = post.postOwnerName ,
                            content = post.content ,
                            createdAt = post.createdAt ,
                            postImage = post.imageUrl ,
                            commentNumber = post.commentNumber ,
                            onProfileClicked = {

                            } ,
                            onCommentClick = {

                            }
                        )
                    }
                }
            }

            is ResultState.Error -> {
                item {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 24.dp)
                    )
                }
            }

            else -> null
        }
    }
}

@Preview
@Composable
private fun EditProfile(
    onClick : () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center ,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable {
                onClick()
            }
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically ,
            horizontalArrangement = Arrangement.spacedBy(8.dp) ,
            modifier = Modifier
                .padding(16.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Edit ,
                contentDescription = null ,
                tint = MaterialTheme.colorScheme.onPrimary
            )

            PrimaryText(
                "Edit Profile" ,
                needBold = true ,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
