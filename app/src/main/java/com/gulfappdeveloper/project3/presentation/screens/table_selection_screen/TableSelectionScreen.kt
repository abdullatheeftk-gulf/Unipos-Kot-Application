package com.gulfappdeveloper.project3.presentation.screens.table_selection_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.domain.remote.get.TableOrder
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.table_selection_screen.components.OrderView
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TableSelectionScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel
) {

    val scaffoldState = rememberScaffoldState()

    val selectedTable by rootViewModel.selectedTable
    val tableOrderList = rootViewModel.tableOrderList

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        rootViewModel.tableSelectionUiEvent.collectLatest { value: TableSelectionUiEvent ->
            when (value.uiEvent) {
                is UiEvent.ShowProgressBar -> {

                }
                is UiEvent.CloseProgressBar -> {

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


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Table Order")
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
        }
    ) {
        it.calculateTopPadding()

        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            contentPadding = PaddingValues(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.Top
        ) {
            items(tableOrderList){item: TableOrder ->
                OrderView(
                    tableOrder = item,
                    rootViewModel = rootViewModel
                )

            }
        }


    }
}