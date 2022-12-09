package com.gulfappdeveloper.project3.presentation.screens.show_kot_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.navigation.root.RootNavScreens
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.show_kot_screen.components.DeleteKotAlertDialog
import com.gulfappdeveloper.project3.presentation.screens.show_kot_screen.components.KotItemDisplay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ShowKOTScreen(
    rootViewModel: RootViewModel,
    navHostController: NavHostController,
) {
    val scaffoldState = rememberScaffoldState()

    val kotItemsList = rootViewModel.kotItemList

    val kotMasterId by rootViewModel.kotMasterId

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var showKOTDeleteAlertDialog by remember {
        mutableStateOf(false)
    }

    val kotCancelPrivilege by rootViewModel.kotCancelPrivilege

    LaunchedEffect(key1 = true) {

        rootViewModel.showKotScreenUiEvent.collectLatest { value ->
            when (value.uiEvent) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }
                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }
                is UiEvent.Navigate -> {

                    navHostController.navigate(
                        route = RootNavScreens.HomeScreen.route
                    ) {
                        popUpTo(route = RootNavScreens.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                    rootViewModel.resetKot()

                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = value.uiEvent.message)
                }
                else -> Unit
            }
        }
    }

    if (showKOTDeleteAlertDialog) {
        DeleteKotAlertDialog(
            onDismissRequest = {
                showKOTDeleteAlertDialog = false
            },
            rootViewModel = rootViewModel
        )

    }
    BackHandler(true) {
        rootViewModel.removeUnOrderedTableOrder()
        navHostController.popBackStack()

    }



    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "KOT Number:- $kotMasterId")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            rootViewModel.removeUnOrderedTableOrder()
                            navHostController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
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
                CircularProgressIndicator()
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.weight(10f)
            ) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Si",
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 5.dp),
                            textAlign = TextAlign.Start,
                            textDecoration = TextDecoration.Underline,
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontStyle = MaterialTheme.typography.h6.fontStyle
                        )
                        Text(
                            text = "Product Name",
                            modifier = Modifier
                                .weight(7f)
                                .padding(bottom = 5.dp),
                            textAlign = TextAlign.Start,
                            textDecoration = TextDecoration.Underline,
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontStyle = MaterialTheme.typography.h6.fontStyle
                        )
                        Text(
                            text = "Quantity",
                            modifier = Modifier
                                .weight(4f)
                                .padding(bottom = 5.dp),
                            textAlign = TextAlign.Center,
                            textDecoration = TextDecoration.Underline,
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontStyle = MaterialTheme.typography.h6.fontStyle
                        )
                    }
                }

                /*item {

                    Canvas(modifier = Modifier) {
                        drawLine(
                            color = Color.Black,
                            start = Offset(x = 0f, y = 0f),
                            end = Offset.Infinite
                        )
                    }
                }*/
                itemsIndexed(kotItemsList) { index, kotItem ->
                    KotItemDisplay(kotItem = kotItem, index = index)
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }

            }

        }








        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        showKOTDeleteAlertDialog = true
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.error
                    ),
                    enabled = if (kotCancelPrivilege) !showProgressBar else false

                ) {
                    Text(text = "Cancel Kot")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        rootViewModel.setEditMode(value = true)
                        navHostController.popBackStack(
                            route = RootNavScreens.EditingScreen.route,
                            inclusive = true
                        )
                        navHostController.navigate(route = RootNavScreens.ReviewScreen.route)
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !showProgressBar
                ) {
                    Text(text = "Edit Kot")
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }


    }


}