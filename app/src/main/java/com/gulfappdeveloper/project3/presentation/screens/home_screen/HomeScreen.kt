package com.gulfappdeveloper.project3.presentation.screens.home_screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.navigation.root.RootNavScreens
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.presentation_util.OrderMode
import com.gulfappdeveloper.project3.presentation.screens.home_screen.components.MenuCard


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

    val uniLicenseDetails by rootViewModel.uniLicenseDetails


    val menuList = listOf(
        Pair(first = R.drawable.table, "DINE IN"),
        Pair(first = R.drawable.take_away, "TAKE AWAY"),
        Pair(first = R.drawable.edit, "EDIT"),
        Pair(first = R.drawable.settings, "SETTINGS")
    )



    Scaffold(scaffoldState = scaffoldState) {
        it.calculateTopPadding()
        if (uniLicenseDetails?.licenseType != "permanent") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "Demo Version",
                    color = MaterialTheme.colors.error,
                    fontStyle = MaterialTheme.typography.h6.fontStyle,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    textDecoration = TextDecoration.Underline
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Valid until :- ${uniLicenseDetails?.expiryDate}")


            }
        }



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
                                    rootViewModel.setOrderMode(OrderMode.DINE_IN)
                                    navHostController.navigate(route = RootNavScreens.DineInScreen.route)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please wait!. Data is loading",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            "TAKE AWAY" -> {
                                if (categoryList.isNotEmpty()) {
                                    rootViewModel.setOrderMode(OrderMode.TAKE_AWAY)
                                    navHostController.navigate(route = RootNavScreens.ProductDisplayScreen.route)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please wait!. Data is loading",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            "EDIT" -> {
                                rootViewModel.getListOfPendingKOTs()
                                rootViewModel.getKotCancelPrivilege()
                                navHostController.navigate(RootNavScreens.EditingScreen.route)
                            }
                            "SETTINGS" -> {
                                navHostController.navigate(RootNavScreens.SettingsScreen.route)
                            }
                        }
                    }
                )
            }

        }

    }

}