package com.gulfappdeveloper.project3.presentation.screens.show_kot_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor

@Composable
fun DeleteKotAlertDialog(
    onDismissRequest: () -> Unit,
    rootViewModel: RootViewModel
) {
    val kotNumber by rootViewModel.kotMasterId
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "Cancel KOT",
                    color = MaterialTheme.colors.background,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    textDecoration = TextDecoration.Underline
                )
            }

        },
        text = {
            Text(
                text = "Do you want to delete KOT with number $kotNumber?",
                color = MaterialTheme.colors.background
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    rootViewModel.deleteKot()
                    onDismissRequest()

                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Magenta
                )
            ) {
                Text(text = "Ok")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(text = "No")
            }
        },
        backgroundColor = MaterialTheme.colors.error,
        contentColor = MaterialTheme.colors.MyPrimeColor,
        shape = MaterialTheme.shapes.medium.copy(all = CornerSize(10.dp))
    )
}