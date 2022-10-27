package com.gulfappdeveloper.project3.presentation.screens.product_display_screen.components.topbar

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.gulfappdeveloper.project3.navigation.root.RootViewModel

@Composable
fun NormalTopBar(
    rootViewModel: RootViewModel,
    onSearchButtonClicked:()->Unit,
) {

    val netAmount by rootViewModel.kotNetAmount

    TopAppBar(
        title = {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colors.background)) {
                        append("Net Amount : ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.Yellow,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("$netAmount")
                    }

                }
            )
        },
        actions = {
            IconButton(onClick = {
                onSearchButtonClicked()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            }
        }
    )
}