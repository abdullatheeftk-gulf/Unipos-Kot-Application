package com.gulfappdeveloper.project3.presentation.screens.review_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.navigation.root.RootViewModel

@Composable
fun CancelKotAlertDialog(
    rootViewModel: RootViewModel,
    onDismissRequest: () -> Unit,
    onYesButtonClicked:()->Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        title = {
            Text(
                text = "Cancel KOT",
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                fontStyle = MaterialTheme.typography.h4.fontStyle,
                fontSize = MaterialTheme.typography.h5.fontSize,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = "Do you want to cancel KOT ?",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        },
        confirmButton = {
            Button(onClick = {

                onYesButtonClicked()
            }) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismissRequest()
            }) {
                Text(text = "No")
            }
        }
    )
}