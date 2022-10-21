package com.gulfappdeveloper.project3.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val CategoryBackGroundColor = Color(0xFFF3EFEE)
val CategoryBackGroundClickedColor = Color(0xFFD0D4F4)
val MyPrimaryColor = Color(0xFF238901)
val BadgeColor = Color(0xFFAB0000)
val ShoppingItemColor = Color(0xFFF4DD5A)
val HistoryItemBackground = Color(0xFFFAFDFA)
val FloatingItemColour = Color(0xFF043909)
val AreaColor = Color(0xFFDAECDA)
val ProgressBarColor = Color(0xFFED6D44)

val Colors.FloatingItemColor
    @Composable
    get() = if (isLight) FloatingItemColour else FloatingItemColour

val Colors.CategoryBackGround
    @Composable
    get() = if (isLight) CategoryBackGroundColor else CategoryBackGroundColor

val Colors.CategoryBackGroundClicked
    @Composable
    get() = if (isLight) CategoryBackGroundClickedColor else CategoryBackGroundClickedColor

val Colors.MyPrimeColor
    @Composable
    get() = if (isLight) MyPrimaryColor else MyPrimaryColor

val Colors.MyBadgeColor
    @Composable
    get() = if (isLight) BadgeColor else BadgeColor

val Colors.MyShoppingItemColor
    @Composable
    get() = if (isLight) ShoppingItemColor else ShoppingItemColor

val Colors.HistoryBackground
    @Composable
    get() = if (isLight) HistoryItemBackground else HistoryItemBackground

val Colors.AreaSelectedColor
    @Composable
    get() = if (isLight) AreaColor else AreaColor

val Colors.ProgressBarColour
    @Composable
    get() = if (isLight) ProgressBarColor else ProgressBarColor