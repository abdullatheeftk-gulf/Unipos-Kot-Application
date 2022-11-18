package com.gulfappdeveloper.project3.presentation.screens.table_selection_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.domain.remote.get.TableOrder
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.table_selection_screen.components.*
import com.gulfappdeveloper.project3.ui.theme.ProgressBarColour
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TableSelectionScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    hideKeyboard: () -> Unit
) {

    val scaffoldState = rememberScaffoldState()

    val selectedTable by rootViewModel.selectedTable

    var selectedOrder:TableOrder? by remember {
        mutableStateOf(null)
    }
    val tableOrderList = rootViewModel.tableOrderList

    val showNewOrderAddButton by rootViewModel.showNewTableOrderAddButton


    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var showCompletedEditOnAndCc by remember {
        mutableStateOf(false)
    }

    var showAddNewOrderDialog by remember {
        mutableStateOf(false)
    }

    var showEditOrderNameAndChairCountDialog by remember {
        mutableStateOf(false)
    }



    LaunchedEffect(key1 = true) {
        rootViewModel.onSelectedTable()
        rootViewModel.tableSelectionUiEvent.collectLatest { value: TableSelectionUiEvent ->
            when (value.uiEvent) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }
                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = value.uiEvent.message)
                }
                is UiEvent.Navigate -> {
                    //navHostController.popBackStack()
                    navHostController.navigate(route = value.uiEvent.route)
                }
                is UiEvent.ShowAlertDialog-> {
                    showCompletedEditOnAndCc = true
                }

                else -> Unit
            }
        }
    }

    BackHandler(true) {
        rootViewModel.removeTableOrder()
        navHostController.popBackStack()
    }

    if (showAddNewOrderDialog) {
        AddNewOrderDialog(
            rootViewModel = rootViewModel,
            hideKeyboard = hideKeyboard,
            noOfSeatsRemaining = selectedTable?.noOfSeats!! - selectedTable?.occupied!!,
            onDismissRequest = {
                showAddNewOrderDialog = false
            }
        )
    }

    if (showEditOrderNameAndChairCountDialog) {
        EditOrderNameAndChairDialog(
            onDismissRequest = {
                showEditOrderNameAndChairCountDialog = false
            },
            hideKeyboard = hideKeyboard,
            rootViewModel = rootViewModel,
            tableOrder = selectedOrder!!
        )
    }

    if (showCompletedEditOnAndCc){
        UpdatedOdAndCcDialog {
            showCompletedEditOnAndCc = false
        }
    }




    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Table ${selectedTable?.tableName} Orders")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                        rootViewModel.removeTableOrder()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            )
        },

        ) {
        it.calculateTopPadding()

        if (showProgressBar) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    strokeWidth = 1.dp,
                    color = MaterialTheme.colors.ProgressBarColour
                )
            }
            return@Scaffold
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(128.dp),
            contentPadding = PaddingValues(all = 12.dp),

            ) {
            items(tableOrderList) { item: TableOrder ->
                OrderView(
                    tableOrder = item,
                    rootViewModel = rootViewModel,
                    onEditButtonClicked = {tableOrder->
                        selectedOrder = tableOrder
                        showEditOrderNameAndChairCountDialog = true
                    }
                )

            }
            if (showNewOrderAddButton) {
                item {
                    AddOrderView(
                        rootViewModel = rootViewModel,
                        addNewOrder = {
                            showAddNewOrderDialog = true
                        }
                    )
                }
            }
        }


    }
}