package com.secure.resident.main.presentation.view.section.chat

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.secure.resident.core.presentation.component.AppButton
import com.secure.resident.core.presentation.component.BackTopBar
import com.secure.resident.core.presentation.component.ImagePickerView
import com.secure.resident.core.presentation.component.MainOutlinedTextField
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.main.data.local.ChatPrefs
import com.secure.resident.main.navigation.MainRoute

@Composable
fun CreateGroupInfoScreen(
    navController: NavController
) {

    var co by remember { mutableIntStateOf(0) }

    var picUri by remember { mutableStateOf<Uri?>(null) }
    var groupName by remember { mutableStateOf("") }
    val context = LocalContext.current

    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            picUri = uri
        }
    }


    Scaffold(
        topBar = {
            BackTopBar(
                onBackClick = {
                    navController.popBackStack()
                },
                topBarName = if (co == 0) "Create Group" else "Complete Create Group"
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

            if (co == 0) {
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
                    "Group Name " ,
                    textAlign = TextAlign.Start ,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                MainOutlinedTextField(
                    singleLine = false ,
                    imeAction = ImeAction.Done,
                    placeholder = "Enter your group name",
                    value = groupName ,
                    onValueChange = {
                        groupName = it
                    }
                )

                AppButton(
                    title = "Complete" ,
                    onClick = {
                        if (picUri==null) {
                            Toast.makeText(context , "Please select group picture" , Toast.LENGTH_LONG ).show()
                            return@AppButton
                        }

                        if (groupName.isBlank()) {
                            Toast.makeText(context , "Please enter a group name" , Toast.LENGTH_LONG ).show()
                            return@AppButton
                        }

                        picUri?.let {
                            ChatPrefs.setGroupCreateInfo(
                                context = context ,
                                imageUri = it,
                                groupName = groupName
                            )
                            navController.navigate(MainRoute.SELECT_GROUP_MEMBERS)
                        }

                    }
                )
            }else {

            }
        }
    }
}