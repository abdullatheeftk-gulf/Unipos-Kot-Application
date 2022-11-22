package com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.product.grid

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
fun GridViewItem(
    rootViewModel: RootViewModel,
    product: Product,
    selectedIndex: Int,
    showProgressBarInItem: Boolean,
    index: Int,
    openMultiSizeProduct: (productId: Int, categoryId: Int, index: Int) -> Unit
) {

    var orderCount by remember {
        mutableStateOf(1)
    }

    val baseUrl by rootViewModel.baseUrl

    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 6.dp,
        modifier = if (product.multiSizeCount > 0) {
            Modifier
                .padding(5.dp)
                .clickable {
                    openMultiSizeProduct(product.id, product.categoryId, index)
                }
        } else {
            Modifier.padding(5.dp)
        },
        border = BorderStroke(0.5.dp, MaterialTheme.colors.MyPrimeColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            AsyncImage(

                model = ImageRequest.Builder(LocalContext.current)
                    .data(baseUrl + HttpRoutes.PRODUCT_IMAGE + product.barcode)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                placeholder = painterResource(id = R.drawable.image_loading),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f)
                    .fillMaxWidth()
                    .alpha(alpha = if (showProgressBarInItem && selectedIndex == index) ContentAlpha.disabled else ContentAlpha.high),
                error = painterResource(id = R.drawable.no_image),
                onError = {
                    //  Log.e(TAG, "ListViewItem: ${it.result.throwable.message}", )

                }
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .height(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (product.multiSizeCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge(){
                                    Text(text = product.multiSizeCount.toString())
                                }
                            },
                            modifier = Modifier.padding(start = 4.dp, end = 12.dp)
                        ) {
                            Text(
                                modifier = Modifier.alpha(alpha = if (showProgressBarInItem && selectedIndex == index) ContentAlpha.disabled else ContentAlpha.high),
                                text = product.name,
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 16.sp
                            )
                            if (selectedIndex == index && showProgressBarInItem) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = MaterialTheme.colors.ProgressBarColour,
                                    strokeWidth = 1.dp
                                )
                            }
                        }

                    } else {
                        Text(
                            text = product.name,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 16.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = product.rate.toString(),
                    fontSize = 22.sp,
                    color = MaterialTheme.colors.MyPrimeColor,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(
                        onClick = {
                            if (orderCount != 1) {
                                orderCount--
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "-",
                            modifier = Modifier.scale(2f)
                        )
                    }
                    TextButton(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        enabled = false
                    ) {
                        Text(
                            text = orderCount.toString(),
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    TextButton(
                        onClick = {
                            orderCount++
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 1.dp)
                    ) {
                        Text(text = "+", modifier = Modifier.scale(1f))
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(Color.Magenta)
                        .clickable {
                            rootViewModel.addProductToKOT(
                                count = orderCount,
                                product = product
                            )
                            orderCount = 1
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "ADD")
                }
            }
        }
    }


}