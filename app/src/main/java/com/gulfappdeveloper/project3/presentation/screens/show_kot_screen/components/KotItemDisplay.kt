package com.gulfappdeveloper.project3.presentation.screens.show_kot_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.domain.remote.post.KotItem


@Composable
fun KotItemDisplay(
    kotItem: KotItem,
    index: Int
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth().padding(bottom = 2.dp)
    ) {
        Text(
            text = "${(index + 1)},",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        Text(
            text = kotItem.productName,
            modifier = Modifier.weight(7f),
            textAlign = TextAlign.Start,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = kotItem.quantity.toInt().toString(),
            modifier = Modifier.weight(4f),
            textAlign = TextAlign.Center
        )
    }
}