package com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.alert_dialogs


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.gulfappdeveloper.project3.domain.remote.get.product.MultiSizeProduct
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.alert_dialogs.multiproduct.MultiGridViewItem
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor

@Composable
fun MultiSizeAlertDialog(
    rootViewModel: RootViewModel,
    parentCategoryId: Int,
    onDismissRequest: () -> Unit,
    multiSizeProductList: SnapshotStateList<MultiSizeProduct>
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier.padding(8.dp),
        buttons = {
            Column(
                modifier = Modifier.padding(all = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Multi Size Product",
                    color = MaterialTheme.colors.MyPrimeColor,
                    fontStyle = MaterialTheme.typography.h6.fontStyle,
                    textDecoration = TextDecoration.Underline
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(all = 4.dp)
                ) {
                    itemsIndexed(multiSizeProductList) { index, item ->
                        MultiGridViewItem(
                            rootViewModel = rootViewModel,
                            multiSizeProduct = item,
                            parentCategoryId = parentCategoryId,
                            onDismissRequest = onDismissRequest
                        )
                    }
                }
            }
        }
    )
}