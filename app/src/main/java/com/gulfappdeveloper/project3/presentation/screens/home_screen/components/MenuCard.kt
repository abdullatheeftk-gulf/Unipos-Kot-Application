package com.gulfappdeveloper.project3.presentation.screens.home_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor

@Composable
fun MenuCard(
    pair: Pair<Int, String>,
    onMenuCardClicked: (String) -> Unit
) {
    Card(
        elevation = 6.dp,
        shape = MaterialTheme.shapes.small.copy(all = CornerSize(10.dp)),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.MyPrimeColor,
        ),
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .clickable {
                onMenuCardClicked(pair.second)
            }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(5.dp))
            Image(
                painter = painterResource(id = pair.first),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = pair.second,
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.h5.fontSize
            )
            Spacer(modifier = Modifier.padding(5.dp))
        }

    }

}