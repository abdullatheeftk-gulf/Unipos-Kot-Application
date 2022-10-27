package com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.category

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.navigation.root.RootViewModel

@Composable
fun CategoryListRow(
    rootViewModel: RootViewModel,
    onCategoryItemClicked:()->Unit
) {
    val categoryList = rootViewModel.categoryList

    LazyRow{

        item {
            Spacer(modifier = Modifier.width(8.dp))
        }

        items(categoryList){category->
            CategoryItemDisplay(
                rootViewModel = rootViewModel,
                category = category,
                onCategoryItemClicked = onCategoryItemClicked
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}