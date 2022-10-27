package com.gulfappdeveloper.project3.presentation.screens.table_selection_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.domain.remote.get.TableOrder
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.ui.theme.CategoryBackGroundClicked
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor

@Composable
fun OrderView(
    tableOrder: TableOrder,
    rootViewModel: RootViewModel
) {

    Card(
        modifier = Modifier
            .size(128.dp)
            .padding(10.dp),
        backgroundColor = MaterialTheme.colors.CategoryBackGroundClicked
    ) {
        Column(modifier = Modifier.border(1.dp, color = MaterialTheme.colors.error)) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .padding(horizontal = 10.dp)
                    .background(color = MaterialTheme.colors.background),
                )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = tableOrder.orderName)
            }
        }
    }
}