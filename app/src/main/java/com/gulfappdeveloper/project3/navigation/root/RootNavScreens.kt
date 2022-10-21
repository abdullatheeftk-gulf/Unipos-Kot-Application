package com.gulfappdeveloper.project3.navigation.root

sealed class RootNavScreens(val route: String) {
    object SplashScreen : RootNavScreens("splash_screen")
    object UrlSetScreen : RootNavScreens("url_set_screen")
    object HomeScreen : RootNavScreens("home_screen")
    object DineInScreen : RootNavScreens("dine_in_screen")
    object TakeAwayScreen : RootNavScreens("take_away_screen")
    object ReviewScreen : RootNavScreens("review_screen")
    object KotScreen : RootNavScreens("kot_screen")
    object SettingsScreen : RootNavScreens("setting_screen")
}
