package com.gulfappdeveloper.project3.presentation.screens.review_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor

@Composable
fun KotSuccessAlertDialog(
    rootViewModel: RootViewModel,
    onDismissRequest: () -> Unit,
) {
    val editMode by rootViewModel.editMode

    AlertDialog(
        onDismissRequest = onDismissRequest,
        buttons = {
            Column(
                modifier = Modifier
                    //.fillMaxWidth(),
                    .padding(all = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (editMode) "KOT updated Successfully" else "KOT generated Successfully!",
                    modifier = Modifier
                        // .fillMaxWidth(),
                        .padding(horizontal = 10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontStyle = MaterialTheme.typography.h6.fontStyle,
                    color = MaterialTheme.colors.MyPrimeColor
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = onDismissRequest) {
                    Text(text = "Ok")
                }
            }
        },
        shape = RoundedCornerShape(percent = 4)
    )
}



