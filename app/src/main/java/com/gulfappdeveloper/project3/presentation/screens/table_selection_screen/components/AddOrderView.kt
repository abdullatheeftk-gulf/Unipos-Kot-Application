package com.gulfappdeveloper.project3.presentation.screens.table_selection_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.navigation.root.RootViewModel

@Composable
fun AddOrderView(
    rootViewModel: RootViewModel,
    addNewOrder:()->Unit
) {
    Column(
        modifier = Modifier
            .width(128.dp)
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .background(Color(0xFFDDDDDD))
            .clickable {
                addNewOrder()
            }
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(188.dp)

        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    tint = Color(0xFF8C8787)
                )  
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "New order")
            }
            
        }
    }
}