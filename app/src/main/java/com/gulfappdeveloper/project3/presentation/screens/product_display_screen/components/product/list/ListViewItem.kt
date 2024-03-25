package com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.product.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.data.remote.HttpRoutes
import com.gulfappdeveloper.project3.domain.remote.get.product.Product
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor
import com.gulfappdeveloper.project3.ui.theme.ProgressBarColour

@Composable
fun ListViewItem(
    rootViewModel: RootViewModel,
    product: Product,
    selectedIndex: Int,
    showProgressBarInItem: Boolean,
    index: Int,
    openMultiSizeProduct: (productId: Int, categoryId: Int, index: Int) -> Unit
) {

    var orderCount by remember {
        mutableIntStateOf(1)
    }


    val baseUrl by rootViewModel.baseUrl

    Card(
        modifier = if (product.multiSizeCount > 0) {

            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(5.dp)
                .clickable {

                    openMultiSizeProduct(product.id, product.categoryId, index)
                }
        } else {
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(5.dp)
        },

        elevation = 6.dp,
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colors.MyPrimeColor
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .weight(.25f)
                    .padding(vertical = 5.dp)
                    .alpha(alpha = if (showProgressBarInItem && selectedIndex == index) ContentAlpha.disabled else ContentAlpha.high),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(baseUrl + HttpRoutes.PRODUCT_IMAGE + product.barcode)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.image_loading),
                contentDescription = "Food item",
                alignment = Alignment.Center,
                error = painterResource(id = R.drawable.no_image),
                onError = {
                    /* Log.e(TAG, "ListViewItem: ${it.result.throwable.message}", )
                     Log.i(TAG, "ListViewItem: ${item.image}")*/
                }
            )
            Row(
                modifier = Modifier.weight(.5f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (product.multiSizeCount > 0) {
                    BadgedBox(
                        modifier = Modifier.weight(0.7f),
                        badge = {
                            Badge(modifier = Modifier.alpha(alpha = if (showProgressBarInItem && selectedIndex == index) ContentAlpha.disabled else ContentAlpha.high)) {
                                Text(text = product.multiSizeCount.toString())
                            }
                        }
                    ) {
                        Text(
                            modifier = Modifier.alpha(alpha = if (showProgressBarInItem && selectedIndex == index) ContentAlpha.disabled else ContentAlpha.high),
                            text = product.name,
                            fontSize = 18.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
                            textAlign = TextAlign.Center
                        )
                        if (selectedIndex == index && showProgressBarInItem) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colors.MyPrimeColor,
                                strokeWidth = 1.dp
                            )
                        }
                    }
                } else {
                    Text(
                        modifier = Modifier.weight(0.7f),
                        text = product.name,
                        fontSize = 18.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    modifier = Modifier.weight(0.3f),
                    text = product.rate.toString(),
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
                            orderCount++
                        },
                    text = "+"
                )
                Text(
                    modifier = Modifier.weight(0.33f),
                    text = orderCount.toString(),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .padding(all = 2.dp)
                        .weight(0.33f)
                        .clickable {
                            if (orderCount != 1) {
                                orderCount--
                            }
                        },
                    text = "-",

                    )

            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Magenta)
                    .weight(0.15f)
                    .clickable {
                        rootViewModel.addProductToKOT(
                            count = orderCount,
                            product = product
                        )
                        orderCount = 1
                    },
                contentAlignment = Alignment.Center

            ) {

                Text(text = "ADD", fontWeight = FontWeight.Bold)
            }

        }
    }


}