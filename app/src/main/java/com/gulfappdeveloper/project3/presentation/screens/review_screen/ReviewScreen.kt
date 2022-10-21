package com.gulfappdeveloper.project3.presentation.screens.review_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.domain.remote.post.KotItem
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.screens.review_screen.components.KotItemsDisplay
import com.gulfappdeveloper.project3.presentation.screens.review_screen.components.ReviewScreenBottomSheet
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor

@Composable
fun ReviewScreen(
    navHostController: NavHostController,
    rootViewModel: RootViewModel
) {

    val scaffoldState = rememberScaffoldState()

    val kotItemList = rootViewModel.kotItemList

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Review Screen")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navHostController.popBackStack()
                        }
                    ) {
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

        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(kotItemList) { item: KotItem ->
                KotItemsDisplay(
                    kotItem = item,
                    rootViewModel = rootViewModel
                )
            }

            item {
                Spacer(modifier = Modifier.height(300.dp))
            }

        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.MyPrimeColor,
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            ) {
                ReviewScreenBottomSheet(
                    rootViewModel = rootViewModel,
                    navHostController = navHostController
                )
            }

        }

    }
}