package com.gulfappdeveloper.project3.presentation.screens.settings_screen

import android.util.Patterns
import android.webkit.URLUtil
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsScreen(
    rootViewModel: RootViewModel,
    hideKeyboard: () -> Unit,
    navHostController: NavHostController,
    settingScreenViewModel: SettingScreenViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()

    var text by remember {
        mutableStateOf("")
    }

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    val focusRequester by remember {
        mutableStateOf(FocusRequester())
    }

    val currentBaseUrl by rootViewModel.baseUrl

    LaunchedEffect(key1 = true) {
        focusRequester.requestFocus()
        settingScreenViewModel.uiEvent.collectLatest { value: UiEvent ->
            when (value) {
                is UiEvent.Navigate -> {
                    navHostController.popBackStack()

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
                    Text(text = "Settings")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },

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
                onValueChange = { typedText ->
                    text = typedText
                },
                placeholder = {
                    Text(text = currentBaseUrl)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {

                        hideKeyboard()
                        if (urlValidator(baseUrl = text)) {
                            rootViewModel.setIsInitialLoadingIsNotFinished()
                            settingScreenViewModel.setBaseUrl(value = text)
                        } else {
                            settingScreenViewModel.onErrorUrl(url = text)
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
                    if (urlValidator(baseUrl = text)) {
                        rootViewModel.setIsInitialLoadingIsNotFinished()
                        settingScreenViewModel.setBaseUrl(value = text)
                    } else {
                        settingScreenViewModel.onErrorUrl(url = text)
                    }
                },
                enabled = !showProgressBar
            ) {
                Text(text = "Set Base Url")
            }
        }

    }

}

private fun urlValidator(baseUrl: String): Boolean {
    return try {
        URLUtil.isValidUrl(baseUrl) && Patterns.WEB_URL.matcher(baseUrl).matches()
    } catch (e: Exception) {
        false
    }
}