package com.gulfappdeveloper.project3.presentation.screens.dine_in_screen.components.tables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.data.remote.HttpRoutes
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Table
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor

//private const val TAG = "TableDisplay"

@Composable
fun TableDisplay(
    rootViewModel: RootViewModel,
    table: Table,
) {

    val baseUrl by rootViewModel.baseUrl

    Card(
        modifier = Modifier
            .size(128.dp)
            .padding(all = 8.dp)
            .clickable {
                rootViewModel.setSelectedTable(table = table)
                rootViewModel.getKotCancelPrivilege()
            },
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.MyPrimeColor
        ),
        elevation = 0.dp,
        shape = MaterialTheme.shapes.medium,
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = baseUrl + HttpRoutes.TABLE_IMAGE + "${table.id}")
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .padding(8.dp),
            placeholder = painterResource(id = R.drawable.image_loading),
            alignment = Alignment.Center,
            error = painterResource(id = R.drawable.no_image)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = table.tableName.trim(),
                color =
                if (table.occupied > 0 && table.occupied < table.noOfSeats)
                    Color.Blue
                else if (table.occupied >= table.noOfSeats)
                    Color.Red
                else
                    Color.Black
            )
        }

    }

}