package com.gulfappdeveloper.project3.presentation.screens.auth_screens.local_auth_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.ui.theme.ProgressBarColour
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LocalRegisterScreen(
    rootViewModel: RootViewModel,
    navHostController: NavHostController,
    hideKeyboard: () -> Unit
) {

    val scaffoldState = rememberScaffoldState()


    var text by remember {
        mutableStateOf("")
    }

    val focusRequester by remember {
        mutableStateOf(FocusRequester())
    }

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var showPasswordToggle by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        focusRequester.requestFocus()
        rootViewModel.localRegisterEvent.collectLatest { value: UiEvent ->
            when (value) {
                is UiEvent.Navigate -> {
                    navHostController.popBackStack()
                    navHostController.navigate(value.route)
                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = value.message,
                        duration = SnackbarDuration.Long
                    )
                }
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }
                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
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
                    Text(text = "Login")
                },
            )
        }
    ) {
        it.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = text,
                trailingIcon = {
                    IconButton(onClick = {
                        showPasswordToggle = !showPasswordToggle
                    }) {
                        Icon(
                            painter = painterResource(
                                id = if (showPasswordToggle) R.drawable.ic_baseline_visibility_off_24 else R.drawable.ic_visibility
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colors.ProgressBarColour
                        )
                    }
                },
                visualTransformation = if (showPasswordToggle) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = { typedText ->
                    text = typedText
                },
                label = {
                    Text(text = "User Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard()
                        if (text.isNotEmpty()) {
                            rootViewModel.onRegisterLocally(
                              //  baseUrl = baseUrl,
                                password = text
                            )
                        } else {
                            rootViewModel.onErrorOnPassword()
                        }
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester = focusRequester),
                enabled = !showProgressBar
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    hideKeyboard()
                    if (text.isNotEmpty()) {
                        rootViewModel.onRegisterLocally(
                          //  baseUrl = baseUrl,
                            password = text
                        )
                    } else {
                        rootViewModel.onErrorOnPassword()
                    }

                },
                enabled = !showProgressBar
            ) {
                Text(text = "Login")
            }
        }
        if (showProgressBar) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

    }
}