package com.gulfappdeveloper.project3.presentation.screens.splash_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.navigation.root.RootNavScreens
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor
import com.gulfappdeveloper.project3.ui.theme.ProgressBarColour
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "SplashScreen"
@Composable
fun SplashScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    splashScreenViewModel: SplashScreenViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()

    val welcomeMessage by rootViewModel.message

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var showUrlSetButton by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        rootViewModel.splashScreenEvent.collectLatest { event ->
            when (event.uiEvent) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }
                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }
                is UiEvent.Navigate -> {
                    navHostController.popBackStack()
                    navHostController.navigate(route = event.uiEvent.route)
                }
                is UiEvent.ShowButton1 -> {
                    showUrlSetButton = true
                }
                is UiEvent.ShowSnackBar->{
                    Log.d(TAG, "SplashScreen: ")
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiEvent.message,
                        duration = SnackbarDuration.Long
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        it.calculateTopPadding()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.manthy),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = welcomeMessage,
                fontStyle = MaterialTheme.typography.h4.fontStyle,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                color = MaterialTheme.colors.MyPrimeColor
            )
            Spacer(modifier = Modifier.height(50.dp))
            if (showProgressBar) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.ProgressBarColour,
                    strokeWidth = 1.dp,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        if (showUrlSetButton) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = {
                        navHostController.popBackStack()
                        navHostController.navigate(route = RootNavScreens.UrlSetScreen.route)
                    }
                ) {
                    Text(text = "Set Base Url")
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }


    }

}