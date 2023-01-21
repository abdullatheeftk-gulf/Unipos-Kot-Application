package com.gulfappdeveloper.project3.presentation.screens.uni_license_act_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.domain.datastore.UniLicenseDetails
import com.gulfappdeveloper.project3.navigation.root.RootNavScreens
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.uni_license_act_screen.components.LicenseInformationDisplayAlertDialog
import com.gulfappdeveloper.project3.presentation.screens.uni_license_act_screen.util.UniLicenseActScreenEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun UniLicenseActScreen(
    rootViewModel: RootViewModel,
    deviceId: String,
    hideKeyboard: () -> Unit,
    navHostController: NavHostController
) {
    val scaffoldState = rememberScaffoldState()

    var licenseKeyText by remember {
        mutableStateOf("")
    }

    val uniLicense by rootViewModel.uniLicenseDetails
    val licenseKeyActivationError by rootViewModel.licenseKeyActivationError

    val deviceIdFromDataStore by rootViewModel.deviceIdState

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var showAlertDialog by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    val focusRequester by remember {
        mutableStateOf(FocusRequester())
    }

    LaunchedEffect(key1 = true) {

        focusRequester.requestFocus()

        rootViewModel.uniLicenseActScreenEvent.collectLatest { value: UniLicenseActScreenEvent ->
            when (value.uiEvent) {
                is UiEvent.ShowAlertDialog -> {
                    showAlertDialog = true
                }
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }
                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(value.uiEvent.message)
                }
                else -> Unit
            }
        }
    }

    if (showAlertDialog) {
        LicenseInformationDisplayAlertDialog(
            onDismissRequest = {
                navHostController.popBackStack()
                navHostController.navigate(route = RootNavScreens.LocalRegisterScreen.route)
            },
            onLicenseExpired = {
                showAlertDialog = false
            },
            uniLicense = uniLicense
        )
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = {
                Text("Activate App")
            })
        }
    ) {
        it.calculateTopPadding()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(15.dp))

            Row {
                Text(
                    text = "Device Id:-   ",
                    fontStyle = MaterialTheme.typography.h5.fontStyle,
                    fontSize = 20.sp
                )
                SelectionContainer() {
                    Text(
                        text = deviceIdFromDataStore.ifEmpty { deviceId },
                        fontStyle = MaterialTheme.typography.h5.fontStyle,
                        fontSize = 20.sp,
                    )
                }
            }
            uniLicense?.let { uniLicenseDetails: UniLicenseDetails ->
                Row {
                    Text(
                        text = "App License:-   ",
                        fontStyle = MaterialTheme.typography.h5.fontStyle,
                        fontSize = 20.sp
                    )
                    SelectionContainer() {
                        Text(
                            text = uniLicenseDetails.licenseKey,
                            fontStyle = MaterialTheme.typography.h5.fontStyle,
                            fontSize = 20.sp,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = licenseKeyText,
                onValueChange = { text ->
                    licenseKeyText = text
                    rootViewModel.setUnitActivationErrorValue("")
                },
                label = {
                    Text(text = "Enter License Key")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .focusRequester(focusRequester = focusRequester),
                isError = licenseKeyActivationError.isNotBlank() || licenseKeyActivationError.isNotEmpty(),
                keyboardActions = KeyboardActions(onDone = {
                    hideKeyboard()
                    if (!licenseKeyValidation(licenseKeyText)) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Invalid License")
                        }
                        return@KeyboardActions
                    }


                    rootViewModel.uniLicenseActivation(
                        deviceId = deviceId,
                        licenseKey = licenseKeyText
                    )
                }),
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp)
            ) {
                if (licenseKeyActivationError.isNotBlank() || licenseKeyActivationError.isNotEmpty()) {
                    Text(
                        text = if (licenseKeyActivationError == "Expired License") "Expired License" else "Bad Request",
                        color = MaterialTheme.colors.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    hideKeyboard()
                    if (!licenseKeyValidation(licenseKeyText)) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Invalid License")
                        }
                        return@Button
                    }


                    rootViewModel.uniLicenseActivation(
                        deviceId = deviceId,
                        licenseKey = licenseKeyText
                    )

                },
                enabled = !showProgressBar
            ) {
                Text(text = "Activate")
            }

        }

        if (showProgressBar) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

    }
}

private fun licenseKeyValidation(value: String): Boolean {
    return value.startsWith('M', ignoreCase = false)
}