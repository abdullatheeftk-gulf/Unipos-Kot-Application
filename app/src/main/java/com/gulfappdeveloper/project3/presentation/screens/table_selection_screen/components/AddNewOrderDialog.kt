package com.gulfappdeveloper.project3.presentation.screens.table_selection_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor

@Composable
fun AddNewOrderDialog(
    rootViewModel: RootViewModel,
    hideKeyboard: () -> Unit,
    noOfSeatsRemaining: Int,
    onDismissRequest: () -> Unit
) {

    var textValue by remember {
        mutableStateOf("New Order")
    }

    var onOfSeatsRequired by remember {
        mutableStateOf(1)
    }

    var expandDropdownMenu by remember {
        mutableStateOf(false)
    }

    var showError by remember {
        mutableStateOf(false)
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(4),
        buttons = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(all = 10.dp)
            ) {
                Text(
                    text = "Add Order",
                    color = MaterialTheme.colors.MyPrimeColor,
                    textDecoration = TextDecoration.Underline,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontStyle = MaterialTheme.typography.h6.fontStyle
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = textValue,
                    onValueChange = { value ->
                        showError = false
                        textValue = value
                    },
                    label = {
                        Text(text = "Order name")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            hideKeyboard()
                        }
                    ),
                    isError = showError
                )
                if (showError) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Order name is empty",
                            color = MaterialTheme.colors.error
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                } else {
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "No of seats required",
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    Box() {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray
                                )
                                .padding(all = 12.dp)
                        ) {
                            Text(
                                text = onOfSeatsRequired.toString(),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = null,
                                tint = MaterialTheme.colors.error,
                                modifier = Modifier.clickable {
                                    expandDropdownMenu = true
                                }
                            )
                        }
                        DropdownMenu(
                            expanded = expandDropdownMenu,
                            onDismissRequest = {
                                expandDropdownMenu = false
                            },
                            modifier = Modifier.width(80.dp),
                        ) {
                            (1..noOfSeatsRemaining).forEach { seatNumber ->
                                DropdownMenuItem(
                                    onClick = {
                                        onOfSeatsRequired = seatNumber
                                        expandDropdownMenu = false
                                    },
                                    modifier = Modifier.height(25.dp)

                                ) {
                                    Text(
                                        text = seatNumber.toString(),
                                    )
                                }
                            }

                        }
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        onDismissRequest()
                    }) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            if (textValue.isNotEmpty()) {
                                rootViewModel.newOrderButtonClicked(
                                    orderName = textValue,
                                    noOfChairRequired = onOfSeatsRequired
                                )
                                onDismissRequest()
                            } else {
                                showError = true
                            }
                        }) {
                        Text(text = "Ok")
                    }
                }
            }
        }
    )

}