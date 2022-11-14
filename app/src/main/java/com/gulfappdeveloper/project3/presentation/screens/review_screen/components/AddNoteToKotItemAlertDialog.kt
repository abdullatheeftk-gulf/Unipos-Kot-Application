package com.gulfappdeveloper.project3.presentation.screens.review_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.domain.remote.post.KotItem
import com.gulfappdeveloper.project3.navigation.root.RootViewModel

@Composable
fun AddNoteToKotItemAlertDialog(
    onDismissRequest: () -> Unit,
    rootViewModel: RootViewModel,
    index:Int,
    kotItem: KotItem
) {
    var typedText by remember {
        mutableStateOf(kotItem.itemNote)
    }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        buttons = {
            Column(
                modifier = Modifier.padding(all = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                OutlinedTextField(
                    value = typedText ?: "",
                    onValueChange = {
                        typedText = it
                    },
                    placeholder = {
                        Text(text = "Add Note")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        rootViewModel.addNoteToKotItem(kotItem = kotItem, note = typedText ?: "", index = index)
                        onDismissRequest()
                    }
                ) {
                    Text(text = "Save")
                }
            }

        },
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
    )
}