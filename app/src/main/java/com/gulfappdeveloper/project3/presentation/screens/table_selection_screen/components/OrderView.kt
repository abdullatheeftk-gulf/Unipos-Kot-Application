package com.gulfappdeveloper.project3.presentation.screens.table_selection_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.domain.remote.get.TableOrder
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Table
import com.gulfappdeveloper.project3.navigation.root.RootViewModel

private const val TAG = "OrderView"

@Composable
fun OrderView(
    tableOrder: TableOrder,
    rootViewModel: RootViewModel,
    hideKeyboard: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    val selectedTable by rootViewModel.selectedTable

    var chairRemaining by remember {
        mutableStateOf(0)
    }

    chairRemaining = selectedTable?.noOfSeats!! - selectedTable?.occupied!!


    var colorChange by remember {
        mutableStateOf(false)
    }

    var orderName by remember {
        mutableStateOf("")
    }
    orderName = tableOrder.orderName

    var chairCount: Int? by remember {
        mutableStateOf(0)
    }

    chairCount = tableOrder.chairCount


    var showDropDownMenu by remember {
        mutableStateOf(false)
    }

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
                .height(128.dp)
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
                        .size(20.dp)
                        .padding(top = 8.dp, start = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (colorChange)
                            Color.Blue
                        else
                            Color.White
                    ),
                    onClick = {
                        colorChange = !colorChange
                        focusManager.clearFocus()
                    },
                    shape = CircleShape
                ) {}
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = orderName)
                Text(text = "Order Id:- ${tableOrder.fK_KOTInvoiceId}")
            }

        }

        Surface(color = Color(0xFFF6D78B)) {
            BasicTextField(
                value = orderName,
                onValueChange = { value ->
                    orderName = value
                    rootViewModel.onOrderNameChange(value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                readOnly = !colorChange,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard()
                        focusManager.clearFocus()
                    }
                )
            )
        }
        Surface(color = Color(0xFF5FE3F0)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = chairCount.toString()
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colors.error,
                    modifier = if (colorChange)
                        Modifier.clickable {
                            showDropDownMenu = true
                        }
                    else
                        Modifier
                )
            }
            DropdownMenu(
                expanded = showDropDownMenu,
                onDismissRequest = { showDropDownMenu = false }
            ) {
                if (selectedTable?.occupied!! == 0) {
                    (1..chairRemaining).forEach {
                        DropdownMenuItem(
                            onClick = {
                                rootViewModel.onChairCountChange(it + chairCount!!)
                                chairCount = it + chairCount!!
                                chairRemaining -= it
                                showDropDownMenu = false
                            }) {
                            Text(text = "$it")
                        }
                    }
                } else if (selectedTable?.occupied!! >= selectedTable?.noOfSeats!!) {
                    DropdownMenuItem(onClick = {
                        showDropDownMenu = false
                    }) {
                        Text(text = "No empty seat")
                    }
                } else {
                    if (tableOrder.id == 0) {
                        (1..chairRemaining).forEach {
                            DropdownMenuItem(
                                onClick = {
                                    rootViewModel.onChairCountChange(it)
                                    chairCount = it
                                    showDropDownMenu = false
                                }) {
                                Text(text = "$it")
                            }
                        }
                    } else {
                        (1..chairRemaining + 1).forEach {
                            DropdownMenuItem(
                                onClick = {
                                    rootViewModel.onChairCountChange(it)
                                    chairCount = it
                                    showDropDownMenu = false
                                }) {
                                Text(text = "$it")
                            }
                        }
                    }
                }
            }
        }


    }


}