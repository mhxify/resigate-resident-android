package com.secure.resident.main.presentation.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.secure.resident.core.presentation.component.PrimaryText

data class DialogItem(
    val name : String,
    val route : String ,
    val icon : ImageVector
)

object DialogRoute {
    const val RESERVE_FACILITIES = "reserve_facilities"
    const val SEND_REPORT = "send_report"
    const val ADD_POST = "add_post"
    const val INCIDENT = "incident"
    const val REQUEST_GUEST = "request_guest"
}

val dialogItems = listOf(
    DialogItem("Add Post", DialogRoute.ADD_POST, Icons.Default.Event),
    DialogItem("Send Report", DialogRoute.SEND_REPORT, Icons.Default.Description),
    DialogItem("Report Incident", DialogRoute.INCIDENT, Icons.Default.Warning),
    DialogItem("Reserve Facilities", DialogRoute.RESERVE_FACILITIES, Icons.Default.Event),
    DialogItem("Request Guest",DialogRoute.REQUEST_GUEST , Icons.Default.PersonAdd),
)


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDialogView(
    onDismiss : () -> Unit = {},
    onItemClicked : (DialogItem) -> Unit = {},
    items : List<DialogItem> = dialogItems
) {
    Dialog(
        onDismissRequest = onDismiss ,
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp) ,
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp) ,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(8.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Add ,
                    contentDescription = null ,
                    tint = MaterialTheme.colorScheme.primary ,
                    modifier = Modifier
                        .size(35.dp)
                )

                PrimaryText(
                    text = "Add Section"
                )
                
                items.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically ,
                        horizontalArrangement = Arrangement.spacedBy(6.dp) ,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {
                                onItemClicked(item)
                            }
                    ) {
                        Icon(
                            imageVector = item.icon ,
                            contentDescription = item.name ,
                            tint = MaterialTheme.colorScheme.primary
                        )

                        PrimaryText(
                            text = item.name ,
                            needBold = true
                        )
                    }
                }
            }
        }
    }
}