package com.gulfappdeveloper.project3.presentation.screens.editing_screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.navigation.root.RootNavScreens
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.editing_screen.components.KOTDetailsDisplay
import com.gulfappdeveloper.project3.presentation.screens.editing_screen.components.app_bars.EditNormalTopBar
import com.gulfappdeveloper.project3.presentation.screens.editing_screen.components.app_bars.EditSearchTopBar
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor
import com.gulfappdeveloper.project3.ui.theme.ProgressBarColour
import kotlinx.coroutines.flow.collectIndexed

private const val TAG = "EditingScreen"

@Composable
fun EditingScreen(
    rootViewModel: RootViewModel,
    navHostController: NavHostController,
    hideKeyboard: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()



    val kotPendingList = rootViewModel.kotPendingList


    var showProgressBar by remember {
        mutableStateOf(false)
    }

    var showEmptyList by remember {
        mutableStateOf(false)
    }

    var normalAndSearchTobBarToggle by remember {
        mutableStateOf(true)
    }

    // val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        //rootViewModel.getListOfPendingKOTs()
        rootViewModel.editingScreenEvent.collectIndexed { index, value ->
           // Log.e(TAG, "EditingScreen: $index")
            when (value.uiEvent) {
                is UiEvent.ShowProgressBar -> {
                    showProgressBar = true
                }
                is UiEvent.CloseProgressBar -> {
                    showProgressBar = false
                }
                is UiEvent.ShowEmptyList -> {
                    showEmptyList = true
                }

                is UiEvent.Navigate -> {
                    // Log.i(TAG, "EditingScreen: ${value.uiEvent.route}")

                    navHostController.navigate(RootNavScreens.ShowKotScreen.route)

                }
                is UiEvent.ShowList -> {
                    showEmptyList = false
                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(value.uiEvent.message)
                }
                else -> Unit
            }
        }
    }
    BackHandler(true) {
        rootViewModel.resetKot()
        rootViewModel.removeUnOrderedTableOrder()
        navHostController.popBackStack()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (normalAndSearchTobBarToggle) {
                EditNormalTopBar(
                    rootViewModel = rootViewModel,
                    onSearchButtonClicked = {
                        normalAndSearchTobBarToggle = false
                    },
                    navHostController = navHostController
                )
            } else {
                EditSearchTopBar(
                    rootViewModel = rootViewModel,
                    onClearButtonClicked = {
                        rootViewModel.getListOfPendingKOTs()
                        normalAndSearchTobBarToggle = true
                    },
                    hideKeyboard = hideKeyboard
                )
            }
        }
    ) {
        it.calculateTopPadding()

        if (showProgressBar) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    strokeWidth = 1.dp,
                    modifier = Modifier.size(30.dp),
                    color = MaterialTheme.colors.ProgressBarColour
                )
            }
        } else {
            if (showEmptyList) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_hourglass_empty),
                            contentDescription = null,
                            alpha = ContentAlpha.disabled,
                            modifier = Modifier.size(200.dp)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Empty List",
                            color = MaterialTheme.colors.MyPrimeColor,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.alpha(ContentAlpha.disabled)
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 128.dp),
                    contentPadding = PaddingValues(all = 8.dp),
                ) {
                    /*  item(
                          span = {
                              GridItemSpan(maxLineSpan)
                          }
                      ) {
                          Text(
                              text = "KOT Pending List",
                              color = MaterialTheme.colors.MyPrimeColor,
                              fontStyle = MaterialTheme.typography.h6.fontStyle,
                              textAlign = TextAlign.Center,
                              fontSize = MaterialTheme.typography.h6.fontSize,
                              textDecoration = TextDecoration.Underline,
                              modifier = Modifier.padding(bottom = 12.dp)
                          )
                      }*/
                    items(kotPendingList) { userOrder ->
                        KOTDetailsDisplay(
                            rootViewModel = rootViewModel,
                            userOrder = userOrder,
                            navHostController = navHostController
                        )
                    }
                }
            }
        }
        /*  Column(
              modifier = Modifier.padding(all = 16.dp),
              horizontalAlignment = Alignment.CenterHorizontally
          ) {
              OutlinedTextField(
                  modifier = Modifier.fillMaxWidth(),
                  value = kotMasterId,
                  onValueChange = { value ->
                      showNoItem = false
                      kotMasterId = value
                  },
                  label = {
                      Text(text = "Enter Kot Master Id")
                  },
                  keyboardOptions = KeyboardOptions(
                      imeAction = ImeAction.Done,
                      keyboardType = KeyboardType.Number
                  ),
                  keyboardActions = KeyboardActions(
                      onDone = {
                          rootViewModel.getKOTDetails(
                              kotNumber = kotMasterId.toInt(),
                              isOrderFromEditScreen = true
                          )
                          hideKeyboard()
                      }
                  ),
                  enabled = !showProgressBar,
                  isError = showNoItem
              )
              if (showNoItem) {
                  Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.Start
                  ) {
                      Spacer(modifier = Modifier.width(12.dp))
                      Text(
                          text = "No item with KOT id",
                          color = MaterialTheme.colors.error,
                          fontSize = MaterialTheme.typography.subtitle1.fontSize
                      )
                  }
              } else {
                  Spacer(modifier = Modifier.height(14.dp))
              }

              Spacer(modifier = Modifier.height(8.dp))

              Button(
                  onClick = {
                      var kotNumber = 0
                      try {
                          kotNumber = kotMasterId.toInt()
                          rootViewModel.getKOTDetails(
                              kotNumber = kotNumber,
                              isOrderFromEditScreen = true
                          )
                          hideKeyboard()
                      } catch (e: Exception) {
                          scope.launch {
                              scaffoldState.snackbarHostState.showSnackbar("Enter kot master id")
                              return@launch
                          }
                      }

                  },
                  enabled = !showProgressBar
              ) {
                  Text(text = "Search KOT")
              }
          }*/

    }
}