package com.gulfappdeveloper.project3.presentation.screens.home_screen

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.presentation.screens.home_screen.components.MenuCard

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    onScanButtonClicked: () -> Unit,
) {

    val scaffoldState = rememberScaffoldState()


    val menuList = listOf(
        Pair(first = R.drawable.table, "DINE IN"),
        Pair(first = R.drawable.take_away, "TAKE AWAY"),
        Pair(first = R.drawable.edit, "EDIT"),
        Pair(first = R.drawable.settings, "SETTINGS")
    )



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
                    onMenuCardClicked = {

                    }
                )
            }

        }

    }

}