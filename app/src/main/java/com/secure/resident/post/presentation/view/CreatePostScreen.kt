package com.secure.resident.post.presentation.view

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.core.presentation.component.AppButton
import com.secure.resident.core.presentation.component.BackTopBar
import com.secure.resident.core.presentation.component.ImagePickerView
import com.secure.resident.core.presentation.component.MainOutlinedTextField
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.core.presentation.helper.toByteArray
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.post.data.model.CreatePostRequest
import com.secure.resident.post.presentation.viewmodel.CreatePostViewModel

@Composable
fun CreatePostScreen(
    navController: NavController ,
    createPostViewModel: CreatePostViewModel = hiltViewModel()
) {
    var postContent by remember { mutableStateOf("") }
    var picUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val userId = remember { AuthPrefs.getUser(context).userId }
    val token = remember { AuthPrefs.getToken(context) }
    val createPostState by createPostViewModel.createPostState.collectAsState()

    val isLoading = createPostState is ResultState.Loading

    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            picUri = uri
        }
    }


    LaunchedEffect(createPostState) {
        when(val state = createPostState) {
            is ResultState.Success -> {
                navController.popBackStack()
            }
            is ResultState.Error -> {
                val errorMessage = state.message

                Toast.makeText(
                    context ,
                    errorMessage ,
                    Toast.LENGTH_LONG
                ).show()


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
                topBarName = "Create Post"
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(16.dp)
                .padding(innerPadding) ,
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            //Image Picker
            ImagePickerView(
                imageUri = picUri ,
                onClick = {
                    pickMedia.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            )

            PrimaryText(
                "Post Description" ,
                textAlign = TextAlign.Start ,
                modifier = Modifier
                    .fillMaxWidth()
            )

            MainOutlinedTextField(
                singleLine = false ,
                imeAction = ImeAction.Done,
                placeholder = "Enter your report content here",
                value = postContent ,
                onValueChange = {
                    postContent = it
                }
            )

            AppButton(
                title = "Share" ,
                enabled = !isLoading,
                onClick = {
                    when {
                        postContent.isBlank() -> {
                            Toast.makeText(context , "Please enter report content" , Toast.LENGTH_LONG).show()
                            return@AppButton
                        }
                        picUri == null -> {
                            Toast.makeText(context , "Please select report picture" , Toast.LENGTH_LONG).show()
                            return@AppButton
                        }
                    }

                    if (!token.isNullOrBlank() && !userId.isNullOrBlank()) {
                        val imageBytes = picUri?.toByteArray(context)
                        imageBytes?.let {
                            createPostViewModel.createPost(
                                token ,
                                request = CreatePostRequest(
                                    userId = userId ,
                                    image = imageBytes ,
                                    content = postContent
                                )
                            )
                        }
                    }
                }
            )
        }
    }
}