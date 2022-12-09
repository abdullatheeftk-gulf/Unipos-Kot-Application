package com.gulfappdeveloper.project3.presentation.screens.product_display_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.navigation.root.RootNavScreens
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.OrderMode
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.alert_dialogs.MultiSizeAlertDialog
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.category.CategoryListRow
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.product.grid.GridViewScreen
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.product.list.ListViewScreen
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.topbar.NormalTopBar
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.topbar.SearchTopBar
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.util.ProductDisplayScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.util.ViewSwitcher
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProductDisplayScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    hideKeyboard: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    val itemsCountInKot by rootViewModel.itemsCountInKot

    val selectedOrderMode by rootViewModel.selectedOrderMode

    val multiSizeProductList = rootViewModel.multiSizeProductList

    val editMode by rootViewModel.editMode

    var multiSizeProductId by remember {
        mutableStateOf(-1)
    }

    var selectedIndex by remember {
        mutableStateOf(-1)
    }

    var showProgressBarInItem by remember {
        mutableStateOf(false)
    }

    var multiSizeCategoryId by remember {
        mutableStateOf(-1)
    }

    var showAlertDialog by remember {
        mutableStateOf(false)
    }


    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var showEmptyList by remember {
        mutableStateOf(false)
    }

    var productListViewSwitcher by remember {
        mutableStateOf(ViewSwitcher.LIST)
    }

    var normalAndSearchTobBarToggle by remember {
        mutableStateOf(true)
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

    if (showAlertDialog) {
        showProgressBarInItem = true
        if (multiSizeProductList.isNotEmpty()) {

            MultiSizeAlertDialog(
                rootViewModel = rootViewModel,
                onDismissRequest = {
                    showProgressBarInItem = false
                    showAlertDialog = false
                },
                multiSizeProductList = multiSizeProductList,
                parentCategoryId = multiSizeCategoryId
            )
        }
    }

    BackHandler(enabled = true) {
        if (editMode) {
            navHostController.popBackStack()
        } else {
            if (selectedOrderMode == OrderMode.TAKE_AWAY) {
                rootViewModel.resetKot()
                navHostController.popBackStack()
            } else {
                navHostController.popBackStack()
                rootViewModel.removeUnOrderedTableOrder()
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (normalAndSearchTobBarToggle) {
                NormalTopBar(
                    rootViewModel = rootViewModel,
                    onSearchButtonClicked = {
                        normalAndSearchTobBarToggle = false
                    },
                    onBackButtonClicked = {
                        if (editMode) {
                            navHostController.popBackStack()
                        } else {
                            if (selectedOrderMode == OrderMode.TAKE_AWAY) {
                                rootViewModel.resetKot()
                                navHostController.popBackStack()
                            } else {
                                navHostController.popBackStack()
                                rootViewModel.removeUnOrderedTableOrder()
                            }
                        }
                    }
                )
            } else {
                SearchTopBar(
                    rootViewModel = rootViewModel,
                    onClearButtonClicked = {
                        normalAndSearchTobBarToggle = true
                    },
                    hideKeyboard = hideKeyboard
                )
            }
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

            CategoryListRow(
                rootViewModel = rootViewModel,
                onCategoryItemClicked = {
                    normalAndSearchTobBarToggle = true
                }
            )

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
                    showEmptyList = showEmptyList,
                    selectedIndex = selectedIndex,
                    showProgressBarInItem = showProgressBarInItem,
                    openMultiSizeProduct = { productId, categoryId, index ->
                        multiSizeProductId = productId
                        selectedIndex = index
                        rootViewModel.getMultiSizeProduct(id = productId)
                        multiSizeCategoryId = categoryId
                        showAlertDialog = true
                    }
                )
            } else {
                GridViewScreen(
                    rootViewModel = rootViewModel,
                    showProgressBar = showProgressBar,
                    showEmptyList = showEmptyList,
                    openMultiSizeProduct = { productId, categoryId, index ->
                        multiSizeProductId = productId
                        selectedIndex = index
                        rootViewModel.getMultiSizeProduct(id = productId)
                        multiSizeCategoryId = categoryId
                        showAlertDialog = true
                    },
                    selectedIndex = selectedIndex,
                    showProgressBarInItem = showProgressBarInItem
                )
            }


        }


    }


}