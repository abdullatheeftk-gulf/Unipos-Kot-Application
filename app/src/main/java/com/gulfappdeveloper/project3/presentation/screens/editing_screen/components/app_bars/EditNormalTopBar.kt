package com.gulfappdeveloper.project3.presentation.screens.editing_screen.components.app_bars

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.navigation.root.RootViewModel

@Composable
fun EditNormalTopBar(
    rootViewModel: RootViewModel,
    onSearchButtonClicked: () -> Unit,
    navHostController: NavHostController
) {
    TopAppBar(
        title = {
            Text(text = "KOT List")
        },
        navigationIcon = {
            IconButton(onClick = {
                rootViewModel.resetKot()
                rootViewModel.removeUnOrderedTableOrder()
                navHostController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = {
                rootViewModel.getListOfPendingKOTs()
            }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = null
                )
            }
            IconButton(onClick = {
                onSearchButtonClicked()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )

            }
        }
    )
}