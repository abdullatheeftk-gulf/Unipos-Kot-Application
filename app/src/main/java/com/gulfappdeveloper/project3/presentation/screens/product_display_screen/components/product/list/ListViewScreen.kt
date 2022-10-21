package com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.product.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor
import com.gulfappdeveloper.project3.ui.theme.ProgressBarColour

@Composable
fun ListViewScreen(
    rootViewModel: RootViewModel,
    showProgressBar: Boolean,
    showEmptyList: Boolean
) {

    val productList = rootViewModel.productList

    if (showProgressBar) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    strokeWidth = 1.dp,
                    color = MaterialTheme.colors.ProgressBarColour
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Loading....")
            }
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
                        alpha = ContentAlpha.medium,
                        modifier = Modifier.size(200.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Empty List",
                        color = MaterialTheme.colors.MyPrimeColor,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    all = 5.dp
                )
            ) {

                item { Spacer(modifier = Modifier.height(7.dp)) }

                items(productList) { product ->
                    ListViewItem(
                        rootViewModel = rootViewModel,
                        product = product
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }


}