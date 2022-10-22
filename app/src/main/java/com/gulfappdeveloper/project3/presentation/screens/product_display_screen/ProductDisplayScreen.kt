package com.gulfappdeveloper.project3.presentation.screens.product_display_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.navigation.root.RootNavScreens
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.category.CategoryListRow
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.product.grid.GridViewScreen
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.product.list.ListViewScreen
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.util.ProductDisplayScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.util.ViewSwitcher
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProductDisplayScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel
) {
    val scaffoldState = rememberScaffoldState()

    val itemsCountInKot by rootViewModel.itemsCountInKot

    val netAmount by rootViewModel.kotNetAmount

    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var showEmptyList by remember {
        mutableStateOf(false)
    }

    var productListViewSwitcher by remember {
        mutableStateOf(ViewSwitcher.LIST)
    }

    LaunchedEffect(key1 = true) {
        rootViewModel.productDisplayScreenEvent.collectLatest { value: ProductDisplayScreenEvent ->
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
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(color = MaterialTheme.colors.background)) {
                                append("Net Amount : ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Yellow,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("$netAmount")
                            }

                        }
                    )
                },
                actions = {
                    IconButton(onClick = {
                        // ToDo
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BadgedBox(badge = {
                            Row {

                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = itemsCountInKot.toString(),
                                    color = Color.Yellow
                                )
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = null
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Review Order")
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }


                },
                onClick = {
                    if (itemsCountInKot == 0) {
                        rootViewModel.showSnackBarInProductDisplayScreen(message = "KOT list is empty")
                    } else {
                        navHostController.navigate(RootNavScreens.ReviewScreen.route)
                    }
                }
            )

        },
        floatingActionButtonPosition = FabPosition.Center

    ) {
        it.calculateTopPadding()

        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(modifier = Modifier.height(10.dp))

            CategoryListRow(rootViewModel = rootViewModel)

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                Icon(
                    modifier = Modifier.clickable {
                        productListViewSwitcher = ViewSwitcher.LIST
                    },
                    painter = painterResource(id = R.drawable.ic_list),
                    contentDescription = "",
                    tint = Color.DarkGray
                )

                Spacer(modifier = Modifier.width(5.dp))

                Icon(
                    modifier = Modifier.clickable {
                        productListViewSwitcher = ViewSwitcher.GRID
                    },
                    painter = painterResource(id = R.drawable.ic_grid_gray),
                    contentDescription = "grid",
                    tint = Color.DarkGray
                )
                Spacer(modifier = Modifier.width(5.dp))

            }


            if (productListViewSwitcher == ViewSwitcher.LIST) {
                ListViewScreen(
                    rootViewModel = rootViewModel,
                    showProgressBar = showProgressBar,
                    showEmptyList = showEmptyList
                )
            } else {
                GridViewScreen(
                    rootViewModel = rootViewModel,
                    showProgressBar = showProgressBar,
                    showEmptyList = showEmptyList
                )
            }


        }


    }


}