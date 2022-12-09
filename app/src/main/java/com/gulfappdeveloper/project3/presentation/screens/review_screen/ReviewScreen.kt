package com.gulfappdeveloper.project3.presentation.screens.review_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.domain.remote.post.KotItem
import com.gulfappdeveloper.project3.navigation.root.RootNavScreens
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.review_screen.components.*
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ReviewScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    deviceId: String
) {

    val scaffoldState = rememberScaffoldState()

    val kotItemList = rootViewModel.kotItemList


    val editMode by rootViewModel.editMode

    var showAddNoteToKotItemAlertDialog by remember {
        mutableStateOf(false)
    }

    val tableId by rootViewModel.tableId

    var kotItemSelectedForAddingNote: KotItem? by remember {
        mutableStateOf(null)
    }

    var kotItemIndexSelectedForAddingNote by remember {
        mutableStateOf(-1)
    }

    var showAddNoteToKotAlertDialog by remember {
        mutableStateOf(false)
    }

    var cancelKotAlertDialog by remember {
        mutableStateOf(false)
    }

    var kotSuccessAlertDialog by remember {
        mutableStateOf(false)
    }

    var showProgressBar by remember {
        mutableStateOf(false)
    }



    LaunchedEffect(key1 = true) {
        rootViewModel.reviewScreenEvent.collectLatest { event ->
            when (event.uiEvent) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }
                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }
                is UiEvent.ShowAlertDialog -> {
                    kotSuccessAlertDialog = true
                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiEvent.message,
                        duration = SnackbarDuration.Long
                    )
                }
                else -> Unit
            }
        }
    }

    kotItemSelectedForAddingNote?.let {
        if (showAddNoteToKotItemAlertDialog) {
            AddNoteToKotItemAlertDialog(
                onDismissRequest = {
                    showAddNoteToKotItemAlertDialog = false
                    kotItemSelectedForAddingNote = null
                },
                rootViewModel = rootViewModel,
                kotItem = it,
                index = kotItemIndexSelectedForAddingNote
            )
        }
    }

    if (showAddNoteToKotAlertDialog) {
        AddNoteToKotAlertDialog(
            rootViewModel = rootViewModel,
            onDismissRequest = {
                showAddNoteToKotAlertDialog = false
            }
        )
    }

    if (cancelKotAlertDialog) {
        CancelKotAlertDialog(
            onYesButtonClicked = {
                cancelKotAlertDialog = false
                // Log.e(TAG, "ReviewScreen: $editMode", )
                if (editMode) {

                    navHostController.navigate(
                        route = RootNavScreens.HomeScreen.route
                    ) {
                        popUpTo(route = RootNavScreens.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                } else {
                    navHostController.popBackStack(
                        route =
                        if (tableId == 0)
                            RootNavScreens.ProductDisplayScreen.route
                        else
                            RootNavScreens.DineInScreen.route,
                        inclusive = true
                    )
                }
                rootViewModel.resetKot()

                rootViewModel.onResetTableId()
            },
            onDismissRequest = {
                cancelKotAlertDialog = false
            }
        )
    }

    if (kotSuccessAlertDialog) {
        KotSuccessAlertDialog(rootViewModel = rootViewModel) {
            kotSuccessAlertDialog = false
            rootViewModel.resetKot()

            navHostController.navigate(route = RootNavScreens.HomeScreen.route) {
                popUpTo(route = RootNavScreens.HomeScreen.route) {
                    inclusive = true
                }
            }
            rootViewModel.onResetTableId()
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

    BackHandler(true) {
        if (editMode) {
            navHostController.navigate(
                route = RootNavScreens.HomeScreen.route
            ) {
                popUpTo(route = RootNavScreens.HomeScreen.route) {
                    inclusive = true
                }
            }
            rootViewModel.resetKot()
        } else {
            navHostController.popBackStack()
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Review Screen")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (editMode) {
                                navHostController.navigate(
                                    route = RootNavScreens.HomeScreen.route
                                ) {
                                    popUpTo(route = RootNavScreens.HomeScreen.route) {
                                        inclusive = true
                                    }
                                }
                                rootViewModel.resetKot()
                            } else {
                                navHostController.popBackStack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            showAddNoteToKotAlertDialog = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.Cyan
                        )
                    }
                    IconButton(onClick = {
                        cancelKotAlertDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Color.Yellow
                        )
                    }
                }
            )
        },
        modifier = Modifier.alpha(
            if (showProgressBar) ContentAlpha.disabled
            else ContentAlpha.high
        )
    ) {
        it.calculateTopPadding()

        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            /* items(kotItemList) { item: KotItem ->
                 KotItemsDisplay(
                     kotItem = item,
                     rootViewModel = rootViewModel,
                     onItemClicked = { kotItem ->
                         kotItemSelectedForAddingNote = kotItem
                         showAddNoteToKotItemAlertDialog = true
                     }
                 )
             }*/

            itemsIndexed(kotItemList) { index, item: KotItem ->
                KotItemsDisplay(
                    kotItem = item,
                    index = index,
                    rootViewModel = rootViewModel,
                    onItemClicked = { kotItem, i ->
                        kotItemSelectedForAddingNote = kotItem
                        kotItemIndexSelectedForAddingNote = i
                        showAddNoteToKotItemAlertDialog = true
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(300.dp))
            }

        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.MyPrimeColor,
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            ) {
                ReviewScreenBottomSheet(
                    rootViewModel = rootViewModel,
                    navHostController = navHostController,
                    deviceId = deviceId,
                    showProgressBar = showProgressBar
                )
            }

        }

    }


}