package com.gulfappdeveloper.project3.presentation.screens.review_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor

@Composable
fun KotSuccessAlertDialog(
    onDismissRequest: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = onDismissRequest,
        buttons = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "KOT generated Successfully!",
                    modifier = Modifier
                        .fillMaxWidth(),
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
        shape = RoundedCornerShape(size = 10.dp)
    )
}


