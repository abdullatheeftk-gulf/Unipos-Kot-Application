package com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.product.grid

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun GridViewItem(
    rootViewModel: RootViewModel,
    product: Product
) {

    var orderCount by remember {
        mutableStateOf(1)
    }

    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 6.dp,
        modifier = Modifier.padding(5.dp),
        border = BorderStroke(0.5.dp, Color.DarkGray)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(HttpRoutes.PRODUCT_IMAGE + "${product.id}")
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                placeholder = painterResource(id = R.drawable.image_loading),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f)
                    .fillMaxWidth(),
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
                        .height(34.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = product.name,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,

                        )
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