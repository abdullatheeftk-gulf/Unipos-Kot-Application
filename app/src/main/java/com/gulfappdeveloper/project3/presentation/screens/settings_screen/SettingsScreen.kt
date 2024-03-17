package com.gulfappdeveloper.project3.presentation.screens.settings_screen

import android.util.Patterns
import android.webkit.URLUtil
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.navigation.root.RootNavScreens
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.ui.theme.ProgressBarColour
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

    val uniLicenseDetails by rootViewModel.uniLicenseDetails


    var showProgressBar by remember {
        mutableStateOf(false)
    }


    val currentBaseUrl by rootViewModel.baseUrl

    LaunchedEffect(key1 = true) {
        settingScreenViewModel.uiEvent.collectLatest { value: UiEvent ->
            when (value) {
                is UiEvent.Navigate -> {
                    rootViewModel.setIsInitialLoadingIsNotFinished()
                    //navHostController.backQueue.clear()
                    navHostController.popBackStack(route = RootNavScreens.HomeScreen.route,inclusive = true)
                    navHostController.navigate(route = RootNavScreens.LocalRegisterScreen.route)
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

    BackHandler(true) {
        if (!showProgressBar) {
            navHostController.popBackStack()
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
                        if (!showProgressBar) {
                            navHostController.popBackStack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            settingScreenViewModel.setLogout()
                        },
                    ) {
                        Text(
                            text = "LOGOUT",
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
                    Text(
                        text = currentBaseUrl,
                        modifier = Modifier.alpha(ContentAlpha.medium)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {

                        hideKeyboard()
                        if (urlValidator(baseUrl = text)) {
                            settingScreenViewModel.setBaseUrl(value = text)
                        } else {
                            settingScreenViewModel.onErrorUrl(url = text)
                        }
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = !showProgressBar
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {

                    hideKeyboard()
                    if (!showProgressBar) {
                        if (urlValidator(baseUrl = text)) {
                            settingScreenViewModel.setBaseUrl(value = text)
                        } else {
                            settingScreenViewModel.onErrorUrl(url = text)
                        }
                    }
                },
                enabled = !showProgressBar
            ) {
                Text(text = "Set Base Url")
            }
            Spacer(modifier = Modifier.height(30.dp))
            uniLicenseDetails?.let {uniLicense->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "App License")
                    Text(text = " : ")
                    Text(
                        text = uniLicense.licenseKey,
                        color = MaterialTheme.colors.ProgressBarColour
                    )

                }
            }

        }

        if (showProgressBar) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
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