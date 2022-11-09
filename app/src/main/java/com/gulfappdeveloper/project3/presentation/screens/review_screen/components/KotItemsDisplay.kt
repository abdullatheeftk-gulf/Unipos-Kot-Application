package com.gulfappdeveloper.project3.presentation.screens.review_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.data.remote.HttpRoutes
import com.gulfappdeveloper.project3.domain.remote.post.KotItem
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor
import com.gulfappdeveloper.project3.ui.theme.ProgressBarColour

@Composable
fun KotItemsDisplay(
    kotItem: KotItem,
    rootViewModel: RootViewModel,
    onItemClicked: (KotItem) -> Unit
) {

    var itemCount by remember {
        mutableStateOf(kotItem.quantity.toInt())
    }

    val baseUrl by rootViewModel.baseUrl

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(5.dp),
        elevation = 2.dp,
        border = BorderStroke(0.5.dp, color = MaterialTheme.colors.MyPrimeColor)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .weight(.25f)
                    .padding(all = 5.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(baseUrl+HttpRoutes.PRODUCT_IMAGE + "${kotItem.productId}")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.image_loading),
                contentDescription = "Food item",
                alignment = Alignment.Center,
                error = painterResource(id = R.drawable.no_image),
            )
            Row(
                modifier = Modifier.weight(.5f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.6f)
                        .clickable {
                            onItemClicked(kotItem)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    Text(
                        text = kotItem.productName,
                        fontSize = 20.sp,
                        fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
                        textAlign = TextAlign.Center
                    )
                    if (kotItem.itemNote?.isNotEmpty()!!){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_notes_24),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colors.ProgressBarColour
                        )
                    }

                }

                Text(
                    modifier = Modifier.weight(0.4f),
                    text = "${kotItem.rate * itemCount}",
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.MyPrimeColor,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(.1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(all = 2.dp)
                        .weight(0.33f)
                        .clickable {
                            itemCount++
                            rootViewModel.onIncrementAndDecrementKotItemClicked(
                                count = itemCount,
                                productId = kotItem.productId
                            )
                        },
                    text = "+"
                )
                Text(
                    modifier = Modifier.weight(0.33f),
                    text = itemCount.toString(),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .padding(all = 2.dp)
                        .weight(0.33f)
                        .clickable {
                            if (itemCount != 1) {
                                itemCount--
                                rootViewModel.onIncrementAndDecrementKotItemClicked(
                                    count = itemCount,
                                    productId = kotItem.productId
                                )
                            }
                        },
                    text = "-"
                )

            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Magenta)
                    .weight(0.15f)
                    .clickable {
                        rootViewModel.onDeleteItemFromKotItemClicked(kotItem = kotItem)
                    },
                contentAlignment = Alignment.Center

            ) {

                Icon(imageVector = Icons.Default.Delete, contentDescription = "")
            }

        }
    }


}