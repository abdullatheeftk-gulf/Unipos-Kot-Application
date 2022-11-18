package com.gulfappdeveloper.project3.presentation.screens.table_selection_screen.components

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gulfappdeveloper.project3.domain.remote.get.TableOrder
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Table
import com.gulfappdeveloper.project3.navigation.root.RootViewModel

private const val TAG = "EditOrderNameAndChairDi"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditOrderNameAndChairDialog(
    onDismissRequest: () -> Unit,
    hideKeyboard: () -> Unit,
    tableOrder: TableOrder,
    rootViewModel: RootViewModel
) {
    val selectedTable: Table? by rootViewModel.selectedTable

    var orderName by remember {
        mutableStateOf("")
    }

    //orderName = tableOrder.orderName

    var chairSelected by remember {
        mutableStateOf(1)
    }

    // chairSelected = tableOrder.chairCount!!

    var noOfChairRemaining by remember {
        mutableStateOf(0)
    }



    Log.i(TAG, "chair remaining: $noOfChairRemaining \nchair selected $chairSelected")
    val interactionSource = remember {
        MutableInteractionSource()
    }

    LaunchedEffect(key1 = true) {
        orderName = tableOrder.orderName

        chairSelected = tableOrder.chairCount!!

        selectedTable?.let {
            noOfChairRemaining = it.noOfSeats - it.occupied
            if (tableOrder.fK_KOTInvoiceId ==0){
                noOfChairRemaining-=chairSelected
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        shape = MaterialTheme.shapes.medium.copy(all = CornerSize(4)),
        buttons = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Edit Order name and Chair Count",
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontStyle = MaterialTheme.typography.h6.fontStyle,
                    textDecoration = TextDecoration.Underline,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = orderName,
                    onValueChange = {
                        orderName = it
                    },
                    label = {
                        Text(text = "Change Order Name")
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            hideKeyboard()
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Chair Count :- ",
                        modifier = Modifier.weight(5f),
                        fontStyle = MaterialTheme.typography.h6.fontStyle,
                        fontSize = MaterialTheme.typography.h6.fontSize
                    )

                    IconButton(
                        onClick = {
                            if (chairSelected != 1) {
                                chairSelected--
                                noOfChairRemaining++
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "-",
                            fontSize = MaterialTheme.typography.h3.fontSize,
                            fontStyle = MaterialTheme.typography.h5.fontStyle
                        )
                    }
                    Box(modifier = Modifier.weight(2f)) {

                        TextFieldDefaults.OutlinedTextFieldDecorationBox(
                            value = chairSelected.toString(),
                            innerTextField = {
                                BasicTextField(
                                    value = chairSelected.toString(),
                                    onValueChange = {
                                        chairSelected = it.toInt()
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Done,
                                        keyboardType = KeyboardType.Number,
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            hideKeyboard()
                                        }
                                    ),
                                    textStyle = TextStyle(
                                        textAlign = TextAlign.Center,
                                        fontSize = 20.sp
                                    ),
                                    readOnly = true
                                )


                            },
                            enabled = true,
                            singleLine = true,
                            visualTransformation = VisualTransformation.None,
                            interactionSource = interactionSource,
                            contentPadding = PaddingValues(
                                vertical = 8.dp,
                                horizontal = 0.dp
                            )
                        )
                    }


                    IconButton(
                        onClick = {
                            if (noOfChairRemaining > 0) {
                                chairSelected++
                                noOfChairRemaining--
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "+",
                            fontSize = MaterialTheme.typography.h5.fontSize,
                            fontStyle = MaterialTheme.typography.h5.fontStyle
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {
                            onDismissRequest()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.error
                        )
                    ) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = {
                        if (tableOrder.fK_KOTInvoiceId == 0) {
                            rootViewModel.newOrderButtonClicked(
                                orderName = orderName,
                                noOfChairRequired = chairSelected
                            )
                        } else {
                            rootViewModel.editOrderNameAndChairCount(
                                id = tableOrder.fK_KOTInvoiceId,
                                orderName = orderName,
                                chairSelected = chairSelected,
                                tableId = tableOrder.fK_TableId
                            )
                        }

                        onDismissRequest()
                    }) {
                        Text(text = "Modify")
                    }
                }

            }
        }
    )
}


