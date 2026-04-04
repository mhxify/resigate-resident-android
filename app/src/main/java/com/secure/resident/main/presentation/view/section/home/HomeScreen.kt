package com.secure.resident.main.presentation.view.section.home

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
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.core.presentation.component.CircularIndicator
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.core.presentation.helper.formatReservationDateTime
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.main.presentation.viewmodel.home.getAllPost.GetAllPostViewModel

@Composable
fun HomeScreen(
    navController: NavController ,
    getAllPostViewModel: GetAllPostViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val token = remember { AuthPrefs.getToken(context) }

    val postState by getAllPostViewModel.getAllPost.collectAsState()

    LaunchedEffect(token) {
        if (!token.isNullOrBlank()) {
            getAllPostViewModel.getUserPosts(token)
        }
    }

    when(val state = postState) {
        is ResultState.Loading -> {
            CircularIndicator()
        }

        is ResultState.Success -> {
            if (state.data.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize() ,
                    contentAlignment = Alignment.Center
                ){
                    PrimaryText(
                        "No post" ,
                        needBold = true
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
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
        }

        is ResultState.Error -> {
            PrimaryText(
                state.message ,
                needBold = true
            )
        }

        else -> null
    }
}

@Preview
@Composable
fun PostView(
    profilePicUrl : String ="",
    userFullName : String = "Mohamed Ali Benouarzeg",
    content : String? ="",
    createdAt : String ="20/12/2026",
    postImage : String? = "test",
    commentNumber : Int = 10 ,
    onCommentClick : () -> Unit = {} ,
    onProfileClicked: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            UserProfile(
                profilePicUrl = profilePicUrl ,
                userFullName = userFullName ,
                onProfileClicked = onProfileClicked
            )

            if(!content.isNullOrBlank()) {
                PrimaryText(
                    text = content ,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            PrimaryText(
                text = formatReservationDateTime(createdAt),
                color = MaterialTheme.colorScheme.onSurface
            )

            if(!postImage.isNullOrBlank()) {
                AsyncImage(
                    model = "${baseUrl.removeSuffix("/")}${postImage}" ,
                    contentDescription = null ,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 1.dp ,
                            shape = RoundedCornerShape(12.dp) ,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                )
            }

            ReactionAndCommentRowView(
                commentNumber = commentNumber ,
                onCommentClick = onCommentClick
            )
        }
    }
}

@Preview
@Composable
fun UserProfile(
    profilePicUrl : String ="",
    userFullName : String = "Mohamed Ali Benouarzeg",
    onProfileClicked: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp) ,
        verticalAlignment = Alignment.CenterVertically , 
        modifier = Modifier
            .clickable {
                onProfileClicked()
            }
    ) {
        AsyncImage(
            model = "${baseUrl.removeSuffix("/")}${profilePicUrl}" ,
            contentDescription = null ,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp ,
                    shape = CircleShape ,
                    color = MaterialTheme.colorScheme.onSurface
                )
        )

        PrimaryText(
            text = userFullName ,
            needBold = true ,
            color = MaterialTheme.colorScheme.onSurface
        )

    }
}

@Preview
@Composable
fun ReactionAndCommentRowView(
    commentNumber : Int = 10 ,
    onCommentClick : () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically ,
        horizontalArrangement = Arrangement.spacedBy(8.dp) ,
        modifier = Modifier
            .clickable {
                onCommentClick()
            }
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.Comment,
            contentDescription = null ,
            tint = MaterialTheme.colorScheme.onSurface
        )

        PrimaryText(
            text = commentNumber.toString() ,
            color = MaterialTheme.colorScheme.onSurface
        )

    }
}