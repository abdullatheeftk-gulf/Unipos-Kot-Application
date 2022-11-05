package com.gulfappdeveloper.project3.presentation.screens.table_selection_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.domain.remote.get.TableOrder
import com.gulfappdeveloper.project3.navigation.root.RootNavScreens
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.table_selection_screen.components.OrderView
import com.gulfappdeveloper.project3.ui.theme.ProgressBarColour
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TableSelectionScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    hideKeyboard:()->Unit
) {

    val scaffoldState = rememberScaffoldState()

    val selectedTable by rootViewModel.selectedTable
    val tableOrderList = rootViewModel.tableOrderList


    var showProgressBar by remember {
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

                }
                is UiEvent.ShowEmptyList -> {

                }
                is UiEvent.ShowList -> {

                }
                else -> Unit
            }
        }
    }

    BackHandler(true) {
        rootViewModel.removeTableOrder()
        navHostController.popBackStack()
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
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },

            )

        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Go to Product List")
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                },
                onClick = {
                    rootViewModel.onTableOrderSet()
                    navHostController.popBackStack()
                    navHostController.navigate(RootNavScreens.ProductDisplayScreen.route)
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
                    hideKeyboard = hideKeyboard
                )

            }
        }


    }
}