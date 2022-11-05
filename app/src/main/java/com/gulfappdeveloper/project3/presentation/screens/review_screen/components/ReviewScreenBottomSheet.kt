package com.gulfappdeveloper.project3.presentation.screens.review_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor
import com.gulfappdeveloper.project3.ui.theme.ProgressBarColour

@Composable
fun ReviewScreenBottomSheet(
    rootViewModel: RootViewModel,
    navHostController: NavHostController,
    deviceId: String,
    showProgressBar: Boolean
) {
    val itemCountInKot by rootViewModel.itemsCountInKot
    val kotNetAmount by rootViewModel.kotNetAmount

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "No of items in KOT",
                modifier = Modifier.weight(4f),
                fontSize = 18.sp,
                fontStyle = MaterialTheme.typography.h4.fontStyle
            )
            Text(
                text = ":",
                modifier = Modifier.weight(0.5f),
                fontSize = 20.sp,
                fontStyle = MaterialTheme.typography.h4.fontStyle
            )
            Text(
                text = "$itemCountInKot",
                modifier = Modifier.weight(2f),
                fontSize = 20.sp,
                fontStyle = MaterialTheme.typography.h4.fontStyle,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "KOT Net Amount",
                modifier = Modifier.weight(4f),
                fontSize = 20.sp,
                fontStyle = MaterialTheme.typography.h4.fontStyle
            )
            Text(
                text = ":",
                modifier = Modifier.weight(0.5f),
                fontSize = 20.sp,
                fontStyle = MaterialTheme.typography.h4.fontStyle
            )
            Text(
                text = "$kotNetAmount",
                modifier = Modifier.weight(2f),
                fontSize = 20.sp,
                fontStyle = MaterialTheme.typography.h4.fontStyle,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    navHostController.popBackStack()
                },
                modifier = Modifier.weight(4f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                ),
                enabled = !showProgressBar
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = MaterialTheme.colors.MyPrimeColor
                )
                Text(
                    text = "Add More Item",
                    color = MaterialTheme.colors.ProgressBarColour
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = {
                    rootViewModel.generateKot(deviceId = deviceId)
                },
                modifier = Modifier.weight(4f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.error
                ),
                enabled = !showProgressBar
            ) {
                Text(text = "Generate KOT", color = MaterialTheme.colors.background)
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colors.MyPrimeColor
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }


}