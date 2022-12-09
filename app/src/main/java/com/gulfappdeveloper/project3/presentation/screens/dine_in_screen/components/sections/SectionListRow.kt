package com.gulfappdeveloper.project3.presentation.screens.dine_in_screen.components.sections

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.navigation.root.RootViewModel

@Composable
fun SectionListRow(
    rootViewModel: RootViewModel
) {

    val sectionList = rootViewModel.sectionList

    Card(
        elevation = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {

            item {
                Spacer(modifier = Modifier.width(8.dp))
            }

            items(sectionList) { section ->
                SectionItemDisplay(
                    rootViewModel = rootViewModel,
                    section = section
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }


}