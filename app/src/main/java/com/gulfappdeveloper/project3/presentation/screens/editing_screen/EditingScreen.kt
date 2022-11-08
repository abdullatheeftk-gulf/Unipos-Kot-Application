package com.gulfappdeveloper.project3.presentation.screens.editing_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.ui.theme.ProgressBarColour
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun EditingScreen(
    rootViewModel: RootViewModel,
    navHostController: NavHostController,
    hideKeyboard: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    var kotMasterId by remember {
        mutableStateOf("")
    }

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var showNoItem by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        rootViewModel.editingScreenEvent.collectLatest { value ->
            when (value.uiEvent) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }
                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }
                is UiEvent.ShowEmptyList -> {
                    showNoItem = true
                }

                is UiEvent.Navigate -> {
                    navHostController.navigate(value.uiEvent.route)
                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(value.uiEvent.message)
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Search for KOT")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        rootViewModel.resetKot()
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        it.calculateTopPadding()

        if (showProgressBar) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    strokeWidth = 1.dp,
                    modifier = Modifier.size(30.dp),
                    color = MaterialTheme.colors.ProgressBarColour
                )
            }
        }
        Column(
            modifier = Modifier.padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = kotMasterId,
                onValueChange = { value ->
                    showNoItem = false
                    kotMasterId = value
                },
                label = {
                    Text(text = "Enter Kot Master Id")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        rootViewModel.getKOTDetails(
                            kotNumber = kotMasterId.toInt()
                        )
                        hideKeyboard()
                    }
                ),
                enabled = !showProgressBar,
                isError = showNoItem
            )
            if (showNoItem) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "No item with KOT id",
                        color = MaterialTheme.colors.error,
                        fontSize = MaterialTheme.typography.subtitle1.fontSize
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(14.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    var kotNumber = 0
                    try {
                        kotNumber = kotMasterId.toInt()
                        rootViewModel.getKOTDetails(
                            kotNumber = kotNumber
                        )
                        hideKeyboard()
                    } catch (e: Exception) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Enter kot master id")
                            return@launch
                        }
                    }

                },
                enabled = !showProgressBar
            ) {
                Text(text = "Search KOT")
            }
        }

    }
}