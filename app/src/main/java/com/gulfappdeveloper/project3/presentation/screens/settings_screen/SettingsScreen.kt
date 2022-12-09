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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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
    
   /* var ipAddress by remember {
        mutableStateOf("")
    }
    
    var portAddress by remember {
        mutableStateOf("")
    }*/

    var showProgressBar by remember {
        mutableStateOf(false)
    }
    
   /* val ip by rootViewModel.ipAddress
    val port by rootViewModel.port
*/


    val currentBaseUrl by rootViewModel.baseUrl

    LaunchedEffect(key1 = true) {
      //  focusRequester.requestFocus()
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
                    .fillMaxWidth(),
                    //.focusRequester(focusRequester = focusRequester),
                enabled = !showProgressBar
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {

                    hideKeyboard()
                    if (!showProgressBar) {
                        if (urlValidator(baseUrl = text)) {
                            rootViewModel.setIsInitialLoadingIsNotFinished()
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
        /*    Text(
                text = "Set Printer Properties",
                textDecoration = TextDecoration.Underline,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontStyle = MaterialTheme.typography.h6.fontStyle,
                fontWeight = MaterialTheme.typography.h6.fontWeight
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = ipAddress, 
                    onValueChange = {typedValue->
                        ipAddress = typedValue
                    },
                    modifier = Modifier.weight(2f),
                    label = {
                        Text(text = "Ip Address")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            hideKeyboard()
                        }
                    ),
                    placeholder = {
                        Text(text = ip)
                    }
                )
                Spacer(modifier = Modifier.width(4.dp))
                OutlinedTextField(
                    value = portAddress,
                    onValueChange = {typedValue->
                        portAddress = typedValue
                    },
                    modifier = Modifier.weight(1f),
                    label = {
                        Text(text = "Port")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            hideKeyboard()
                        }
                    ),
                    placeholder = {
                        Text(text = port)
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                hideKeyboard()
                settingScreenViewModel.saveIpAddress(ipAddress = ipAddress)
                settingScreenViewModel.savePortAddress(portAddress = portAddress)
                rootViewModel.readPortAddress()
                rootViewModel.readIpAddress()
            }) {
               Text(text = "Save Printer Address")
            }
        }

        if (showProgressBar){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    strokeWidth = 1.dp,
                    color = MaterialTheme.colors.ProgressBarColour,
                )
            }*/
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