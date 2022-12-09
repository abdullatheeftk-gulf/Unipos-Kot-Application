package com.gulfappdeveloper.project3.presentation.screens.table_selection_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.TableOrder
import com.gulfappdeveloper.project3.navigation.root.RootViewModel

@Composable
fun OrderView(
    tableOrder: TableOrder,
    rootViewModel: RootViewModel,
    onEditButtonClicked: (TableOrder) -> Unit
) {


    var orderName by remember {
        mutableStateOf("")
    }
    orderName = tableOrder.orderName

    var chairCount: Int? by remember {
        mutableStateOf(0)
    }

    chairCount = tableOrder.chairCount




    Column(
        modifier = Modifier
            .width(128.dp)
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .clickable {
                if (tableOrder.id == 0) {
                    rootViewModel.onNewTableOrderSet()
                } else {
                    rootViewModel.onEditTableOrderSet(kotMasterId = tableOrder.fK_KOTInvoiceId)
                }

            }
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    color = if (tableOrder.id == 0)
                        Color(0xFF727272)
                    else
                        Color(0xFFF4BCA3)
                )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                Button(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(top = 8.dp, start = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF71ADE8)
                    ),
                    onClick = {
                        onEditButtonClicked(tableOrder)
                    },
                    shape = CircleShape
                ) {}
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "KOT id:- ${tableOrder.fK_KOTInvoiceId}"
                )
                Text(
                    text = orderName,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = MaterialTheme.typography.h6.fontWeight,
                    fontStyle = MaterialTheme.typography.h6.fontStyle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = "$chairCount seats")
            }

        }


    }

}