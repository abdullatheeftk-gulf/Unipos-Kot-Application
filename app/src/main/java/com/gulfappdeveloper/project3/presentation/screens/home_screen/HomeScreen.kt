package com.gulfappdeveloper.project3.presentation.screens.home_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.navigation.root.RootNavScreens
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.screens.home_screen.components.MenuCard

private const val TAG = "HomeScreen"
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
    onScanButtonClicked: () -> Unit,
) {

    val scaffoldState = rememberScaffoldState()

    val sectionList = rootViewModel.sectionList
    val categoryList = rootViewModel.categoryList

    val context = LocalContext.current


    val menuList = listOf(
        Pair(first = R.drawable.table, "DINE IN"),
        Pair(first = R.drawable.take_away, "TAKE AWAY"),
        Pair(first = R.drawable.edit, "EDIT"),
        Pair(first = R.drawable.settings, "SETTINGS")
    )

   // Log.d(TAG, "HomeScreen: ")



    Scaffold(scaffoldState = scaffoldState) {
        it.calculateTopPadding()

        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(all = 10.dp)
        ) {

            items(menuList) { item: Pair<Int, String> ->
                MenuCard(
                    pair = item,
                    onMenuCardClicked = { menu ->
                        when (menu) {
                            "DINE IN" -> {
                                if (sectionList.isNotEmpty()) {
                                    navHostController.navigate(route = RootNavScreens.DineInScreen.route)
                                }else{
                                    Toast.makeText(context, "Please wait!. Data is loading", Toast.LENGTH_SHORT).show()
                                }
                            }
                            "TAKE AWAY" -> {
                                if (categoryList.isNotEmpty()) {
                                    navHostController.navigate(route = RootNavScreens.ProductDisplayScreen.route)
                                }else{
                                    Toast.makeText(context, "Please wait!. Data is loading", Toast.LENGTH_SHORT).show()
                                }
                            }
                            "EDIT" -> {

                            }
                            "SETTINGS" -> {

                            }
                        }
                    }
                )
            }

        }

    }

}