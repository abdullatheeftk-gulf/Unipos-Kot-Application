package com.gulfappdeveloper.project3.presentation.screens.dine_in_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.dine_in_screen.components.sections.SectionListRow
import com.gulfappdeveloper.project3.presentation.screens.dine_in_screen.components.tables.TableListDisplay
import com.gulfappdeveloper.project3.presentation.screens.dine_in_screen.components.util.DineInScreenEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DineInScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    hideKeyboard: () -> Unit,
) {

    val scaffoldState = rememberScaffoldState()

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var showEmptyList by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        rootViewModel.getTableList(value = 1,callFromDiningScreen = true)
        rootViewModel.dineInScreenEvent.collectLatest { value: DineInScreenEvent ->
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
                is UiEvent.ShowEmptyList -> {
                    showEmptyList = true
                }
                is UiEvent.ShowList -> {
                    showEmptyList = false
                }
                is UiEvent.Navigate -> {
                    navHostController.navigate(value.uiEvent.route)
                }
                else -> Unit
            }
        }
    }



    BackHandler(enabled = true) {
        rootViewModel.resetKot()
        navHostController.popBackStack()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Select Table")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        rootViewModel.resetKot()
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

        Column(modifier = Modifier.fillMaxSize()) {


            SectionListRow(rootViewModel = rootViewModel)

            Spacer(modifier = Modifier.height(15.dp))


            TableListDisplay(
                rootViewModel = rootViewModel,
                showProgressBar = showProgressBar,
                showEmptyList = showEmptyList
            )


        }


    }

}