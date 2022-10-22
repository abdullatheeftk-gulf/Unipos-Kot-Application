package com.gulfappdeveloper.project3.presentation.screens.dine_in_screen.components.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Section
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.ui.theme.CategoryBackGround
import com.gulfappdeveloper.project3.ui.theme.CategoryBackGroundClicked

@Composable
fun SectionItemDisplay(
    rootViewModel: RootViewModel,
    section: Section
) {
    val selectedSection by rootViewModel.selectedSection

    Card(
        modifier = Modifier.clickable {
            rootViewModel.setSelectedSection(value = section.id)
        },
        elevation = 4.dp,
        backgroundColor = if (selectedSection == section.id) MaterialTheme.colors.CategoryBackGroundClicked else MaterialTheme.colors.CategoryBackGround,
        shape = MaterialTheme.shapes.large.copy(CornerSize(25))
    ) {
        Text(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 4.dp),
            text = section.name
        )
    }
}