package com.gulfappdeveloper.project3.presentation.screens.show_kot_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.gulfappdeveloper.project3.domain.remote.post.KotItem


@Composable
fun KotItemDisplay(
    kotItem: KotItem,
    index:Int
) {
   Row(
       horizontalArrangement = Arrangement.Center,
       verticalAlignment = Alignment.CenterVertically,
       modifier = Modifier.fillMaxWidth()
   ) {
       Text(
           text = (index+1).toString(),
           modifier = Modifier.weight(1f),
           textAlign = TextAlign.Start
       )
       Text(
           text = kotItem.productName,
           modifier = Modifier.weight(7f),
       )
       Text(
           text = kotItem.quantity.toString(),
           modifier = Modifier.weight(2f),
           textAlign = TextAlign.Center
       )
   }
}